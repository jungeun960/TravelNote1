package com.example.travelnote1;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.net.Uri;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.beardedhen.androidbootstrap.BootstrapCircleThumbnail;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

public class ShareAdapter extends RecyclerView.Adapter<ShareAdapter.CustomViewHolder>
    implements Filterable {
    // 어뎁터 구현 시 필수 생성 메서드
    //onCreateViewHolder() : 뷰홀더 객체 생성.
    //onBindViewHolder() : 데이터를 뷰홀더에 바인딩.
    //getItemCount() : 전체 아이템 갯수 리턴.

    //어댑터에 들어갈 list
    private ArrayList<Share> arrayList;
    ArrayList<Share> filteredList;
    Activity activity;
    private Context mContext;


//    public void filterList(ArrayList<Share> filteredList) {
//        arrayList = filteredList;
//        notifyDataSetChanged();
//    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String charString = constraint.toString();
                if(charString.isEmpty()) {
                    arrayList = filteredList;
                } else {
                    ArrayList<Share> filteringList = new ArrayList<>();
                    for(Share item : filteredList) {
                        if(item.getTv_cotent().toLowerCase().contains(charString.toLowerCase())) {
                            filteringList.add(item);
                        }
                    }
                    arrayList = filteringList;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = arrayList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                arrayList = (ArrayList<Share>)results.values;
                notifyDataSetChanged();
            }
        };
    }

    // 1. 컨텍스트 메뉴를 사용하라면 RecyclerView.ViewHolder를 상속받은 클래스에서
    // OnCreateContextMenuListener 리스너를 구현해야 합니다.
    public class CustomViewHolder extends RecyclerView.ViewHolder
                implements View.OnCreateContextMenuListener {
        // 아이템 뷰를 저장하는 뷰홀더 클래스.

        protected BootstrapCircleThumbnail iv_profile;
        protected TextView tv_name;
        protected TextView tv_content;
        protected TextView tv_title;

        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);
            // 뷰 객체에 대한 참조. (hold strong reference)
            this.iv_profile=(BootstrapCircleThumbnail) itemView.findViewById(R.id.iv_profile);
            this.tv_name=(TextView)itemView.findViewById(R.id.tv_name);
            this.tv_content=(TextView)itemView.findViewById(R.id.tv_content);
            this.tv_title=(TextView)itemView.findViewById(R.id.tv_title);
            itemView.setOnCreateContextMenuListener(this);
            //2. OnCreateContextMenuListener 리스너를 현재 클래스에서 구현한다고 설정해둡니다.
        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            // 3. 컨텍스트 메뉴를 생성하고 메뉴 항목 선택시 호출되는 리스너를 등록해줍니다.
            // ID 1001, 1002로 어떤 메뉴를 선택했는지 리스너에서 구분하게 됩니다.
            MenuItem Edit = menu.add(Menu.NONE, 1001, 1, "편집");
            MenuItem Delete = menu.add(Menu.NONE, 1002, 2, "삭제");
            Edit.setOnMenuItemClickListener(onEditMenu);
            Delete.setOnMenuItemClickListener(onEditMenu);
        }

        // 4. 컨텍스트 메뉴에서 항목 클릭시 동작을 설정합니다.
        private final MenuItem.OnMenuItemClickListener onEditMenu = new MenuItem.OnMenuItemClickListener() {

            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case 1001:  // 5. 편집 항목을 선택시
                        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                        // 다이얼로그를 보여주기 위해 edit_box.xml 파일을 사용합니다.
                        View view = LayoutInflater.from(mContext)
                                .inflate(R.layout.activity_sharedmodify, null, false);
                        builder.setView(view);
                        final Button btn_modify = (Button) view.findViewById(R.id.btn_modify);
                        final EditText et_title = (EditText) view.findViewById(R.id.et_title);
                        final EditText et_context = (EditText) view.findViewById(R.id.et_context);

                        // 6. 해당 줄에 입력되어 있던 데이터를 불러와서 다이얼로그에 보여줍니다.
                        et_title.setText(arrayList.get(getAdapterPosition()).getTv_title());
                        et_context.setText(arrayList.get(getAdapterPosition()).getTv_cotent());

                        final AlertDialog dialog = builder.create();
                        btn_modify.setOnClickListener(new View.OnClickListener() {
                            // 7. 수정 버튼을 클릭하면 현재 UI에 입력되어 있는 내용으로
                            public void onClick(View v) {
                                String strtitle = et_title.getText().toString();
                                String strcontext = et_context.getText().toString();


                                Share share = new Share("content://com.android.providers.media.documents/document/image%3A210690","홓홓",strcontext,strtitle);

                                // 8. ListArray에 있는 데이터를 변경하고
                                arrayList.set(getAdapterPosition(), share);
                                // 9. 어댑터에서 RecyclerView에 반영하도록 합니다.
                                notifyItemChanged(getAdapterPosition());
                                dialog.dismiss();
                            }
                        });
                        dialog.show();
                        break;
                    case 1002:  // 5. 삭제 항목을 선택시

                        AlertDialog.Builder builder1 = new AlertDialog.Builder(mContext);
                        builder1.setTitle("삭제");
                        builder1.setMessage("글을 삭제하시겠습니까?");
                        builder1.setPositiveButton("예",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        //remove(getAdapterPosition()); // 제거
                                        arrayList.remove(getAdapterPosition());
                                        // 7. 어댑터에서 RecyclerView에 반영하도록 합니다.
                                        // 6. ArratList에서 해당 데이터를 삭제하고
                                        notifyItemRemoved(getAdapterPosition());
                                        notifyItemRangeChanged(getAdapterPosition(), arrayList.size());
                                        Toast.makeText(mContext,"삭제되었습니다.",Toast.LENGTH_SHORT).show();

                                        SharedPreferences sharedPreferences = mContext.getSharedPreferences("shared", MODE_PRIVATE);
                                        SharedPreferences.Editor editor = sharedPreferences.edit();
                                        Gson gson1 = new Gson();
                                        String json = gson1.toJson(arrayList); // 리스트 객체를 json으로 변형
                                        editor.putString("sharelist", json);
                                        editor.apply();

                                    }
                                });
                        builder1.setNegativeButton("아니오",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        //Toast.makeText(activity,"아니오를 선택했습니다.",Toast.LENGTH_LONG).show();
                                    }
                                });
                        builder1.show();
                        break;
                }
                return true;
            }
        };
    }

    public ShareAdapter(Context context, ArrayList<Share> arrayList){
        // 생성자에서 데이터 리스트 객체를 전달받음.
        this.arrayList = arrayList;
        this.mContext = context;
        this.filteredList = arrayList;
    }

