package com.example.jasamarga.fragment;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.example.jasamarga.AppController;
import com.example.jasamarga.R;
import com.example.jasamarga.Server;
import com.example.jasamarga.adapter.LaporanAdapter;
import com.example.jasamarga.data.modelData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentTwo extends Fragment {

    private ArrayList<modelData> modelDataList = new ArrayList<>();
    RecyclerView recyclerView;
    private LaporanAdapter laporanAdapter;
    ProgressDialog pd;
    private String url = Server.URL + "laporanSelesai.php";
    public FragmentTwo() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_two, container, false);

        pd = new ProgressDialog(getActivity());
        recyclerView = (RecyclerView) v.findViewById(R.id.recylerView);
        laporanAdapter = new LaporanAdapter(getActivity(), modelDataList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(laporanAdapter);

        loadjson();

        return v;
    }

    public void loadjson(){

        pd.setMessage("Mengambil Data");
        pd.setCancelable(false);
        pd.show();


        JsonArrayRequest arrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                pd.cancel();
                for (int i=0; i < response.length(); i++){
                    try {
                        JSONObject data = response.getJSONObject(i);
                        modelData modelData = new modelData(data.getInt("id"),data.getString("kategori"),data.getString("jenis"),"b","c","d",data.getString("deskripsi"),"f",data.getString("created_at"));
                        modelDataList.add(modelData);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                laporanAdapter.notifyDataSetChanged();
            }
        }, new Response.ErrorListener(){

            @Override
            public void onErrorResponse(VolleyError error) {
                pd.cancel();
                Log.d("volley", "error : " + error.getMessage());
            }
        });
        AppController.getInstance().addToRequestQueue(arrayRequest);
    }

}
