package com.example.travelnote1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.beardedhen.androidbootstrap.BootstrapEditText;
import com.example.travelnote1.HomeActivity;
import com.example.travelnote1.R;

import java.io.InputStream;

public class NoteActivity extends AppCompatActivity {

        private ImageView travel_image;
        private BootstrapEditText Note_day;
        private BootstrapEditText Note_title;
        private BootstrapEditText Note_location;
        private BootstrapEditText Note_note;
        private static final int REQUEST_CODE = 0;
        private Bitmap img;
        private Uri photoUri;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_note);

            travel_image = findViewById(R.id.travel_image);
            Note_day = findViewById(R.id.Note_day);
            Note_title = findViewById(R.id.Note_title);
            Note_location = findViewById(R.id.Note_location);
            Note_note = findViewById(R.id.Note_note);

            BootstrapButton button = (BootstrapButton)findViewById(R.id.btn_travel);

            travel_image.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent();
                    intent.setType("image/*");
                    intent.setAction(Intent.ACTION_GET_CONTENT);
                    startActivityForResult(intent, REQUEST_CODE);
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
                    Uri imageUri = photoUri;
                    Log.i("일기 추가", day + title + location + note);
                    intent.putExtra("day",day);
                    intent.putExtra("title",title);
                    intent.putExtra("location",location);
                    intent.putExtra("note",note);
                    intent.putExtra("image",imageUri.toString());

                    setResult(RESULT_OK,intent); // 추가 정보를 넣은 후 다시 인텐트를 반환합니다.
                    finish(); // 액티비티 종료
                }
            });

        }


        @Override
        protected void onActivityResult(int requestCode, int resultCode, Intent data) {
            super.onActivityResult(requestCode, resultCode, data);
            if (requestCode == REQUEST_CODE) {
                if (resultCode == RESULT_OK) {
                    try {
                        InputStream in = getContentResolver().openInputStream(data.getData());
                        photoUri = data.getData();
                        Log.e("갤러리 진입","경로 : "+photoUri);

                        img = BitmapFactory.decodeStream(in);
                        in.close();
                        travel_image.setImageBitmap(img);
                    } catch (Exception e) {

                    }
                } else if (resultCode == RESULT_CANCELED) {
                    Toast.makeText(this, "사진 선택 취소", Toast.LENGTH_LONG).show();
                }
            }
        }
    }
