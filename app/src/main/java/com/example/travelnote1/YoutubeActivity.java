package com.example.travelnote1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.beardedhen.androidbootstrap.BootstrapCircleThumbnail;
import com.beardedhen.androidbootstrap.BootstrapEditText;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class YoutubeActivity extends AppCompatActivity {

    ArrayList<DataSetList> arrayList;
    private RecyclerView recyclerView;
    private YoutubeAdapter youtubeAdapter;
    private LinearLayoutManager linearLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_youtube);

        loadData();

        recyclerView = (RecyclerView)findViewById(R.id.recycle);
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);
        //arrayList = new ArrayList<DataSetList>();
        youtubeAdapter = new YoutubeAdapter(arrayList,getApplicationContext());
        recyclerView.setAdapter(youtubeAdapter);


//        //DataSetList dataSetList = new DataSetList("https://www.youtube.com/watch?v=er6Y3uWNX0g");
//        DataSetList dataSetList = new DataSetList("https://www.youtube.com/embed/aFFQzvKe-Gg");
//        arrayList.add(dataSetList);
//        dataSetList = new DataSetList("https://www.youtube.com/embed/er6Y3uWNX0g");
//        arrayList.add(dataSetList);
//        dataSetList = new DataSetList("https://www.youtube.com/embed/hOuTkv13skU");
//        arrayList.add(dataSetList);
//        dataSetList = new DataSetList("https://www.youtube.com/embed/njp1YLEIN0k");
//        arrayList.add(dataSetList);
//        dataSetList = new DataSetList("https://www.youtube.com/embed/bnwo13Vsitg");
//        arrayList.add(dataSetList);
//        dataSetList = new DataSetList("https://www.youtube.com/embed/VekIL6sVO-s");
//        arrayList.add(dataSetList);
//        dataSetList = new DataSetList("https://www.youtube.com/embed/cCcPdTyClmA");
//        arrayList.add(dataSetList);
//
//        final YoutubeAdapter youtubeAdapter = new YoutubeAdapter(arrayList,getApplicationContext());
//        recyclerView.setAdapter(youtubeAdapter);

        BootstrapButton btn_link_add = (BootstrapButton) findViewById(R.id.btn_link_add);
        btn_link_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BootstrapEditText et_link = (BootstrapEditText)findViewById(R.id.et_link);
                String link = et_link.getText().toString();
                if(link.equals("")){
                    Toast.makeText(getApplicationContext(), "링크를 입력해주세요.",Toast.LENGTH_SHORT).show();
                }else {
                    String link_full = "https://www.youtube.com/embed/" + link;
                    //String link_full =link;
                    arrayList.add(new DataSetList(link_full));
                    youtubeAdapter.notifyItemInserted(arrayList.size());
                    et_link.setText(null);
                    Toast.makeText(getApplicationContext(), "영상이 추가되었습니다.",Toast.LENGTH_SHORT).show();

                    // 데이터 저장하기
                    SharedPreferences sharedPreferences = getSharedPreferences("shared", MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    Gson gson = new Gson();
                    String json = gson.toJson(arrayList); // 리스트 객체를 json으로 변형
                    editor.putString("youtube_link", json);
                    editor.apply();
                }

            }
        });

        ImageButton button1 = (ImageButton)findViewById(R.id.btn_home);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(getApplicationContext(), MainActivity.class);
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

    private void loadData() { // 데이터 들고오기 oncreate에 선언 mExampleList = new ArrayList<>(); 지우고
        SharedPreferences sharedPreferences = getSharedPreferences("shared", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("youtube_link", null);
        Type type = new TypeToken<ArrayList<DataSetList>>() {}.getType();
        arrayList = gson.fromJson(json, type);

        if (arrayList == null) { // 비어있으면 객체 선언
            arrayList = new ArrayList<>();
        }
    }

}
