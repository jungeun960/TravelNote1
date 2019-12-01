package com.example.travelnote1;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.beardedhen.androidbootstrap.BootstrapCircleThumbnail;
import com.beardedhen.androidbootstrap.BootstrapEditText;
import com.example.travelnote1.R;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;

public class ProfileEditActivity extends AppCompatActivity {

    private static final int REQUEST_CODE = 0;
    private BootstrapCircleThumbnail photo;
    private BootstrapEditText et_name;
    private Bitmap img;
    private TextView email;

    private static final String TAG = "Photo";
    private Boolean isPermission = true;
    private static final int PICK_FROM_ALBUM = 1;
    private File tempFile;
    private Uri image_uri;

    private String a;
    private String b;
    private String c;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_edit);

        //이메일 받아오기
        Intent intent = getIntent();
        a = intent.getStringExtra("email");
        b = intent.getStringExtra("image");
        c = intent.getStringExtra("name");
        email = (TextView)findViewById(R.id.email);
        photo = findViewById(R.id.photo);
        et_name = (BootstrapEditText)findViewById(R.id.et_name);
        email.setText(a);
        Picasso.with(this).load(Uri.parse(b)).into(photo);
        et_name.setText(c);

        // 수정하기 버튼
        BootstrapButton btn_save = (BootstrapButton)findViewById(R.id.btn_save);
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),ProfileActivity.class);
                startActivity(intent);
                //회원정보 불러오기
                SharedPreferences sharedPreferences = getSharedPreferences("shared", MODE_PRIVATE);
                String Useremail = sharedPreferences.getString("CurrentUser",""); // 꺼내오는 것이기 때문에 빈칸
                String User = sharedPreferences.getString(Useremail,"");
                Gson gson = new Gson();
                Person person = gson.fromJson(User,Person.class);
                String email = person.getEt_email();

                //Person 클래스에 회원정보 담기
                Person person1 = new Person();
                person1.setEt_email(person.getEt_email());
                if(et_name.getText().toString()==""){
                    person1.setEt_name(c);
                }else {
                    person1.setEt_name(et_name.getText().toString());
                }
                person1.setEt_pass(person.getEt_pass());
                if(image_uri.toString()==""){
                    person1.setEt_name(b);
                }else {
                    person1.setPhoto(image_uri.toString());
                }
                // Gson 인스턴스 생성
                gson = new GsonBuilder().create();
                // JSON 으로 변환
                String strContact = gson.toJson(person1, Person.class);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                // 이메일을 key로 설정
                editor.putString(email, strContact); // JSON으로 변환한 객체를 저장한다.
                editor.commit(); //완료한다.

                finish(); // 액티비티 종료

            }
        });

        // 사진 클릭 시
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
