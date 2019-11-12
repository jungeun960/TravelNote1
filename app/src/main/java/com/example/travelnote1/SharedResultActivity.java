package com.example.travelnote1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class SharedResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shared_result);

        String name = "";
        String title = "";
        String content = "";
        int profile = 0;

        Bundle extras = getIntent().getExtras();

        name = extras.getString("name");
        title = extras.getString("title");
        content = extras.getString("content");
        profile = extras.getInt("profile");

        TextView vname = (TextView) findViewById(R.id.re_name);
        TextView vtitle = (TextView) findViewById(R.id.re_title);
        TextView vcontent = (TextView) findViewById(R.id.re_content);
        ImageView vprofile = (ImageView)findViewById(R.id.re_profile);

        vname.setText(name);
        vtitle.setText(title);
        vcontent.setText(content);
        vprofile.setImageResource(profile);
    }
}
