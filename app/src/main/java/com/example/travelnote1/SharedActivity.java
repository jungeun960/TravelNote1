package com.example.travelnote1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.beardedhen.androidbootstrap.BootstrapEditText;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class SharedActivity extends AppCompatActivity {

    private ArrayList<Share> arrayList; // 어댑터에 들어갈 list
    private ShareAdapter shareAdapter;
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;

    // 크롤링
    private String htmlPageUrl = "http://www.hanatour.com/"; //파싱할 홈페이지의 URL주소 하나 투어
    private TextView textviewHtmlDocument;
    private TextView rank;
    private String htmlContentInStringFormat="";
    private String[] list;
    private int i =0;
    private int j =0;

    int cnt=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shared);

        loadData();

        // 리사이클러뷰에 LinearLayoutManager 객체 지정.
        recyclerView = (RecyclerView)findViewById(R.id.rv);
        //수평(Horizontal) 방향으로 아이템을 배치
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        // 리사이클러뷰에 표시할 데이터 리스트 생성.
        //arrayList = new ArrayList<>();

        // 리사이클러뷰에 shareAdapter 객체 지정.
        shareAdapter = new ShareAdapter(this, arrayList);
        recyclerView.setAdapter(shareAdapter);


        // 크롤링 데이터 띄우기
        rank = (TextView)findViewById(R.id.rank);
        textviewHtmlDocument = (TextView)findViewById(R.id.place);
        textviewHtmlDocument.setMovementMethod(new ScrollingMovementMethod()); //스크롤 가능한 텍스트뷰로 만들기

        System.out.println( (cnt+1) +"번째 파싱");
        JsoupAsyncTask jsoupAsyncTask = new JsoupAsyncTask();
        jsoupAsyncTask.execute();
        cnt++;


        (new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                while (!Thread.interrupted())
                    try
                    {
                        Thread.sleep(2000);
                        runOnUiThread(new Runnable() // start actions in UI thread
                        {
                            @Override
                            public void run()
                            {
                                textviewHtmlDocument.setText(getCurrentlist());
                                rank.setText(getrank());
                            }
                        });
                    }
                    catch (InterruptedException e)
                    {
                        // ooops
                    }
            }
        })).start();

        // 검색기능 - 텍스트 입력이 되면
        BootstrapEditText editText = findViewById(R.id.edittext);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                shareAdapter.getFilter().filter(s);
            }
            @Override
            public void afterTextChanged(Editable s) {
                //filter(s.toString());
            }
        });

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                linearLayoutManager.getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), recyclerView, new ClickListener() {
            @Override
            // 아이템 클릭 시
            public void onClick(View view, int position) {
//                Log.i("메인", "클릭"+position);
//                Share share = arrayList.get(position);
//                //Toast.makeText(getApplicationContext(),
//                //        share.getTv_name()+share.getTv_title()+share.getTv_cotent(), Toast.LENGTH_LONG).show();
//                // 인텐트 ResultActivity로 값 넘기기
//                Intent intent = new Intent(getBaseContext(), SharedResultActivity.class);
//                intent.putExtra("name", share.getTv_name());
//                intent.putExtra( "title", share.getTv_title());
//                intent.putExtra("content", share.getTv_cotent());
//                intent.putExtra("profile",share.getIv_profile());
//                intent.putExtra("date",share.getDate());
//                startActivity(intent);
            }

            @Override
            public void onLongClick(View view, int position) {
            }
        }));

