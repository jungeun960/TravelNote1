package com.example.travelnote1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.beardedhen.androidbootstrap.BootstrapCircleThumbnail;
import com.beardedhen.androidbootstrap.BootstrapEditText;
import com.example.travelnote1.폴더추가하기.MainActivity;
import com.example.travelnote1.프로필.ProfileEditActivity;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.Picasso;

import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class SignupActivity extends AppCompatActivity {

    private Gson gson;
    private static final int REQUEST_CODE = 0;
    private Uri photoUri;
    private BootstrapCircleThumbnail photo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        photo = findViewById(R.id.photo);

        photo.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent, REQUEST_CODE);
            }
        });

        setInsertButton();
    }

    private void setInsertButton() {
        BootstrapButton button = (BootstrapButton) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BootstrapEditText et_email = (BootstrapEditText)findViewById(R.id.et_email);
                BootstrapEditText et_name = (BootstrapEditText)findViewById(R.id.et_name);
                BootstrapEditText et_pass = (BootstrapEditText)findViewById(R.id.et_pass);
                BootstrapEditText et_pass_ok = (BootstrapEditText)findViewById(R.id.et_pass_ok);


//                SharedPreferences sharedPreferences = getSharedPreferences("shared", MODE_PRIVATE);
//                String em = et_email.getText().toString();
//                String value = sharedPreferences.getString(em, "");
//                if(em.isEmpty()){
//                    Toast.makeText(getApplicationContext(), "사용가능한 아이디 입니다.", Toast.LENGTH_SHORT).show();
//                    Log.e("str","사용가능한 아이디 입니다.");
//                }

                if(et_email.getText().toString().length()==0){
                    Toast.makeText(getApplicationContext(), "Email을 입력하세요!", Toast.LENGTH_SHORT).show();
                    et_email.requestFocus();
                    return;
                }
                if(et_name.getText().toString().length()==0){
                    Toast.makeText(getApplicationContext(), "닉네임을 입력하세요!", Toast.LENGTH_SHORT).show();
                    et_name.requestFocus();
                    return;
                }
                if(et_pass.getText().toString().length()==0){
                    Toast.makeText(getApplicationContext(), "비밀번호를 입력하세요!", Toast.LENGTH_SHORT).show();
                    et_pass.requestFocus();
                    return;
                }
                if(et_pass_ok.getText().toString().length()==0){
                    Toast.makeText(getApplicationContext(), "비밀번호 확인을 입력하세요!", Toast.LENGTH_SHORT).show();
                    et_pass_ok.requestFocus();
                    return;
                }
                if(!et_pass.getText().toString().equals(et_pass_ok.getText().toString())){
                    Toast.makeText(getApplicationContext(), "비밀번호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show();
                    et_pass.setText("");
                    et_pass_ok.setText("");
                    et_pass.requestFocus();
                    return;
                }

                Toast.makeText(getApplicationContext(), "회원가입이 성공적으로 이루어졌습니다.", Toast.LENGTH_SHORT).show();

                Person person = new Person();
                person.setEt_email(et_email.getText().toString());
                person.setEt_name(et_name.getText().toString());
                person.setEt_pass(et_pass.getText().toString());
                person.setPhoto(photoUri.toString());

                String email = et_email.getText().toString();

                // Gson 인스턴스 생성
                gson = new GsonBuilder().create();
                // JSON 으로 변환
                String strContact = gson.toJson(person, Person.class);

                SharedPreferences sp = getSharedPreferences("shared", MODE_PRIVATE);
                SharedPreferences.Editor editor = sp.edit();
                editor.putString(email, strContact); // JSON으로 변환한 객체를 저장한다.
                editor.commit(); //완료한다.

                Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
                startActivity(intent);
                finish();

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                try {
                    photoUri = data.getData();
                    Picasso.with(this).load(photoUri).into(photo);
                    Log.e("갤러리 진입","경로 : "+photoUri);
                } catch (Exception e) {

                }
            } else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(this, "사진 선택 취소", Toast.LENGTH_LONG).show();
            }
        }
    }


}
