package com.example.travelnote1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.beardedhen.androidbootstrap.BootstrapEditText;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class AddActivity extends AppCompatActivity {

    private ImageView travel_image;
    private BootstrapEditText travel_title;
    private static final int REQUEST_CODE = 0;
    private Bitmap img;
    private Uri photoUri;
//    private ArrayList<Travel> arrayList; // 어댑터에 들어갈 list
//    private TravelAdapter travelAdapter;
//    private RecyclerView recyclerView;
//    private LinearLayoutManager linearLayoutManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        travel_image = findViewById(R.id.travel_image);
        travel_title = findViewById(R.id.travel_title);
        BootstrapButton button = (BootstrapButton)findViewById(R.id.btn_travel);

//        // 리사이클러뷰에 LinearLayoutManager 객체 지정.
//        recyclerView = (RecyclerView)findViewById(R.id.recycle);
//        //수평(Horizontal) 방향으로 아이템을 배치
//        linearLayoutManager = new LinearLayoutManager(this);
//        recyclerView.setLayoutManager(linearLayoutManager);
//
//        // 리사이클러뷰에 표시할 데이터 리스트 생성.
//        arrayList = new ArrayList<>();
//
//        // 리사이클러뷰에 mainAdapter 객체 지정.
//        travelAdapter = new TravelAdapter(this, arrayList);
//        recyclerView.setAdapter(travelAdapter);


        // 갤러리 진입
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
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);

                // 작성일 구하기
                SimpleDateFormat format1 = new SimpleDateFormat ( "MM/dd HH:mm");
                Date time = new Date();
                String time1 = format1.format(time);

                String title = travel_title.getText().toString();
                Uri imageUri = photoUri;

//                Travel mainData = new Travel(imageUri.toString(), title,time1);
//                arrayList.add(mainData); // 내용 추가
//                travelAdapter.notifyDataSetChanged();
//
//                // 데이터 저장하기
//                SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
//                SharedPreferences.Editor editor = sharedPreferences.edit();
//                String Useremail = sharedPreferences.getString("CurrentUser",""); // 꺼내오는 것이기 때문에 빈칸
//                String listname = Useremail+"list";
//                Gson gson = new Gson();
//                String json = gson.toJson(arrayList); // 리스트 객체를 json으로 변형
//                editor.putString(listname, json);
//                editor.apply();

                Log.i("여행 추가", "제목 : "+title +"이미지 uri :"+imageUri);
                intent.putExtra("title",title);
                intent.putExtra("imageUri",imageUri.toString());
                intent.putExtra("date",time1);

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
                    Picasso.with(this).load(photoUri).into(travel_image);
                    Log.e("갤러리 진입","경로 : "+photoUri);
                } catch (Exception e) {

                }
            } else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(this, "사진 선택 취소", Toast.LENGTH_LONG).show();
            }
        }
    }
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == REQUEST_CODE) {
//            if (resultCode == RESULT_OK) {
//                try {
//                    InputStream in = getContentResolver().openInputStream(data.getData());
//                    photoUri = data.getData();
//                    Log.e("갤러리 진입","경로 : "+photoUri);
//
//                    img = BitmapFactory.decodeStream(in);
//                    in.close();
//                    travel_image.setImageBitmap(img);
//                } catch (Exception e) {
//
//                }
//            } else if (resultCode == RESULT_CANCELED) {
//                Toast.makeText(this, "사진 선택 취소", Toast.LENGTH_LONG).show();
//            }
//        }
//    }
}

