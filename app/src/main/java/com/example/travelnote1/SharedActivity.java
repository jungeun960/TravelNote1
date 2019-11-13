package com.example.travelnote1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Dictionary;

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

        // 리사이클러뷰에 shareAdapter 객체 지정.
        shareAdapter = new ShareAdapter(this, arrayList);
        recyclerView.setAdapter(shareAdapter);

        Share mainData1 = new Share(R.mipmap.ic_launcher, "최복치","마카롱 맛집 공유합니다.","파리 3박 4일");
        Share mainData2 = new Share(R.mipmap.ic_launcher, "절미","팬션 추천받습니다","강릉 여행");
        Share mainData3 = new Share(R.mipmap.ic_launcher, "마이크","엄지네 포장마차 강추","속초 맛집 추천");
        arrayList.add(mainData1); // 내용 추가
        arrayList.add(mainData2);
        arrayList.add(mainData3);
        shareAdapter.notifyDataSetChanged();

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                linearLayoutManager.getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), recyclerView, new ClickListener() {
            @Override
            // 아이템 클릭 시
            public void onClick(View view, int position) {
                Log.i("메인", "클릭"+position);
                Share share = arrayList.get(position);
                //Toast.makeText(getApplicationContext(),
                //        share.getTv_name()+share.getTv_title()+share.getTv_cotent(), Toast.LENGTH_LONG).show();
                // 인텐트 ResultActivity로 값 넘기기
                Intent intent = new Intent(getBaseContext(),SharedResultActivity.class);
                intent.putExtra("name", share.getTv_name());
                intent.putExtra( "title", share.getTv_title());
                intent.putExtra("content", share.getTv_cotent());
                intent.putExtra("profile",share.getIv_profile());
                startActivity(intent);
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



//        Button btn_add = (Button)findViewById(R.id.button2);
//        btn_add.setOnClickListener(new View.OnClickListener() {// 추가 버튼 클릭 시
//            @Override
//            public void onClick(View v) {
//                Share mainData = new Share(R.mipmap.ic_launcher, "홍홍","우왕","리사이클러뷰");
//                arrayList.add(mainData); // 내용 추가
//                shareAdapter.notifyDataSetChanged(); // 새로고침해 반영
//            }
//        });

        Button button = (Button)findViewById(R.id.button2);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),Shared1Activity.class);
                //startActivity(intent);
                startActivityForResult(intent,1);
            }
        });

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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 1){
            if (resultCode == Activity.RESULT_OK){

                String a = data.getStringExtra("title");
                String b = data.getStringExtra("context");
                Log.e("LOG", "결과 제목 a :"+a +"내용 b : "+b );
                Share mainData = new Share(R.mipmap.ic_launcher, "홍홍",b, a);
                arrayList.add(mainData); // 내용 추가
                shareAdapter.notifyDataSetChanged(); // 새로고침해 반영

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

}
