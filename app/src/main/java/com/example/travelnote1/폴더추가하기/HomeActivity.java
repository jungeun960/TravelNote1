package com.example.travelnote1.폴더추가하기;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.bumptech.glide.Glide;
import com.example.travelnote1.R;
import com.example.travelnote1.공유하기.Share;
import com.example.travelnote1.공유하기.ShareAdapter;
import com.example.travelnote1.공유하기.Shared1Activity;
import com.example.travelnote1.공유하기.SharedActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {

    private ArrayList<Note> arrayList; // 어댑터에 들어갈 list
    private NoteAdapter noteAdapter;
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // 리사이클러뷰에 LinearLayoutManager 객체 지정.
        recyclerView = (RecyclerView)findViewById(R.id.recycle);
        //수평(Horizontal) 방향으로 아이템을 배치
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        // 리사이클러뷰에 표시할 데이터 리스트 생성.
        arrayList = new ArrayList<>();

        noteAdapter = new NoteAdapter(this, arrayList);
        recyclerView.setAdapter(noteAdapter);

        Note mainData = new Note("DAY-1 2019.01.10","스위스 페러글라이딩", "스위스 인터라켄",
                "content://com.android.providers.media.documents/document/image%3A210692","페러글라이딩 존잼이다ㅠ");
        Note mainData1 = new Note("DAY-2 2019.01.11","비온다ㅠㅠ", "루체른",
                "content://com.android.providers.media.documents/document/image%3A210693","비와서 숙소에만 있는다ㅠㅠ 근데 숙소가 넘 이뿜..");
        arrayList.add(mainData); // 내용 추가
        arrayList.add(mainData1);
        noteAdapter.notifyDataSetChanged();
        //Glide.with(this).load(R.drawable.me).into(img);


        Intent intent = getIntent();
        String travel_title = intent.getStringExtra("travel_title");
        String travel_img = intent.getStringExtra("travel_img");
        Log.d("값 확인","제목 : " +travel_title + " 이미지 uri : " + travel_img);

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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 1){
            if (resultCode == Activity.RESULT_OK){

                String a = data.getStringExtra("day");
                String b = data.getStringExtra("title");
                String c = data.getStringExtra("location");
                String d = data.getStringExtra("note");
                String e = data.getStringExtra("image");

                Log.e("LOG", "결과 제목 a :"+a +"내용 b : "+b +e );

                Note mainData = new Note(a,b,c,e,d);
                arrayList.add(mainData); // 내용 추가
                noteAdapter.notifyDataSetChanged(); // 새로고침해 반영

            }
        }
    }
}