//        shareAdapter.setOnItemClickListener(new ShareAdapter.OnItemClickListener() {
//            @Override
//            public void onItemClick(View v, int position) {
//                Log.i("메인", "클릭"+position);
//                Share share = arrayList.get(position);
//                //Toast.makeText(getApplicationContext(),
//                //        share.getTv_name()+share.getTv_title()+share.getTv_cotent(), Toast.LENGTH_LONG).show();
//                        // 인텐트 ResultActivity로 값 넘기기
//                Intent intent = new Intent(getBaseContext(),SharedResultActivity.class);
//                intent.putExtra("name", share.getTv_name());
//                intent.putExtra( "title", share.getTv_title());
//                intent.putExtra("content", share.getTv_cotent());
//                intent.putExtra("profile",share.getIv_profile());
//                startActivity(intent);
//
//            }
//        }) ;


        // 하단 메뉴
        BootstrapButton button = (BootstrapButton)findViewById(R.id.button2);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Shared1Activity.class);
                //startActivity(intent);
                startActivityForResult(intent,1);
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
                Intent intent2 = new Intent(getApplicationContext(),SharedActivity.class);
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

    private class JsoupAsyncTask extends AsyncTask<Void, Void, Void> {

        // 백그라운드 작업이 시작되기 전 호출
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }


        // pre 메소드 실행 후 동작
        @Override
        protected Void doInBackground(Void... params) {
            try {

                Document doc = Jsoup.connect(htmlPageUrl).get();
                Elements titles;
                titles= doc.select("div.vote_keyword"); // 하나투어 한달간 인기 키워드 top5 view-source:http://www.hanatour.com/

                System.out.println("-------------------------------------------------------------");
                for(Element e: titles){
                    //System.out.println("title: " + e.text() + "\n");
                    htmlContentInStringFormat += e.text().trim() + "\n";
                }
                htmlContentInStringFormat = htmlContentInStringFormat.replace("인기키워드 한 달간 인기키워드","");
                System.out.println(htmlContentInStringFormat + "\n");
                list = htmlContentInStringFormat.split("\\s");

                for (String li : list){
                    System.out.println(li);
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        // 백그라운드 작업이 완료 된 후 결과값 얻음
        @Override
        protected void onPostExecute(Void result) {
            //textviewHtmlDocument.setText(htmlContentInStringFormat);
        }
    }

    // 인기 여행지 리스트 반환
    public String getCurrentlist(){
        if(i==5)
            i=0;
        i++;
        return list[i];
    }

    public String getrank(){
        if(j==5)
            j=0;
        j++;

        return Integer.toString(j);
    }

    // 데이터 들고오기 oncreate에 선언 mExampleList = new ArrayList<>(); 지우고
    private void loadData() {
        SharedPreferences sharedPreferences = getSharedPreferences("shared", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("sharelist", null);
        Type type = new TypeToken<ArrayList<Share>>() {}.getType();
        arrayList = gson.fromJson(json, type);

        if (arrayList == null) { // 비어있으면 객체 선언
            arrayList = new ArrayList<>();
        }
    }

    // 공유 게시글 추가하기
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 1){
            if (resultCode == Activity.RESULT_OK){

                String a = data.getStringExtra("title");
                String b = data.getStringExtra("context");
                Log.e("LOG", "결과 제목 a :"+a +"내용 b : "+b );

                // 회원정보 불러오기
                SharedPreferences sharedPreferences = getSharedPreferences("shared", MODE_PRIVATE);
                String Useremail = sharedPreferences.getString("CurrentUser",""); // 꺼내오는 것이기 때문에 빈칸
                String User = sharedPreferences.getString(Useremail,"");
                Gson gson = new Gson();
                Person person = gson.fromJson(User,Person.class);
                String name = person.getEt_name();
                String profile = person.getPhoto();

                // 작성일 구하기
                SimpleDateFormat format1 = new SimpleDateFormat ( "MM/dd HH:mm:ss");
                Date time = new Date();
                String time1 = format1.format(time);

                Share mainData = new Share(profile, name,b, a,time1,0);
                arrayList.add(mainData); // 내용 추가
                shareAdapter.notifyDataSetChanged(); // 새로고침해 반영

                SharedPreferences.Editor editor = sharedPreferences.edit();
                Gson gson1 = new Gson();
                String json = gson1.toJson(arrayList); // 리스트 객체를 json으로 변형
                editor.putString("sharelist", json);
                //editor.putString("photo",)
                editor.apply();



                //Toast.makeText(getApplicationContext(),"제목 :"+a+"내용 :" + b,Toast.LENGTH_LONG).show();


            }
        }
    }

    public interface ClickListener {
        void onClick(View view, int position);

        void onLongClick(View view, int position);
    }

    public static class RecyclerTouchListener implements RecyclerView.OnItemTouchListener {

        private GestureDetector gestureDetector;
        private SharedActivity.ClickListener clickListener;

        public RecyclerTouchListener(Context context, final RecyclerView recyclerView, final SharedActivity.ClickListener clickListener) {
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

// 검색하기 필터
//    private void filter(String text) {
//        ArrayList<Share> filteredList = new ArrayList<>();
//
//        for (Share item : arrayList) {
//            if (item.getTv_cotent().toLowerCase().contains(text.toLowerCase())) {
//                filteredList.add(item);
//            }
//        }
//
//        shareAdapter.filterList(filteredList);
//    }

}
