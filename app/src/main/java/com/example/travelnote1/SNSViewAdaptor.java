package com.example.travelnote1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class SNSViewAdaptor extends BaseAdapter {

    /* 데이터 그릇들의 집합을 정의 */
    private ArrayList<SNSViewItem> mItems = new ArrayList<>();

    @Override
    public int getCount() {
        return mItems.size();
    }

    @Override
    public SNSViewItem getItem(int position) {
        return mItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Context context = parent.getContext();

        // 커스텀 리스트뷰의 xml을 inflate
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.listview_custom, parent, false);
        }

        /* 커스텀 리스트뷰 xml에 있는 속성값들을 정의 */
        TextView blog_title = (TextView) convertView.findViewById(R.id.blog_title);
        TextView blog_description = (TextView) convertView.findViewById(R.id.blog_description);
        TextView blogger_name = (TextView) convertView.findViewById(R.id.blogger_name);
        TextView post_date = (TextView) convertView.findViewById(R.id.post_date);


        /* 데이터를 담는 그릇 정의 */
        SNSViewItem snsViewItem = getItem(position);

        /* 해당 그릇에 담긴 정보들을 커스텀 리스트뷰 xml의 각 TextView에 뿌려줌 */
        blog_title.setText(snsViewItem.getTitle());
        blog_description.setText(snsViewItem.getDescription());
        blogger_name.setText(snsViewItem.getBloggername());
        post_date.setText(snsViewItem.getPostdate());

        return convertView;
    }

    /* 네이버 블로그 검색 중, 제목, 내용, 블로거이름, 포스팅 일자, 포스트 링크를 그릇에 담음 */
    public void addItem(String title, String description, String bloggername, String postdate, String link) {

        SNSViewItem mItem = new SNSViewItem();

        mItem.setTitle(title);
        mItem.setDescription(description);
        mItem.setBloggername(bloggername);
        mItem.setPostdate(postdate);
        mItem.setBloglink(link);

        /* 데이터그릇 mItem에 담음 */
        mItems.add(mItem);

    }
}

