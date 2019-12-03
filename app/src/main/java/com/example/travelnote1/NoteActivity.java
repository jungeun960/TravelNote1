package com.example.travelnote1;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.beardedhen.androidbootstrap.BootstrapEditText;
import com.example.travelnote1.HomeActivity;
import com.example.travelnote1.R;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;

public class NoteActivity extends AppCompatActivity {

    private ImageView travel_image;
    private BootstrapEditText Note_day;
    private BootstrapEditText Note_title;
    private BootstrapEditText Note_location;
    private BootstrapEditText Note_note;

    private static final String TAG = "Photo";
    private Boolean isPermission = true;
    private static final int PICK_FROM_ALBUM = 1;
    private File tempFile;
    private Uri image_uri;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);

        tedPermission();

        travel_image = findViewById(R.id.travel_image);
        Note_day = findViewById(R.id.Note_day);
        Note_title = findViewById(R.id.Note_title);
        Note_location = findViewById(R.id.Note_location);
        Note_note = findViewById(R.id.Note_note);

        BootstrapButton button = (BootstrapButton)findViewById(R.id.btn_travel);

        travel_image.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                // 권한 허용에 동의하지 않았을 경우 토스트를 띄웁니다.
                if (isPermission) { //앨범에서 이미지 가져오기
                    Intent intent = new Intent(Intent.ACTION_PICK);
                    intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
                    startActivityForResult(intent, PICK_FROM_ALBUM);
                }
            }
        });

            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getApplicationContext(), HomeActivity.class);

                    String day = Note_day.getText().toString();
                    String title = Note_title.getText().toString();
                    String location = Note_location.getText().toString();
                    String note = Note_note.getText().toString();
                    Log.i("일기 추가", day + title + location + note);
                    intent.putExtra("day",day);
                    intent.putExtra("title",title);
                    intent.putExtra("location",location);
                    intent.putExtra("note",note);
                    intent.putExtra("image",image_uri.toString());

                    setResult(RESULT_OK,intent); // 추가 정보를 넣은 후 다시 인텐트를 반환합니다.
                    finish(); // 액티비티 종료
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
            //ImageView imageView = findViewById(R.id.travel_image);
            Picasso.with(this).load(image_uri).into(travel_image);
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

