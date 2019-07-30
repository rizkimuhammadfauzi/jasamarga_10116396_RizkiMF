package com.example.jasamarga;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import static com.example.jasamarga.DashboardActivity.TAG_USERNAME;
import static com.example.jasamarga.LoginActivity.TAG_ID;

public class settingActivity extends AppCompatActivity {

    private ImageView back;
    private Button logout;
    SharedPreferences sharedpreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        logout = findViewById(R.id.btnLogout);
        back = findViewById(R.id.back);

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                sharedpreferences = getSharedPreferences(LoginActivity.my_shared_preferences, Context.MODE_PRIVATE);

                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.putBoolean(LoginActivity.session_status, false);
                editor.putString(TAG_ID, null);
                editor.putString(TAG_USERNAME, null);
                editor.commit();

                Intent intent = new Intent(settingActivity.this,LoginActivity.class);
                startActivity(intent);
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(settingActivity.this,LoginActivity.class);
                startActivity(intent);
            }
        });
    }
}
