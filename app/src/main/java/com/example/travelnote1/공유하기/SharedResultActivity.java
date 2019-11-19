package com.example.travelnote1.공유하기;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.SharedMemory;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.travelnote1.R;

import java.util.ArrayList;

public class SharedResultActivity extends AppCompatActivity {

    private ArrayList<Comment> arrayList; // 어댑터에 들어갈 list
    private CommentAdapter commentAdapter;
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shared_result);

        // 리사이클러뷰에 LinearLayoutManager 객체 지정.
        recyclerView = (RecyclerView)findViewById(R.id.recycle);
        //수평(Horizontal) 방향으로 아이템을 배치
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        // 리사이클러뷰에 표시할 데이터 리스트 생성.
        arrayList = new ArrayList<>();

        // 리사이클러뷰에 shareAdapter 객체 지정.
        commentAdapter = new CommentAdapter(this, arrayList);
        recyclerView.setAdapter(commentAdapter);

        Comment mainData1 = new Comment(R.mipmap.ic_launcher, "최복치","화이트 에펠 강추합니다!");
        Comment mainData2 = new Comment(R.mipmap.ic_launcher, "절미","1일1에펠 하세요");
        arrayList.add(mainData1); // 내용 추가
        arrayList.add(mainData2);
        commentAdapter.notifyDataSetChanged();

        String name = "";
        String title = "";
        String content = "";
        int profile = 0;
        int post_position = 0;

        Bundle extras = getIntent().getExtras();

        name = extras.getString("name");
        title = extras.getString("title");
        content = extras.getString("content");
        profile = extras.getInt("profile");
        post_position = extras.getInt("position");

        TextView vname = (TextView) findViewById(R.id.re_name);
        TextView vtitle = (TextView) findViewById(R.id.re_title);
        TextView vcontent = (TextView) findViewById(R.id.re_content);
        ImageView vprofile = (ImageView)findViewById(R.id.re_profile);

        vname.setText(name);
        vtitle.setText(title);
        vcontent.setText(content);
        vprofile.setImageResource(profile);

    }
}