//    public ShareAdapter(Activity act, ArrayList<Share> arrayList){
//        // 생성자에서 데이터 리스트 객체를 전달받음.
//        this.arrayList = arrayList;
//        this.activity = act;
//    }

    public interface OnItemClickListener {
        void onItemClick(View v, int position) ;
    }

    // 리스너 객체 참조를 저장하는 변수
    private OnItemClickListener mListener = null ;

    // OnItemClickListener 리스너 객체 참조를 어댑터에 전달하는 메서드
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mListener = listener ;
    }

    @NonNull
    @Override
    public ShareAdapter.CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // viewType 형태의 아이템 뷰를 위한 뷰홀더 객체 생성하여 리턴
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list,parent, false);
        CustomViewHolder holder = new CustomViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ShareAdapter.CustomViewHolder holder, final int position) {
        // position에 해당하는 데이터를 뷰홀더의 아이템뷰에 표시.

        Uri uri = Uri.parse(arrayList.get(position).getIv_profile());
        holder.iv_profile.setImageURI(uri);

        //Picasso.with(activity).load(arrayList.get(position).getIv_profile()).into(holder.iv_profile);
        //holder.iv_profile.setImageResource(arrayList.get(position).getIv_profile()); // 이미지 가져오기
        holder.tv_name.setText(arrayList.get(position).getTv_name()); // 이름 가져오기
        holder.tv_content.setText(arrayList.get(position).getTv_cotent()); // 내용 가져오기
        holder.tv_title.setText(arrayList.get(position).getTv_title());

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

//        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
//            //아이템 길게 클릭시
//            @Override
//            public boolean onLongClick(View v) {
//
//                AlertDialog.Builder builder = new AlertDialog.Builder(activity);
//                builder.setTitle("삭제");
//                builder.setMessage("글을 삭제하시겠습니까?");
//                builder.setPositiveButton("예",
//                        new DialogInterface.OnClickListener() {
//                            public void onClick(DialogInterface dialog, int which) {
//                                remove(holder.getAdapterPosition()); // 제거
//                                Toast.makeText(activity,"삭제되었습니다.",Toast.LENGTH_SHORT).show();
//                            }
//                        });
//                builder.setNegativeButton("아니오",
//                        new DialogInterface.OnClickListener() {
//                            public void onClick(DialogInterface dialog, int which) {
//                                //Toast.makeText(activity,"아니오를 선택했습니다.",Toast.LENGTH_LONG).show();
//                            }
//                        });
//                builder.show();
//                return true;
//
//            }
//        });
    }

    @Override
    public int getItemCount() {
        // 전체 아이템 갯수 리턴.
        return (null != arrayList ? arrayList.size() : 0);
    }

//    public void remove(int position){
//        // 삭제
//        try{
//            arrayList.remove(position); // arrayList에서 제거
//            notifyItemRemoved(position);// 새로고침해 지워줌
//        }catch (IndexOutOfBoundsException ex){
//            ex.printStackTrace();
//        }
//    }


}
