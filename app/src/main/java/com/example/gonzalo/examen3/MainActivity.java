package com.example.gonzalo.examen3;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button _btnProvisional;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        _btnProvisional = findViewById(R.id.btnProvisional);

        Intent intent = new Intent(MainActivity.this, HomeAct.class);
        startActivity(intent);
    }
}
