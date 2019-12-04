package com.example.travelnote1;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.beardedhen.androidbootstrap.BootstrapEditText;
import com.google.gson.Gson;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

public class TravelAdapter extends RecyclerView.Adapter<TravelAdapter.CustomViewHolder> {

    //어댑터에 들어갈 list
    private ArrayList<Travel> arrayList;
    private Context context;
    Activity activity;

    private BootstrapButton btn_modify;
    private ImageView travel_image;
    private BootstrapEditText travel_title;

    private static final String TAG = "Photo";
    private Boolean isPermission = true;
    private static final int PICK_FROM_ALBUM = 1;
    private File tempFile;
    private Uri image_uri;


    public TravelAdapter(Activity act, ArrayList<Travel> arrayList) {
        // 생성자에서 데이터 리스트 객체를 전달받음.
        this.activity = act;
        this.arrayList = arrayList;

    }

    public class CustomViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener {
        // 아이템 뷰를 저장하는 뷰홀더 클래스.

        protected ImageView travel_image;
        protected TextView travel_name;
        protected TextView travel_date;

        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);
            this.travel_image = (ImageView) itemView.findViewById(R.id.travel_image);
            this.travel_name = (TextView) itemView.findViewById(R.id.travel_name);
            this.travel_date = (TextView) itemView.findViewById(R.id.travel_date);

            itemView.setOnCreateContextMenuListener(this);
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
                        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                        // 다이얼로그를 보여주기 위해 edit_box.xml 파일을 사용합니다.
                        View view = LayoutInflater.from(activity)
                                .inflate(R.layout.activity_travel_modify, null, false);
                        builder.setView(view);
                        btn_modify = (BootstrapButton) view.findViewById(R.id.btn_travel);
                        travel_image = (ImageView) view.findViewById(R.id.travel_image);
                        travel_title = (BootstrapEditText) view.findViewById(R.id.travel_title);

                        // 6. 해당 줄에 입력되어 있던 데이터를 불러와서 다이얼로그에 보여줍니다.
                        travel_title.setText(arrayList.get(getAdapterPosition()).getTravel_name());
                        Picasso.with(activity).load(arrayList.get(getAdapterPosition()).getImageUrl()).into(travel_image);

                        tedPermission();

                        // 갤러리 진입
                        travel_image.setOnClickListener(new View.OnClickListener(){
                            @Override
                            public void onClick(View v) {
                                // 권한 허용에 동의하지 않았을 경우 토스트를 띄웁니다.
                                if(isPermission) { //앨범에서 이미지 가져오기
                                    Intent intent = new Intent(Intent.ACTION_PICK);
                                    intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
                                    activity.startActivityForResult(intent, PICK_FROM_ALBUM);
                                    //goToAlbum();
                                }
                                //else
                                //Toast.makeText(view.getContext(), getResources().getString(R.string.permission_2), Toast.LENGTH_LONG).show();
                            }
                        });
                        final AlertDialog dialog = builder.create();
                        btn_modify.setOnClickListener(new View.OnClickListener() {
                            // 7. 수정 버튼을 클릭하면 현재 UI에 입력되어 있는 내용으로
                            public void onClick(View v) {
                                String title = travel_title.getText().toString();
                                String date = arrayList.get(getAdapterPosition()).getTravel_date();
                                String id = arrayList.get(getAdapterPosition()).getId();
                                String img = arrayList.get(getAdapterPosition()).getImageUrl();

                                Travel travel = new Travel(img,title,date,id);
                                arrayList.set(getAdapterPosition(), travel);
                                notifyItemChanged(getAdapterPosition());


                                SharedPreferences sharedPreferences = activity.getSharedPreferences("shared", MODE_PRIVATE);
                                // 현재 회원의 email 불러오기
                                String Useremail = sharedPreferences.getString("CurrentUser",""); // 꺼내오는 것이기 때문에 빈칸
                                String list_name = Useremail+"list";

                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                Gson gson = new Gson();
                                String json = gson.toJson(arrayList); // 리스트 객체를 json으로 변형
                                editor.putString(list_name, json);
                                editor.apply();

                                dialog.dismiss();
                            }
                        });
                        dialog.show();
                        break;
                    case 1002:  // 5. 삭제 항목을 선택시

                        AlertDialog.Builder builder1 = new AlertDialog.Builder(activity);
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
                                        Toast.makeText(activity, "삭제되었습니다.", Toast.LENGTH_SHORT).show();

