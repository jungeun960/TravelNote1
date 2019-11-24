package com.example.travelnote1.폴더추가하기;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.travelnote1.Person;
import com.example.travelnote1.R;
import com.example.travelnote1.유튜브.YoutubeActivity;
import com.example.travelnote1.공유하기.SharedActivity;
import com.example.travelnote1.ProfileActivity;
import com.google.gson.Gson;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ArrayList<Travel> arrayList; // 어댑터에 들어갈 list
    private TravelAdapter travelAdapter;
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private TextView tv_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv_name = (TextView)findViewById(R.id.tv_name);
        // 불러오기
        SharedPreferences sharedPreferences = getSharedPreferences("shared", MODE_PRIVATE);
        String Useremail = sharedPreferences.getString("CurrentUser",""); // 꺼내오는 것이기 때문에 빈칸
        String User = sharedPreferences.getString(Useremail,"");
        Gson gson = new Gson();
        Person person = gson.fromJson(User,Person.class);
        tv_name.setText(person.getEt_name());



        // 리사이클러뷰에 LinearLayoutManager 객체 지정.
        recyclerView = (RecyclerView)findViewById(R.id.recycle);
        //수평(Horizontal) 방향으로 아이템을 배치
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        // 리사이클러뷰에 표시할 데이터 리스트 생성.
        arrayList = new ArrayList<>();

        // 리사이클러뷰에 mainAdapter 객체 지정.
        travelAdapter = new TravelAdapter(this, arrayList);
        recyclerView.setAdapter(travelAdapter);
        travelAdapter.setOnItemClickListener(new TravelAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                Log.i("메인", "클릭"+position);
                Travel travel = arrayList.get(position);
                //Toast.makeText(getApplicationContext(),
                //        share.getTv_name()+share.getTv_title()+share.getTv_cotent(), Toast.LENGTH_LONG).show();
                Intent intent = new Intent(getBaseContext(), HomeActivity.class);
                intent.putExtra("travel_title", travel.getTravel_name());
                intent.putExtra( "travel_img", travel.getImageUrl());

                startActivity(intent);
            }
        });


//        Travel mainData1 = new Travel("content://com.android.providers.media.documents/document/image%3A210690", "유럽여행 28박 29일","2019.11.12");
//        Travel mainData2 = new Travel("content://com.android.providers.media.documents/document/image%3A210691", "대만여행 3박 4일","2018.01.12");
//        arrayList.add(mainData1); // 내용 추가
//        arrayList.add(mainData2); // 내용 추가
//        travelAdapter.notifyDataSetChanged();

        Intent intent = getIntent();
        String title = intent.getStringExtra("title");
        String imageUri = intent.getStringExtra("imageUri");
        String date = intent.getStringExtra("date");
        //Toast.makeText(getApplicationContext(), "제목은 "+title, Toast.LENGTH_SHORT).show();
        Travel mainData = new Travel(imageUri, title,date);
        arrayList.add(mainData); // 내용 추가
        travelAdapter.notifyDataSetChanged();
        //travelAdapter.notifyItemInserted(0);

//        emptyData = (ImageView) findViewById(R.id.empty);
//        if(commentAdapter.getItemCount()==0){
//            recyclerView.setVisibility(View.GONE);
//            emptyData.setVisibility(View.VISIBLE);
//        }else{
//            recyclerView.setVisibility(View.VISIBLE);
//            emptyData.setVisibility(View.GONE);
//        }

//        TextView empty = (TextView)findViewById(R.id.empty);
//        if(travelAdapter.getItemCount()==0){
//            empty.setVisibility(View.VISIBLE);
//        }else{
//            empty.setVisibility(View.GONE);
//        }

        ImageButton button1 = (ImageButton)findViewById(R.id.btn_home);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(getApplicationContext(),MainActivity.class);
                intent1.addFlags (Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent1);
            }
        });

        ImageButton button2 = (ImageButton)findViewById(R.id.btn_shared);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(getApplicationContext(), SharedActivity.class);
                intent2.addFlags (Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent2);
            }
        });

        ImageButton button3 = (ImageButton)findViewById(R.id.btn_add);
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent3 = new Intent(getApplicationContext(), AddActivity.class);
                startActivity(intent3);
            }
        });

        ImageButton button4 = (ImageButton)findViewById(R.id.btn_profile);
        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent4 = new Intent(getApplicationContext(), ProfileActivity.class);
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
    }

}
