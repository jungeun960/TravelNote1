package com.example.travelnote1;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.Checkable;
import android.widget.ImageView;
import android.widget.Toast;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.beardedhen.androidbootstrap.BootstrapCircleThumbnail;
import com.beardedhen.androidbootstrap.BootstrapEditText;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;

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
    private CheckBox checkbox;
    private boolean check_agree = false; // 약관 동의 유무

    private static final String TAG = "Photo";
    private Boolean isPermission = true;
    private static final int PICK_FROM_ALBUM = 1;
    private File tempFile;
    private Uri image_uri;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        // 툴바 없애기
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        tedPermission();

        et_email = (BootstrapEditText)findViewById(R.id.et_email);
        et_name = (BootstrapEditText)findViewById(R.id.et_name);
        et_pass = (BootstrapEditText)findViewById(R.id.et_pass);
        et_pass_ok = (BootstrapEditText)findViewById(R.id.et_pass_ok);
        checkbox = (CheckBox)findViewById(R.id.checkbox);

        checkbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                check_agree = true;
            }
        });

        // 프로필 사진 등록
        photo = findViewById(R.id.photo);
        photo.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                // 권한 허용에 동의하지 않았을 경우 토스트를 띄웁니다.
                if(isPermission) { //앨범에서 이미지 가져오기
                    Intent intent = new Intent(Intent.ACTION_PICK);
                    intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
                    startActivityForResult(intent, PICK_FROM_ALBUM);
                }
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
                if(check_agree==false){
                    Toast.makeText(getApplicationContext(), "약관 동의가 필요합니다.", Toast.LENGTH_SHORT).show();
                    return;
                }

                Toast.makeText(getApplicationContext(), "회원가입이 성공적으로 이루어졌습니다.", Toast.LENGTH_SHORT).show();

                //Person 클래스에 회원정보 담기
                Person person = new Person();
                person.setEt_email(et_email.getText().toString());
                person.setEt_name(et_name.getText().toString());
                person.setEt_pass(et_pass.getText().toString());
                person.setPhoto(image_uri.toString());

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

    @SuppressLint("MissingSuperCall")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK) {
            Toast.makeText(this, "취소 되었습니다.", Toast.LENGTH_SHORT).show();

            if(tempFile != null) {
                if (tempFile.exists()) {
                    if (tempFile.delete()) {
                        Log.e(TAG, tempFile.getAbsolutePath() + " 삭제 성공");
                        tempFile = null;
                    }
                }
            }
            return;
        }

        if (requestCode == PICK_FROM_ALBUM) {

            Uri photoUri = data.getData();
            Log.e(TAG, "PICK_FROM_ALBUM photoUri (Uri photoUri = data.getData()): " + photoUri);

            Cursor cursor = null;
            try {
                // Uri 스키마를  content:/// 에서 file:/// 로  변경한다.
                String[] proj = {MediaStore.Images.Media.DATA};
                assert photoUri != null;
                cursor = getContentResolver().query(photoUri, proj, null, null, null);
                assert cursor != null;
                int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                cursor.moveToFirst();

                tempFile = new File(cursor.getString(column_index));
                Log.e(TAG, "tempFile Uri (Uri.fromFile(tempFile)): " + Uri.fromFile(tempFile));
                image_uri = Uri.fromFile(tempFile);

            } finally {
                if (cursor != null) {
                    cursor.close();
                }
            }
            Picasso.with(this).load(image_uri).into(photo);
            Log.e(TAG, "tempFile : " + tempFile);
            Log.e(TAG, "tempFile.getAbsolutePath() : " + tempFile.getAbsolutePath());
            tempFile = null;
        }
    }

    /**
     *  권한 설정
     */
    private void tedPermission() {
        PermissionListener permissionListener = new PermissionListener() {
            @Override
            public void onPermissionGranted() {
                // 권한 요청 성공
                isPermission = true;
            }
            @Override
            public void onPermissionDenied(ArrayList<String> deniedPermissions) {
                // 권한 요청 실패
                isPermission = false;
            }
        };
        TedPermission.with(this)
                .setPermissionListener(permissionListener)
                .setRationaleMessage(getResources().getString(R.string.permission_2))
                .setDeniedMessage(getResources().getString(R.string.permission_1))
                .setPermissions(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA)
                .check();
    }

}
