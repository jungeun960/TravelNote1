package com.example.travelnote1.유튜브;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.example.travelnote1.R;
import com.example.travelnote1.YoutubeAdapter;
import com.example.travelnote1.공유하기.SharedActivity;
import com.example.travelnote1.유튜브.DataSetList;
import com.example.travelnote1.폴더추가하기.AddActivity;
import com.example.travelnote1.폴더추가하기.MainActivity;
import com.example.travelnote1.프로필.ProfileActivity;

import java.util.ArrayList;

public class YoutubeActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ArrayList<DataSetList> arrayList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_youtube);

        recyclerView = findViewById(R.id.recycle);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        arrayList = new ArrayList<DataSetList>();

        //DataSetList dataSetList = new DataSetList("https://www.youtube.com/watch?v=er6Y3uWNX0g");
        DataSetList dataSetList = new DataSetList("https://www.youtube.com/embed/aFFQzvKe-Gg");
        arrayList.add(dataSetList);
        dataSetList = new DataSetList("https://www.youtube.com/embed/er6Y3uWNX0g");
        arrayList.add(dataSetList);
        dataSetList = new DataSetList("https://www.youtube.com/embed/hOuTkv13skU");
        arrayList.add(dataSetList);
        dataSetList = new DataSetList("https://www.youtube.com/embed/njp1YLEIN0k");
        arrayList.add(dataSetList);
        dataSetList = new DataSetList("https://www.youtube.com/embed/bnwo13Vsitg");
        arrayList.add(dataSetList);
        dataSetList = new DataSetList("https://www.youtube.com/embed/VekIL6sVO-s");
        arrayList.add(dataSetList);
        dataSetList = new DataSetList("https://www.youtube.com/embed/cCcPdTyClmA");
        arrayList.add(dataSetList);

        YoutubeAdapter youtubeAdapter = new YoutubeAdapter(arrayList,getApplicationContext());
        recyclerView.setAdapter(youtubeAdapter);

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
}
