package com.example.travelnote1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.Picasso;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ArrayList<Travel> arrayList; // 어댑터에 들어갈 list
    private TravelAdapter travelAdapter;
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private TextView tv_name;

    private ImageView ad;
    int[] imageArray = {R.drawable.ad1, R.drawable.ad2, R.drawable.ad3, R.drawable.ad4, R.drawable.ad5,R.drawable.ad6};
    String[] linkArray = {"http://www.modetourc.com/event/plan/detail.aspx?mloc=07&eidx=10128&siteno=4608",
            "http://www.modetourc.com/event/plan/detail.aspx?mloc=07&eidx=6873&siteno=4608",
            "http://www.modetourc.com/event/plan/detail.aspx?mloc=07&eidx=9796&siteno=4608",
            "http://www.modetourc.com/event/plan/detail.aspx?mloc=07&eidx=9299&siteno=4608",
            "http://www.modetourc.com/event/plan/detail.aspx?mloc=07&eidx=9184&siteno=4608",
            "http://www.modetourc.com/event/plan/detail.aspx?mloc=07&eidx=10195&siteno=4608"};
    int index = 0;
    Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loadData();

        // 회원 닉네임 불러오기
        tv_name = (TextView)findViewById(R.id.tv_name);
        SharedPreferences sharedPreferences = getSharedPreferences("shared", MODE_PRIVATE);
        String Useremail = sharedPreferences.getString("CurrentUser",""); // 꺼내오는 것이기 때문에 빈칸
        String User = sharedPreferences.getString(Useremail,"");
        Gson gson = new Gson();
        Person person = gson.fromJson(User,Person.class);
        tv_name.setText(person.getEt_name());

        ad = (ImageView)findViewById(R.id.ad);
        //Picasso.with(this).load(R.drawable.ad2).into(ad);
        //ad.setImageResource(R.drawable.ad2);

        (new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                while (!Thread.interrupted())
                    try
                    {
                        Thread.sleep(3000);
                        runOnUiThread(new Runnable() // start actions in UI thread
                        {
                            @Override
                            public void run()
                            {
                                ad.setImageResource(getCurrentlist());
                            }
                        });
                    }
                    catch (InterruptedException e)
                    {
                        // ooops
                    }

            }
        })).start();


        // 리사이클러뷰에 LinearLayoutManager 객체 지정.
        recyclerView = (RecyclerView)findViewById(R.id.recycle);
        //수평(Horizontal) 방향으로 아이템을 배치
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        // 리사이클러뷰에 표시할 데이터 리스트 생성.
        //arrayList = new ArrayList<>();

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

//        Travel mainData1 = new Travel("file:///storage/sdcard1/Gallery/new/praha.PNG", "유럽여행 28박 29일","2019.11.12");
//        Travel mainData2 = new Travel("file:///com.android.providers.media.documents/document/image%3A210691", "대만여행 3박 4일","2018.01.12");
//        arrayList.add(mainData1); // 내용 추가
//        arrayList.add(mainData2); // 내용 추가
//        travelAdapter.notifyDataSetChanged();

        // 폴더 추가 시
        Intent intent = getIntent();
        String title = intent.getStringExtra("title");
        String imageUri = intent.getStringExtra("imageUri");
        String date = intent.getStringExtra("date");
        //Toast.makeText(getApplicationContext(), "제목은 "+title, Toast.LENGTH_SHORT).show();
        if(title!=null&&imageUri!=null&&date!=null) {
            Travel mainData = new Travel(imageUri, title, date);
            arrayList.add(mainData); // 내용 추가
            travelAdapter.notifyDataSetChanged();

            // 현재 회원의 email 불러오기
            String Useremail1 = sharedPreferences.getString("CurrentUser",""); // 꺼내오는 것이기 때문에 빈칸

            // 데이터 저장하기
            SharedPreferences.Editor editor = sharedPreferences.edit();
            Gson gson1 = new Gson();
            String json = gson1.toJson(arrayList); // 리스트 객체를 json으로 변형

            // 회원 email + list로 리사이클러뷰 key값 생성하기(자기만 보는 일기)
            String list_name = Useremail1+"list";
            editor.putString(list_name, json);
            editor.apply();
        }
        //travelAdapter.notifyItemInserted(0);

        // 폴더가 없을 때 이미지 출력하기
        ImageView emptyData = (ImageView) findViewById(R.id.empty);
        if(travelAdapter.getItemCount()==0){
            recyclerView.setVisibility(View.GONE);
            emptyData.setVisibility(View.VISIBLE);
        }else{
            recyclerView.setVisibility(View.VISIBLE);
            emptyData.setVisibility(View.GONE);
        }

        // 하단 바
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

    // 광고이미지
    public int getCurrentlist(){
        index++;
        if(index >= imageArray.length){
            index=0;
        }

        return imageArray[index];
    }

    private void loadData() { // 데이터 들고오기 oncreate에 선언 mExampleList = new ArrayList<>(); 지우고
        SharedPreferences sharedPreferences = getSharedPreferences("shared", MODE_PRIVATE);
        Gson gson = new Gson();
        // 현재 회원의 email 불러오기
        String Useremail = sharedPreferences.getString("CurrentUser",""); // 꺼내오는 것이기 때문에 빈칸
        String list_name = Useremail+"list";
        String json = sharedPreferences.getString(list_name, null);
        Type type = new TypeToken<ArrayList<Travel>>() {}.getType();
        arrayList = gson.fromJson(json, type);

        if (arrayList == null) { // 비어있으면 객체 선언
            arrayList = new ArrayList<>();
        }
    }

}
