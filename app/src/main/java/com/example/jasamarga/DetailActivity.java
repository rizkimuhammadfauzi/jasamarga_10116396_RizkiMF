package com.example.jasamarga;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;

public class DetailActivity extends AppCompatActivity {

    private ImageView back,photo;
    ProgressDialog pDialog;
    Button btnProses;
    int success;
    String imagebase64 = "";

    EditText jenis,meter,meterd,deskripsi;

    private static final String TAG = JalanActivity.class.getSimpleName();

    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";

    String tag_json_obj = "json_obj_req";
    private String url = Server.URL + "detailLaporan.php";
    private String url_update = Server.URL + "updateLaporan.php";
    int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_detail);

        Bundle b = getIntent().getExtras();
         id = b.getInt("id");
            laporanDetail(id);

        btnProses = findViewById(R.id.btnProses);
        back = findViewById(R.id.back);
        jenis = findViewById(R.id.txtJenis);
        meter = findViewById(R.id.txtMeter);
        meterd = findViewById(R.id.txtMeterDetail);
        deskripsi = findViewById(R.id.txtDeskripsi);
        photo = findViewById(R.id.gambar);
        final Drawable mDrawable = photo.getDrawable();

        photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ImageView mImageView = view.findViewById(R.id.gambar);
                Drawable mDrawable = mImageView.getDrawable();
                Bitmap mBitmap =((BitmapDrawable)mDrawable).getBitmap();
                Intent intent = new Intent(view.getContext(), detailGambar.class);
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                mBitmap.compress(Bitmap.CompressFormat.JPEG,100,stream);
                byte [] bytes = stream.toByteArray();
                intent.putExtra("images",bytes);
                startActivity(intent);
            }
        });

        btnProses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               updateLaporan();
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DetailActivity.this,LaporanActivity.class);
                startActivity(intent);
            }
        });

    }

    private void updateLaporan() {
        StringRequest strReq = new StringRequest(Request.Method.POST, url_update, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.e(TAG, "Register Response: " + response.toString());
                hideDialog();
                /*  Toast.makeText(getApplicationContext(),response, Toast.LENGTH_LONG).show();*/

                try {
                    JSONObject jObj = new JSONObject(response);
                    success = jObj.getInt(TAG_SUCCESS);

                    // Check for error node in json
                    if (success == 1) {

                        Log.e("Update Data Berhasil!", jObj.toString());

                        Toast.makeText(getApplicationContext(),
                                jObj.getString(TAG_MESSAGE), Toast.LENGTH_LONG).show();

                        Intent intent = new Intent(DetailActivity.this, LaporanActivity.class);
                        startActivity(intent);

                    } else {
                        Toast.makeText(getApplicationContext(),
                                jObj.getString(TAG_MESSAGE), Toast.LENGTH_LONG).show();

                    }
                } catch (JSONException e) {
                    // JSON error
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Detail Tampil Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_LONG).show();

                hideDialog();

            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<String, String>();
                params.put("id", String.valueOf(id));

                return params;
            }

        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_json_obj);
    }

    private void laporanDetail(final int id ) {

        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);
        pDialog.setMessage("Ambil Data ...");
        showDialog();

        StringRequest strReq = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.e(TAG, "Detail Response: " + response.toString());
                hideDialog();
              /*  Toast.makeText(getApplicationContext(),response, Toast.LENGTH_LONG).show();*/

                try {
                    JSONObject jObj = new JSONObject(response);
                    success = jObj.getInt(TAG_SUCCESS);

                    // Check for error node in json
                    if (success == 1) {

                        jenis.setText(jObj.getString("jenis"));
                        meter.setText(jObj.getString("meter"));
                        meterd.setText(jObj.getString("meterDetail"));
                        deskripsi.setText(jObj.getString("deskripsi"));
                        Picasso.get().load("http://ferinatexjaya.store/jasamarga/images/"+jObj.getString("foto")).into(photo);


                    } else {
                        Toast.makeText(getApplicationContext(),
                                jObj.getString(TAG_MESSAGE), Toast.LENGTH_LONG).show();

                    }
                } catch (JSONException e) {
                    // JSON error
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Detail Tampil Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_LONG).show();

                hideDialog();

            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<String, String>();
                params.put("id", String.valueOf(id));

                return params;
            }

        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_json_obj);

    }

    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }

}
