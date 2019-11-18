package com.example.travelnote1.폴더추가하기;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.travelnote1.R;
import com.example.travelnote1.공유하기.Share;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.CustomViewHolder> {

    //어댑터에 들어갈 list
    private ArrayList<Note> arrayList;
    //private Context context;
    Activity activity;
    private Context mContext;

    public NoteAdapter(Context context, ArrayList<Note> arrayList){
        // 생성자에서 데이터 리스트 객체를 전달받음.
        this.arrayList = arrayList;
        this.mContext = context;
    }

    public interface OnItemClickListener {
        void onItemClick(View v, int position) ;
    }

    // 리스너 객체 참조를 저장하는 변수
    private OnItemClickListener mListener = null ;

    // OnItemClickListener 리스너 객체 참조를 어댑터에 전달하는 메서드
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mListener = listener ;
    }

    @Override
    public NoteAdapter.CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.noteitem, parent, false);
        CustomViewHolder holder = new CustomViewHolder(view);
        return holder;
    }

    /**
     * gets the image url from adapter and passes to Glide API to load the image
     *
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(@NonNull final NoteAdapter.CustomViewHolder holder, final int position) {
        // position에 해당하는 데이터를 뷰홀더의 아이템뷰에 표시.
        Picasso.with(activity).load(arrayList.get(position).getImageView6()).into(holder.imageView6);
        //Glide.with(activity).load(arrayList.get(position).getImageUrl()).into(holder.travel_image);
        //Glide.with(this).load(R.drawable.me).into(img);
        holder.tv_day.setText(arrayList.get(position).getTv_day());
        holder.tv_title.setText(arrayList.get(position).getTv_title());
        holder.tv_location.setText(arrayList.get(position).getTv_location());
        holder.tv_note.setText(arrayList.get(position).getTv_note());
        holder.itemView.setTag(position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            // 아이템 클릭시
            @Override
            public void onClick(View v) {
//                String curName = holder.tv_name.getText().toString();
//                Toast.makeText(v.getContext(),curName,Toast.LENGTH_SHORT).show(); // 토스트 메세지 보내기
                mListener.onItemClick(v, position) ;
            }
        });

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            //아이템 길게 클릭시
            @Override
            public boolean onLongClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                builder.setTitle("삭제");
                builder.setMessage("글을 삭제하시겠습니까?");
                builder.setPositiveButton("예",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                remove(holder.getAdapterPosition()); // 제거
                                Toast.makeText(mContext,"삭제되었습니다.",Toast.LENGTH_SHORT).show();
                            }
                        });
                builder.setNegativeButton("아니오",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                //Toast.makeText(activity,"아니오를 선택했습니다.",Toast.LENGTH_LONG).show();
                            }
                        });
                builder.show();
                return true;

            }
        });
    }



    @Override
    public int getItemCount() {
        // 전체 아이템 갯수 리턴.
        return (null != arrayList ? arrayList.size() : 0);
    }

    public void remove(int position){
        // 삭제
        try{
            arrayList.remove(position); // arrayList에서 제거
            notifyItemRemoved(position);// 새로고침해 지워줌
        }catch (IndexOutOfBoundsException ex){
            ex.printStackTrace();
        }
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {
        // 아이템 뷰를 저장하는 뷰홀더 클래스.

        protected ImageView imageView6;
        protected TextView tv_day;
        protected TextView tv_title;
        protected TextView tv_location;
        protected TextView tv_note;

        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);
            this.imageView6 = (ImageView)itemView.findViewById(R.id.imageView6);
            this.tv_day = (TextView)itemView.findViewById(R.id.tv_day);
            this.tv_title = (TextView)itemView.findViewById(R.id.tv_title);
            this.tv_location = (TextView)itemView.findViewById(R.id.tv_location);
            this.tv_note = (TextView)itemView.findViewById(R.id.tv_note);
        }
    }
}