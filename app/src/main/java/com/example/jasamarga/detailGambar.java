package com.example.jasamarga;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class detailGambar extends AppCompatActivity {

    ImageView photo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_gambar);

        photo = findViewById(R.id.gambar);
        byte[]  bytes = getIntent().getByteArrayExtra("images");

        Bitmap bmp = BitmapFactory.decodeByteArray(bytes,0,bytes.length);

        photo.setImageBitmap(bmp);

    }

    public void klik(View view){
        Intent intent = new Intent(detailGambar.this,LaporanActivity.class);
        startActivity(intent);
    }
}
