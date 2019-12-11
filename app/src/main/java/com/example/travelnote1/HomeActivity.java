package com.example.travelnote1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.Picasso;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Dictionary;

public class HomeActivity extends AppCompatActivity {

    private ArrayList<Note> arrayList; // 어댑터에 들어갈 list
    private NoteAdapter noteAdapter;
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private String travel_id;
    private BootstrapButton button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Intent intent = getIntent();
        final String travel_title = intent.getStringExtra("travel_title");
        final String travel_img = intent.getStringExtra("travel_img");
        travel_id = intent.getStringExtra("travel_id");
        final String travel_date = intent.getStringExtra("travel_date");
        final String position = intent.getStringExtra("position");
        Log.e("id",travel_id);
        Log.d("값 확인","제목 : " +travel_title + " 이미지 uri : " + travel_img);

        button = (BootstrapButton) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getBaseContext(), TravelmodifyActivity.class);
                intent.putExtra("travel_title", travel_title);
                intent.putExtra( "travel_img", travel_img);
                intent.putExtra("travel_id",travel_id);
                intent.putExtra("travel_date",travel_date);
                intent.putExtra("position",position);

                startActivity(intent);

            }
        });
        loadData();

        // 리사이클러뷰에 LinearLayoutManager 객체 지정.
        recyclerView = (RecyclerView)findViewById(R.id.recycle);
        //수평(Horizontal) 방향으로 아이템을 배치
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        noteAdapter = new NoteAdapter(this, arrayList);
        recyclerView.setAdapter(noteAdapter);

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), recyclerView, new ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Note note = arrayList.get(position);
                //Toast.makeText(getApplicationContext(), dict.getId()+' '+dict.getEnglish()+' '+dict.getKorean(), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onLongClick(View view, int position) {
            }
        }));

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

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()){
//            case android.R.id.home:{ //toolbar의 back키 눌렀을 때 동작
//                finish();
//                return true;
//            }
//        }
//        return super.onOptionsItemSelected(item);
//    }

    // 데이터 들고오기 oncreate에 선언 mExampleList = new ArrayList<>(); 지우고
    private void loadData() {
        SharedPreferences sharedPreferences = getSharedPreferences("shared", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("daily"+travel_id, null);
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

                Note mainData = new Note(a,b,c,e,d,travel_id);
                arrayList.add(mainData); // 내용 추가
                noteAdapter.notifyDataSetChanged(); // 새로고침해 반영

                SharedPreferences sharedPreferences = getSharedPreferences("shared", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                Gson gson = new Gson();
                String json = gson.toJson(arrayList); // 리스트 객체를 json으로 변형
                editor.putString("daily"+travel_id, json);
                editor.apply();


            }
        }
    }

    public interface ClickListener {
        void onClick(View view, int position);

        void onLongClick(View view, int position);
    }

    public static class RecyclerTouchListener implements RecyclerView.OnItemTouchListener {

        private GestureDetector gestureDetector;
        private HomeActivity.ClickListener clickListener;

        public RecyclerTouchListener(Context context, final RecyclerView recyclerView, final HomeActivity.ClickListener clickListener) {
            this.clickListener = clickListener;
            gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    return true;
                }

                @Override
                public void onLongPress(MotionEvent e) {
                    View child = recyclerView.findChildViewUnder(e.getX(), e.getY());
                    if (child != null && clickListener != null) {
                        clickListener.onLongClick(child, recyclerView.getChildAdapterPosition(child));
                    }
                }
            });
        }

        @Override
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
            View child = rv.findChildViewUnder(e.getX(), e.getY());
            if (child != null && clickListener != null && gestureDetector.onTouchEvent(e)) {
                clickListener.onClick(child, rv.getChildAdapterPosition(child));
            }
            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView rv, MotionEvent e) {
        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {
        }
    }

}

