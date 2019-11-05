package com.example.travelnote1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

public class Add1Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add1);

        Intent intent = getIntent();
        String title = intent.getStringExtra("title");
        Toast.makeText(getApplicationContext(), "제목은 "+title, Toast.LENGTH_SHORT).show();
    }
}
