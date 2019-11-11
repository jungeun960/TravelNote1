package com.example.travelnote1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import java.util.ArrayList;

public class SharedActivity extends AppCompatActivity {

    private ArrayList<Share> arrayList; // 어댑터에 들어갈 list
    private ShareAdapter shareAdapter;
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shared);

        // 리사이클러뷰에 LinearLayoutManager 객체 지정.
        recyclerView = (RecyclerView)findViewById(R.id.rv);
        //수평(Horizontal) 방향으로 아이템을 배치
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        // 리사이클러뷰에 표시할 데이터 리스트 생성.
        arrayList = new ArrayList<>();

        // 리사이클러뷰에 mainAdapter 객체 지정.
        shareAdapter = new ShareAdapter(arrayList);
        recyclerView.setAdapter(shareAdapter);

        Share mainData1 = new Share(R.mipmap.ic_launcher, "최복치","마카롱 맛집 공유합니다.","파리 3박 4일");
        Share mainData2 = new Share(R.mipmap.ic_launcher, "절미","팬션 추천받습니다","강릉 여행");
        Share mainData3 = new Share(R.mipmap.ic_launcher, "마이크","엄지네 포장마차 강추","속초 맛집 추천");
        arrayList.add(mainData1); // 내용 추가
        arrayList.add(mainData2);
        arrayList.add(mainData3);
        shareAdapter.notifyDataSetChanged();

        Button btn_add = (Button)findViewById(R.id.button2);
        btn_add.setOnClickListener(new View.OnClickListener() {// 추가 버튼 클릭 시
            @Override
            public void onClick(View v) {
                Share mainData = new Share(R.mipmap.ic_launcher, "홍홍","우왕","리사이클러뷰");
                arrayList.add(mainData); // 내용 추가
                shareAdapter.notifyDataSetChanged(); // 새로고침해 반영
            }
        });

//        Button button = (Button)findViewById(R.id.button2);
//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(getApplicationContext(),Shared1Activity.class);
//                startActivity(intent);//ddff
//            }
//        });

        ImageButton button1 = (ImageButton)findViewById(R.id.btn_home);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent1);
            }
        });

        ImageButton button2 = (ImageButton)findViewById(R.id.btn_shared);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(getApplicationContext(),SharedActivity.class);
                startActivity(intent2);
            }
        });

        ImageButton button3 = (ImageButton)findViewById(R.id.btn_add);
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent3 = new Intent(getApplicationContext(),AddActivity.class);
                startActivity(intent3);
            }
        });

        ImageButton button4 = (ImageButton)findViewById(R.id.btn_profile);
        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent4 = new Intent(getApplicationContext(),ProfileActivity.class);
                startActivity(intent4);
            }
        });
    }
}
