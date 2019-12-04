package com.example.travelnote1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.Picasso;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class HomeActivity extends AppCompatActivity {

    private ArrayList<Note> arrayList; // 어댑터에 들어갈 list
    private NoteAdapter noteAdapter;
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private String travel_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Intent intent = getIntent();
        String travel_title = intent.getStringExtra("travel_title");
        String travel_img = intent.getStringExtra("travel_img");
        travel_id = intent.getStringExtra("travel_id");
        Log.d("값 확인","제목 : " +travel_title + " 이미지 uri : " + travel_img);

        loadData();

        // 리사이클러뷰에 LinearLayoutManager 객체 지정.
        recyclerView = (RecyclerView)findViewById(R.id.recycle);
        //수평(Horizontal) 방향으로 아이템을 배치
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        noteAdapter = new NoteAdapter(this, arrayList);
        recyclerView.setAdapter(noteAdapter);

        TextView title = (TextView) findViewById(R.id.title);
        ImageView img = (ImageView)findViewById(R.id.imageView2);

        title.setText(travel_title);
        //Glide.with(this).load(travel_img).into(img);
        Picasso.with(this).load(travel_img).into(img);
        //Glide.with(this).load(R.drawable.me).into(img);
        //Glide를 이용하여 이미지뷰에 url에 있는 이미지를 세팅해줌


        BootstrapButton button = (BootstrapButton)findViewById(R.id.button2);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(getApplicationContext(), NoteActivity.class);
                startActivityForResult(intent2,1);
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:{ //toolbar의 back키 눌렀을 때 동작
                finish();
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    // 데이터 들고오기 oncreate에 선언 mExampleList = new ArrayList<>(); 지우고
    private void loadData() {
        SharedPreferences sharedPreferences = getSharedPreferences("shared", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString(travel_id, null);
        Type type = new TypeToken<ArrayList<Note>>() {}.getType();
        arrayList = gson.fromJson(json, type);

        if (arrayList == null) { // 비어있으면 객체 선언
            arrayList = new ArrayList<>();
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 1){
            if (resultCode == Activity.RESULT_OK){

                String a = data.getStringExtra("day");
                String b = data.getStringExtra("title");
                String c = data.getStringExtra("location");
                String d = data.getStringExtra("note");
                String e = data.getStringExtra("image");

                Log.e("LOG", "날짜 :"+a +"제목: "+ b + "위치: " + c + "내용: "+d +"이미지 uri: "+e );

                Note mainData = new Note(a,b,c,e,d);
                arrayList.add(mainData); // 내용 추가
                noteAdapter.notifyDataSetChanged(); // 새로고침해 반영

                SharedPreferences sharedPreferences = getSharedPreferences("shared", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                Gson gson = new Gson();
                String json = gson.toJson(arrayList); // 리스트 객체를 json으로 변형
                editor.putString(travel_id, json);
                editor.apply();


            }
        }
    }
}
