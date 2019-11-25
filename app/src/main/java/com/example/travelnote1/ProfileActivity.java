package com.example.travelnote1;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.beardedhen.androidbootstrap.BootstrapCircleThumbnail;
import com.beardedhen.androidbootstrap.TypefaceProvider;
import com.example.travelnote1.유튜브.YoutubeActivity;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

public class ProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        TypefaceProvider.registerDefaultIconSets();

        TextView tv_name = (TextView)findViewById(R.id.tv_name);
        final TextView tv_email = (TextView)findViewById(R.id.tv_email);
        BootstrapCircleThumbnail img = (BootstrapCircleThumbnail) findViewById(R.id.photo);

        // 회원정보 불러오기
        SharedPreferences sharedPreferences = getSharedPreferences("shared", MODE_PRIVATE);
        String Useremail = sharedPreferences.getString("CurrentUser",""); // 꺼내오는 것이기 때문에 빈칸
        String User = sharedPreferences.getString(Useremail,"");
        Gson gson = new Gson();
        Person person = gson.fromJson(User,Person.class);
        tv_name.setText(person.getEt_name());
        tv_email.setText(person.getEt_email());
        String imguri = person.getPhoto();
        //File f = new File(imguri);

        Picasso.with(this).load(Uri.parse(imguri)).into(img);

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

        // 로그아웃
        BootstrapButton logout = (BootstrapButton)findViewById(R.id.logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 현재유저 키 삭제
                SharedPreferences sharedPreferences = getSharedPreferences("shared", MODE_PRIVATE);
                //String Useremail = sharedPreferences.getString("CurrentUser",""); // 꺼내오는 것이기 때문에 빈칸
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.remove("CurrentUser");
                editor.commit();
                Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
                startActivity(intent);
            }
        });

        // 탈퇴하기
        Button out = (Button)findViewById(R.id.out);
        out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                show();
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

        ImageButton button5 = (ImageButton)findViewById(R.id.btn_youtube);
        button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent5 = new Intent(getApplicationContext(), YoutubeActivity.class);
                intent5.addFlags (Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent5);
            }
        });

        // 프로필 수정하기
        BootstrapButton button_edit = (BootstrapButton)findViewById(R.id.btn_edit);
 //       Button button_edit = (Button)findViewById(R.id.btn_edit);
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

//        TextView tv_name = (TextView)findViewById(R.id.tv_name);
//        BootstrapCircleThumbnail img = (BootstrapCircleThumbnail) findViewById(R.id.photo);
//
//        if(resultCode==RESULT_OK) // 액티비티가 정상적으로 종료되었을 경우
//        {
//            if(requestCode==1) // InformationInput에서 호출한 경우에만 처리합니다.
//            {               // 받아온 이름과 전화번호를 InformationInput 액티비티에 표시합니다.
//                tv_name.setText(data.getStringExtra("name"));
//                byte[] bytes = data.getByteArrayExtra("img");
//                Bitmap bmp = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
//                img.setImageBitmap(bmp);
//                //digit_view.setText(data.getStringExtra("data_digit"));
//            }
//        }
    }


    void show()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("탈퇴하기");
        builder.setMessage("정말로 탈퇴하시겠습니까? 탈퇴 시 소중한 기록이 모두 삭제됩니다.");
        builder.setPositiveButton("아니오",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
        builder.setNegativeButton("예",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // 쉐어드에서 사용자 정보 삭제
                        SharedPreferences sharedPreferences = getSharedPreferences("shared", MODE_PRIVATE);
                        String Useremail = sharedPreferences.getString("CurrentUser",""); // 꺼내오는 것이기 때문에 빈칸
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.remove(Useremail);
                        editor.commit();

                        // 로그인 페이지로 이동
                        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                        startActivity(intent);

                        Toast.makeText(getApplicationContext(),"탈퇴합니다.",Toast.LENGTH_LONG).show();
                    }
                });

        builder.show();
    }

}
