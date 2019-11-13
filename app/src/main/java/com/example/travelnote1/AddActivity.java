package com.example.travelnote1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AddActivity extends AppCompatActivity {

    private ImageView travel_image;
    private EditText travel_title;
    private static final int REQUEST_CODE = 0;
    private Bitmap img;
    private Uri photoUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        travel_image = findViewById(R.id.travel_image);
        travel_title = findViewById(R.id.travel_title);
        Button button = (Button)findViewById(R.id.btn_travel);

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
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);

                // 작성일 구하기
                SimpleDateFormat format1 = new SimpleDateFormat ( "yyyy-MM-dd HH:mm:ss");
                Date time = new Date();
                String time1 = format1.format(time);

                String title = travel_title.getText().toString();
                Uri imageUri = photoUri;
                Log.i("여행 추가", "제목 : "+title +"이미지 uri :"+imageUri);
                intent.putExtra("title",title);
                intent.putExtra("imageUri",imageUri.toString());
                intent.putExtra("date",time1);

                startActivity(intent);
                finish();
            }
        });


    }

    private String getRealPathFromURI(Uri contentURI){
        String result;
        Cursor cursor = getContentResolver().query(contentURI, null, null, null,null);
        if(cursor == null){
            result = contentURI.getPath();
        }
        else{
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            result = cursor.getString(idx);
            cursor.close();
        }
        return  result;
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

