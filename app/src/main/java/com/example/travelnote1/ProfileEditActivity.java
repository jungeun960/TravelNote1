package com.example.travelnote1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.beardedhen.androidbootstrap.BootstrapEditText;
import com.example.travelnote1.R;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

public class ProfileEditActivity extends AppCompatActivity {

    private static final int REQUEST_CODE = 0;
    private ImageView imageView;
    private BootstrapEditText et_name;
    private Bitmap img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_edit);

        imageView = findViewById(R.id.image);

        BootstrapButton btn_save = (BootstrapButton)findViewById(R.id.btn_save);

        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = getIntent();
                et_name = findViewById(R.id.et_name);
                intent.putExtra("name",et_name.getText().toString());

                //intent.putExtra("img",img);
                //ByteArrayOutputStream stream = new ByteArrayOutputStream();
                //img.compress(Bitmap.CompressFormat.PNG, 100, stream);
                //byte[] bytes = stream.toByteArray();
                //intent.putExtra("BMP",bytes);
                //Log.i("사진", String.valueOf(bytes));

               // startActivity(intent);
                // finish();
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                img.compress(Bitmap.CompressFormat.PNG, 100, stream);
                byte[] bytes = stream.toByteArray();
                intent.putExtra("img",bytes);

                setResult(RESULT_OK,intent); // 추가 정보를 넣은 후 다시 인텐트를 반환합니다.
                finish(); // 액티비티 종료


            }
        });

        imageView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent, REQUEST_CODE);
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

                    img = BitmapFactory.decodeStream(in);
                    in.close();


                    imageView.setImageBitmap(img);
                } catch (Exception e) {

                }
            } else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(this, "사진 선택 취소", Toast.LENGTH_LONG).show();
            }
        }
    }
}
