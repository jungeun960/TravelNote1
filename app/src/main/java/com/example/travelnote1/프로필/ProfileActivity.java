package com.example.travelnote1.프로필;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.beardedhen.androidbootstrap.TypefaceProvider;
import com.example.travelnote1.폴더추가하기.AddActivity;
import com.example.travelnote1.폴더추가하기.MainActivity;
import com.example.travelnote1.R;
import com.example.travelnote1.공유하기.SharedActivity;

public class ProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        TypefaceProvider.registerDefaultIconSets();
// 웹페이지 이동 , 건의하기
        Button button_web = (Button)findViewById(R.id.btn_Web);
        Button button_Sms = (Button)findViewById(R.id.btn_Sms);


        button_web.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("http://naver.com");
                Intent intent = new Intent(Intent.ACTION_VIEW,uri);
                startActivity(intent);
            }
        });

        button_Sms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_SENDTO);
                intent.setData(Uri.parse("smsto:"+Uri.encode("01084543139")));
                startActivity(intent);
            }
        });

// 하단 버튼
        ImageButton button1 = (ImageButton)findViewById(R.id.btn_home);
        ImageButton button2 = (ImageButton)findViewById(R.id.btn_shared);
        ImageButton button3 = (ImageButton)findViewById(R.id.btn_add);
        ImageButton button4 = (ImageButton)findViewById(R.id.btn_profile);

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(getApplicationContext(), MainActivity.class);
                intent1.addFlags (Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent1);
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(getApplicationContext(), SharedActivity.class);
                intent2.addFlags (Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent2);
            }
        });

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent3 = new Intent(getApplicationContext(), AddActivity.class);
                startActivity(intent3);
            }
        });

        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent4 = new Intent(getApplicationContext(),ProfileActivity.class);
                intent4.addFlags (Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent4);
            }
        });

        // 프로필 수정하기
        BootstrapButton button_edit = (BootstrapButton)findViewById(R.id.btn_edit);
        //Button button_edit = (Button)findViewById(R.id.btn_edit);
        button_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ProfileEditActivity.class);
                startActivityForResult(intent,1);
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        TextView tv_name = (TextView)findViewById(R.id.tv_name);
        ImageView img = (ImageView)findViewById(R.id.imageView);

        if(resultCode==RESULT_OK) // 액티비티가 정상적으로 종료되었을 경우
        {
            if(requestCode==1) // InformationInput에서 호출한 경우에만 처리합니다.
            {               // 받아온 이름과 전화번호를 InformationInput 액티비티에 표시합니다.
                tv_name.setText(data.getStringExtra("name"));
                byte[] bytes = data.getByteArrayExtra("img");
                Bitmap bmp = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                img.setImageBitmap(bmp);
                //digit_view.setText(data.getStringExtra("data_digit"));
            }
        }
    }



}
