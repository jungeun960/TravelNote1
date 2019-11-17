package com.example.travelnote1.공유하기;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.travelnote1.R;

public class Shared1Activity extends AppCompatActivity {

    private EditText et_title;
    private EditText et_context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shared1);

        Button btn_register = (Button)findViewById(R.id.btn_register);
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = getIntent();
                et_title = findViewById(R.id.et_title);
                et_context = findViewById(R.id.et_context);
                intent.putExtra("title",et_title.getText().toString());
                intent.putExtra("context",et_context.getText().toString());
                setResult(RESULT_OK,intent); // 추가 정보를 넣은 후 다시 인텐트를 반환합니다.
                finish(); // 액티비티 종료
            }
        });

    }
}
