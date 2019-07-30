package com.example.jasamarga.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.jasamarga.DetailActivity;
import com.example.jasamarga.R;
import com.example.jasamarga.data.modelData;

import java.util.ArrayList;

public class LaporanAdapter extends RecyclerView.Adapter<LaporanAdapter.ViewProcessHolder>{
    Context context;
    CardView card1,card2;


    private ArrayList<modelData> item;

    public LaporanAdapter(Context context, ArrayList<modelData> item) {
        this.context = context;
        this.item = item;
    }

    @NonNull
    @Override
    public LaporanAdapter.ViewProcessHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View test = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_fragment_one, parent, false); //memanggil layout list recyclerview
        ViewProcessHolder processHolder = new ViewProcessHolder(test);

        card1 = (CardView) test.findViewById(R.id.card);


        return processHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull LaporanAdapter.ViewProcessHolder holder, int position) {

        final modelData data = item.get(position);
        /*holder.kilometer.setText(data.getKilometer());
        holder.meter.setText(data.getMeter());
        holder.meterDetail.setText(data.getMeterDetail());*/
        holder.kategori.setText(data.getKategori());
        holder.jenis.setText(data.getJenis());
        holder.deskripsi.setText(data.getDeskripsi());
        holder.tgl.setText(data.getTgl());


        card1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), DetailActivity.class);
                intent.putExtra("id", data.getId());
                context.startActivity(intent);
            }
        });

        /*card2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), DetailSelesaiActivity.class);
                intent.putExtra("id", data.getId());
                context.startActivity(intent);
            }
        });*/

    }

    @Override
    public int getItemCount() {
        return item.size();
    }

    public class ViewProcessHolder extends RecyclerView.ViewHolder {

        ImageView foto;
        TextView kategori, jenis, deskripsi, tgl;

        public ViewProcessHolder(@NonNull View itemView) {
            super(itemView);

            kategori    = (TextView) itemView.findViewById(R.id.title1);
            jenis       = (TextView) itemView.findViewById(R.id.title2);
            deskripsi   = (TextView) itemView.findViewById(R.id.description);
            tgl         = (TextView) itemView.findViewById(R.id.tgl);
            foto        = (ImageView) itemView.findViewById(R.id.photo);
        }
    }
}
