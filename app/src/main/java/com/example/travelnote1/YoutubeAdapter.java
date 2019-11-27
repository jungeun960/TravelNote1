package com.example.travelnote1;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

public class YoutubeAdapter extends RecyclerView.Adapter<YoutubeViewHolder>{


    ArrayList<DataSetList> arrayList;
    Context context;

    public YoutubeAdapter(ArrayList<DataSetList> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public YoutubeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.video_per_row, parent,false);
        return new YoutubeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final YoutubeViewHolder holder, int position) {
        final DataSetList current = arrayList.get(position);
        holder.webView.loadUrl(current.getLink());
        holder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, VideoFullScreen.class);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                i.putExtra("link",current.getLink());
                context.startActivity(i);
            }
        });
        holder.trash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                remove(holder.getAdapterPosition()); // 제거
                Toast.makeText(context,"삭제되었습니다.",Toast.LENGTH_SHORT).show();
            }
        });


    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public void remove(int position){
        // 삭제
        try{
            arrayList.remove(position); // arrayList에서 제거
            notifyItemRemoved(position);// 새로고침해 지워줌

            SharedPreferences sharedPreferences = context.getSharedPreferences("shared", MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            Gson gson1 = new Gson();
            String json = gson1.toJson(arrayList); // 리스트 객체를 json으로 변형
            editor.putString("youtube_link", json);
            editor.apply();

        }catch (IndexOutOfBoundsException ex){
            ex.printStackTrace();
        }
    }
}
