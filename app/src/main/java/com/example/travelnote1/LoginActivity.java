package com.example.travelnote1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.beardedhen.androidbootstrap.BootstrapEditText;
import com.example.travelnote1.폴더추가하기.MainActivity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class LoginActivity extends AppCompatActivity {

    private Gson gson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final BootstrapEditText et_email =(BootstrapEditText)findViewById(R.id.et_email);
        final BootstrapEditText et_password = (BootstrapEditText)findViewById(R.id.et_password);

        // Hide ActionBar
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        BootstrapButton button = (BootstrapButton)findViewById(R.id.btn_login);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = et_email.getText().toString();
                String password = et_password.getText().toString();

                // 회원정보 조회하기
                SharedPreferences sharedPreferences = getSharedPreferences("shared", MODE_PRIVATE);
                String value = sharedPreferences.getString(email, "");
                Log.e("str",value);

                if(value.isEmpty()){
                    Toast.makeText(getApplicationContext(), "존재하지 않는 아이디 입니다.", Toast.LENGTH_SHORT).show();
                    Log.e("str","존재하지 않는 아이디 입니다.");
                }else{
                    Gson gson = new Gson();
                    Person person = gson.fromJson(value, Person.class);
                    String pass = person.getEt_pass();
                    Log.e("str","비밀번호 : " +person.getEt_pass());
                    Log.e("str","내가 누른 비밀번호 : " +password);

                    boolean check = password.equals(pass);
                    if(check==true){
                        Toast.makeText(getApplicationContext(), "로그인 되었습니다.", Toast.LENGTH_SHORT).show();
                        Log.e("str","로그인 되었습니다.");
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("CurrentUser",email);
                        editor.commit();//save를 완료하라

                        Intent intent2 = new Intent(getApplicationContext(),MainActivity.class);
                        startActivity(intent2);
                        finish();
                    }else{
                        Toast.makeText(getApplicationContext(), "비밀번호를 다시 확인해주세요.", Toast.LENGTH_SHORT).show();
                        Log.e("str","비밀번호를 다시 확인해주세요.");
                    }
                }
            }
        });

        BootstrapButton button1 = (BootstrapButton)findViewById(R.id.btn_signup);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(getApplicationContext(),SignupActivity.class);
                startActivity(intent1);
                finish();
            }
        });
    }
}