                                        SharedPreferences sharedPreferences = activity.getSharedPreferences("shared", MODE_PRIVATE);

                                        // 현재 회원의 email 불러오기
                                        String Useremail = sharedPreferences.getString("CurrentUser",""); // 꺼내오는 것이기 때문에 빈칸
                                        String list_name = Useremail+"list";

                                        SharedPreferences.Editor editor = sharedPreferences.edit();
                                        Gson gson1 = new Gson();
                                        String json = gson1.toJson(arrayList); // 리스트 객체를 json으로 변형

                                        editor.putString(list_name, json);
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
    public TravelAdapter.CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list2, parent, false);
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
    public void onBindViewHolder(@NonNull final TravelAdapter.CustomViewHolder holder, final int position) {
        // position에 해당하는 데이터를 뷰홀더의 아이템뷰에 표시.
        //Uri uri = Uri.parse(arrayList.get(position).getImageUrl());
        //holder.travel_image.setImageURI(uri);
        Picasso.with(activity).load(arrayList.get(position).getImageUrl()).into(holder.travel_image);
        //Glide.with(activity).load(arrayList.get(position).getImageUrl()).into(holder.travel_image);
        holder.travel_name.setText(arrayList.get(position).getTravel_name()); // 내용 가져오기
        holder.travel_date.setText(arrayList.get(position).getTravel_date());

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

    public void remove(int position){
        // 삭제
        try{
            arrayList.remove(position); // arrayList에서 제거
            notifyItemRemoved(position);// 새로고침해 지워줌

            SharedPreferences sharedPreferences = activity.getSharedPreferences("shared", MODE_PRIVATE);

            // 현재 회원의 email 불러오기
            String Useremail = sharedPreferences.getString("CurrentUser",""); // 꺼내오는 것이기 때문에 빈칸
            String list_name = Useremail+"list";

            SharedPreferences.Editor editor = sharedPreferences.edit();
            Gson gson1 = new Gson();
            String json = gson1.toJson(arrayList); // 리스트 객체를 json으로 변형

            editor.putString(list_name, json);
            editor.apply();

        }catch (IndexOutOfBoundsException ex){
            ex.printStackTrace();
        }
    }



    @SuppressLint("MissingSuperCall")
    //@Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK) {
            Toast.makeText(activity, "취소 되었습니다.", Toast.LENGTH_SHORT).show();

            if(tempFile != null) {
                if (tempFile.exists()) {
                    if (tempFile.delete()) {
                        Log.e(TAG, tempFile.getAbsolutePath() + " 삭제 성공");
                        tempFile = null;
                    }
                }
            }
            return;
        }

        if (requestCode == PICK_FROM_ALBUM) {

            Uri photoUri = data.getData();
            Log.e(TAG, "PICK_FROM_ALBUM photoUri (Uri photoUri = data.getData()): " + photoUri);

            Cursor cursor = null;
            try {
                // Uri 스키마를  content:/// 에서 file:/// 로  변경한다.
                String[] proj = {MediaStore.Images.Media.DATA};
                assert photoUri != null;
                cursor = activity.getContentResolver().query(photoUri, proj, null, null, null);
                assert cursor != null;
                int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                cursor.moveToFirst();

                tempFile = new File(cursor.getString(column_index));
                Log.e(TAG, "tempFile Uri (Uri.fromFile(tempFile)): " + Uri.fromFile(tempFile));
                image_uri = Uri.fromFile(tempFile);

            } finally {
                if (cursor != null) {
                    cursor.close();
                }
            }
            //ImageView imageView = findViewById(R.id.travel_image);
            Picasso.with(activity).load(image_uri).into(travel_image);
            Log.e(TAG, "tempFile : " + tempFile);
            Log.e(TAG, "tempFile.getAbsolutePath() : " + tempFile.getAbsolutePath());
            tempFile = null;
        }
    }

    /**
     *  권한 설정
     */
    private void tedPermission() {
        PermissionListener permissionListener = new PermissionListener() {
            @Override
            public void onPermissionGranted() {
                // 권한 요청 성공
                isPermission = true;
            }
            @Override
            public void onPermissionDenied(ArrayList<String> deniedPermissions) {
                // 권한 요청 실패
                isPermission = false;
            }
        };
        TedPermission.with(activity)
                .setPermissionListener(permissionListener)
                .setRationaleMessage(activity.getResources().getString(R.string.permission_2))
                .setDeniedMessage(activity.getResources().getString(R.string.permission_1))
                .setPermissions(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA)
                .check();
    }


}