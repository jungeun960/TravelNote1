package com.example.travelnote1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.beardedhen.androidbootstrap.BootstrapCircleThumbnail;
import com.example.travelnote1.Comment;
import com.example.travelnote1.CommentAdapter;
import com.example.travelnote1.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.Picasso;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class SharedResultActivity extends AppCompatActivity {

    private ArrayList<Comment> arrayList; // 어댑터에 들어갈 list
    private CommentAdapter commentAdapter;
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private EditText add_comment;
    private Button post;
    private String date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shared_result);

        // 상단 게시물 생성
        String name = "";
        String title = "";
        String content = "";
        String profile = "";

        Bundle extras = getIntent().getExtras();

        // 게시글 내용 받아오기
        name = extras.getString("name");
        title = extras.getString("title");
        content = extras.getString("content");
        profile = extras.getString("profile");
        date = extras.getString("date");

        loadData();

        TextView vname = (TextView) findViewById(R.id.re_name);
        TextView vtitle = (TextView) findViewById(R.id.re_title);
        TextView vcontent = (TextView) findViewById(R.id.re_content);
        ImageView vprofile = (ImageView)findViewById(R.id.re_profile);

        vname.setText(name);
        vtitle.setText(title);
        vcontent.setText(content);
        Picasso.with(this).load(Uri.parse(profile)).into(vprofile);

        // 댓글 리사이클러뷰 생성
        // 리사이클러뷰에 LinearLayoutManager 객체 지정.
        recyclerView = (RecyclerView)findViewById(R.id.recycle);
        //수평(Horizontal) 방향으로 아이템을 배치
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        // 리사이클러뷰에 표시할 데이터 리스트 생성.
        //arrayList = new ArrayList<>();

        // 리사이클러뷰에 shareAdapter 객체 지정.
        commentAdapter = new CommentAdapter(this, arrayList);
        recyclerView.setAdapter(commentAdapter);

//        Comment mainData1 = new Comment(R.mipmap.ic_launcher, "최복치","화이트 에펠 강추합니다!");
//        Comment mainData2 = new Comment(R.mipmap.ic_launcher, "절미","1일1에펠 하세요");
//        arrayList.add(mainData1); // 내용 추가
//        arrayList.add(mainData2);
//        commentAdapter.notifyDataSetChanged();



        // 댓글창 회원 프로필 불러오기
        BootstrapCircleThumbnail current_img = (BootstrapCircleThumbnail)findViewById(R.id.current_img);
        SharedPreferences sharedPreferences = getSharedPreferences("shared", MODE_PRIVATE);
        String Useremail = sharedPreferences.getString("CurrentUser",""); // 꺼내오는 것이기 때문에 빈칸
        String User = sharedPreferences.getString(Useremail,"");
        Gson gson = new Gson();
        Person person = gson.fromJson(User,Person.class);
        String img = person.getPhoto();
        Picasso.with(this).load(Uri.parse(img)).into(current_img);

        // 댓글 작성
        post = (Button)findViewById(R.id.post);
        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                add_comment = findViewById(R.id.add_comment);
                if(add_comment.getText().toString().equals("")){
                    Toast.makeText(getApplicationContext(), "댓글을 입력해주세요.",Toast.LENGTH_SHORT).show();
                }else {

                    // 회원정보 불러오기
                    SharedPreferences sharedPreferences = getSharedPreferences("shared", MODE_PRIVATE);
                    String Useremail = sharedPreferences.getString("CurrentUser",""); // 꺼내오는 것이기 때문에 빈칸
                    String User = sharedPreferences.getString(Useremail,"");
                    Gson gson = new Gson();
                    Person person = gson.fromJson(User,Person.class);
                    String image = person.getPhoto();
                    String name = person.getEt_name();

                    String comment = add_comment.getText().toString();
                    Comment new_commment = new Comment(image, name, comment);
                    arrayList.add(new_commment);
                    commentAdapter.notifyDataSetChanged();
                    add_comment.setText(null);

                    // 데이터 저장하기
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    Gson gson1 = new Gson();
                    String json = gson1.toJson(arrayList); // 리스트 객체를 json으로 변형

                    // 게시글의 생성 날짜를 key값으로 보내기
                    editor.putString(date, json);
                    editor.apply();

                }

            }
        });

    }

    private void loadData() { // 데이터 들고오기 oncreate에 선언 mExampleList = new ArrayList<>(); 지우고
        SharedPreferences sharedPreferences = getSharedPreferences("shared", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString(date, null);
        Type type = new TypeToken<ArrayList<Comment>>() {}.getType();
        arrayList = gson.fromJson(json, type);

        if (arrayList == null) { // 비어있으면 객체 선언
            arrayList = new ArrayList<>();
        }
    }
}
