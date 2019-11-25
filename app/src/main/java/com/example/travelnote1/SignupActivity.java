package com.example.travelnote1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.beardedhen.androidbootstrap.BootstrapCircleThumbnail;
import com.beardedhen.androidbootstrap.BootstrapEditText;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.picasso.Picasso;

public class SignupActivity extends AppCompatActivity {

    private Gson gson;
    private static final int REQUEST_CODE = 0;
    private Uri photoUri;
    private BootstrapCircleThumbnail photo;
    private BootstrapButton check;
    private BootstrapEditText et_email;
    private BootstrapEditText et_name;
    private BootstrapEditText et_pass;
    private BootstrapEditText et_pass_ok;
    private boolean check_email=false; // 중복체크 확인 유무 판별하기

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        // 툴바 없애기
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        et_email = (BootstrapEditText)findViewById(R.id.et_email);
        et_name = (BootstrapEditText)findViewById(R.id.et_name);
        et_pass = (BootstrapEditText)findViewById(R.id.et_pass);
        et_pass_ok = (BootstrapEditText)findViewById(R.id.et_pass_ok);

        // 프로필 사진 등록
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

        // 이메일 중복 체크
        check = (BootstrapButton)findViewById(R.id.check);
        check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 쉐어드에 동일한 이메일 있는지 확인
                String email = et_email.getText().toString();
                SharedPreferences sharedPreferences = getSharedPreferences("shared", MODE_PRIVATE);
                String value = sharedPreferences.getString(email, "");
                Log.e("str",value);

                // 값이 없을 때
                if(value.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "사용가능한 아이디 입니다.", Toast.LENGTH_SHORT).show();
                    Log.e("str", "사용가능한 아이디 입니다.");
                    // edittext 수정 불가능하게 만듬
                    et_email.setFocusable(false);
                    et_email.setClickable(false);
                    check_email=true;
                }else {
                    // 있을 때
                    Toast.makeText(getApplicationContext(), "이미 존재하는 이메일 입니다. 다른 이메일로 등록해주세요.", Toast.LENGTH_SHORT).show();
                    Log.e("str", "이미 존재하는 이메일 입니다. 다른 이메일로 등록해주세요.");
                    check_email=false;
                }
            }
        });

        // 계정 만들기 클릭 시
        BootstrapButton button = (BootstrapButton) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(check_email==false){
                    Toast.makeText(getApplicationContext(), "이메일 중복확인을 해주세요", Toast.LENGTH_SHORT).show();
                    et_email.requestFocus();
                    return;
                }

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

                //Person 클래스에 회원정보 담기
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
                // 이메일을 key로 설정
                editor.putString(email, strContact); // JSON으로 변환한 객체를 저장한다.
                editor.commit(); //완료한다.

                // 로그인 페이지로 이동
                Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }

    // 갤러리 열어 사진 가져오기
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
