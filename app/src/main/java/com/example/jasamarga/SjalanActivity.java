package com.example.jasamarga;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class SjalanActivity extends AppCompatActivity implements ImageUtils.ImageAttachmentListener{

    ProgressDialog pDialog;
    private ImageView back, photo;
    private Spinner spjalan, spmeter, spmeterdetail;
    private EditText deskripsi, kilometer;
    private Button btnSimpan;

    String imagebase64 = "";
    ImageUtils imageutils;
    /*Dialog popup;
    TextView titleTv, messageTv;
    Button btnBerhasil, btnGagal;*/

    int success;
    ConnectivityManager conMgr;

    private String url = Server.URL + "sepanjangJalan.php";

    private static final String TAG = SjalanActivity.class.getSimpleName();

    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";

    String tag_json_obj = "json_obj_req";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_sjalan);

        imageutils =new ImageUtils(SjalanActivity.this);

        back = findViewById(R.id.back);
        photo = findViewById(R.id.photo);
        spjalan = findViewById(R.id.spJalan);
        spmeter = findViewById(R.id.spMeter);
        spmeterdetail = findViewById(R.id.spMeterdetail);
        deskripsi = findViewById(R.id.etDeskripsi);
        kilometer = findViewById(R.id.etKilo);
        btnSimpan = findViewById(R.id.btnSimpan);


        conMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        {
            if (conMgr.getActiveNetworkInfo() != null
                    && conMgr.getActiveNetworkInfo().isAvailable()
                    && conMgr.getActiveNetworkInfo().isConnected()) {
            } else {
                Toast.makeText(getApplicationContext(), "No Internet Connection",
                        Toast.LENGTH_LONG).show();
            }
        }

        btnSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String jenis = spjalan.getSelectedItem().toString();
                String meter = spmeter.getSelectedItem().toString();
                String meterdetail = spmeterdetail.getSelectedItem().toString();
                String deskrip = deskripsi.getText().toString();
                String kiloMeter = kilometer.getText().toString();

                if (conMgr.getActiveNetworkInfo() != null
                        && conMgr.getActiveNetworkInfo().isAvailable()
                        && conMgr.getActiveNetworkInfo().isConnected()) {
                    checkJalan(jenis, kiloMeter, meter, meterdetail, deskrip);
                } else {
                    Toast.makeText(getApplicationContext(), "No Internet Connection", Toast.LENGTH_SHORT).show();
                }

            }
        });

        spjalan.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View selectedItemView, int position, long id) {
                ((TextView) adapterView.getChildAt(0)).setTextColor(Color.WHITE);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SjalanActivity.this, DashboardActivity.class);
                startActivity(intent);
            }
        });

        photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imageutils.imagepicker(1);
            }
        });


    }

    private void checkJalan(final String jenis, final String kilometer, final String meter, final String meterdetail, final String deskrip) {

        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);
        pDialog.setMessage("Simpan Data ...");
        showDialog();

        StringRequest strReq = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.e(TAG, "Register Response: " + response.toString());
                hideDialog();

                try {
                    JSONObject jObj = new JSONObject(response);
                    success = jObj.getInt(TAG_SUCCESS);

                    // Check for error node in json
                    if (success == 1) {

                        Log.e("Successfully Register!", jObj.toString());

                        /*showPopupBerhasil();*/

                        Toast.makeText(getApplicationContext(),
                                jObj.getString(TAG_MESSAGE), Toast.LENGTH_LONG).show();

                       /* txtNama.setText("");
                        txtUsername.setText("");
                        txtPassword.setText("");
                        txtRepassword.setText("");
                        txtNama.setText("");*/


                    } else {

                        /*showPopupGagal();*/
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
                Log.e(TAG, "SimpanData Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_LONG).show();

                hideDialog();

            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<String, String>();
                params.put("jenis", jenis);
                params.put("kilometer", kilometer);
                params.put("meter", meter);
                params.put("meterdetail", meterdetail);
                params.put("deskripsi", deskrip);
                params.put("foto", imagebase64);

                return params;
            }

        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_json_obj);

    }

    /*private void showPopupGagal() {

        popup.setContentView(R.layout.gagal_popup);
        btnGagal = (Button) popup.findViewById(R.id.btnGagal);
        titleTv = (TextView) popup.findViewById(R.id.titleTv);
        messageTv = (TextView) popup.findViewById(R.id.messageTv);

        btnGagal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popup.dismiss();
            }
        });

        popup.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        popup.show();
    }

    private void showPopupBerhasil() {

        popup.setContentView(R.layout.success_popup);
        btnBerhasil = (Button) popup.findViewById(R.id.btnBerhasil);
        titleTv = (TextView) popup.findViewById(R.id.titleTv);
        messageTv = (TextView) popup.findViewById(R.id.messageTv);

        btnBerhasil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popup.dismiss();
            }
        });

        popup.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        popup.show();

    }*/


    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        Log.d("Fragment", "onRequestPermissionsResult: "+requestCode);
        imageutils.request_permission_result(requestCode, permissions, grantResults);
    }

    @Override
    public void image_attachment(int from, String filename, Bitmap file, Uri uri) {
        photo.setImageBitmap(file);
        Bitmap bitmap = file;
        String file_name = filename;
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream .toByteArray();
        imagebase64 = Base64.encodeToString(byteArray, Base64.DEFAULT);
        String path =  Environment.getExternalStorageDirectory() + File.separator + "ImageAttach" + File.separator;
        imageutils.createImage(file,filename,path,false);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        Log.d("Fragment", "onActivityResult: ");
        imageutils.onActivityResult(requestCode, resultCode, data);

    }
}
