package com.example.travelnote1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.beardedhen.androidbootstrap.BootstrapButton;
import com.beardedhen.androidbootstrap.BootstrapEditText;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

public class LoginActivity extends AppCompatActivity {

    private Gson gson;

    private Context mContext;
    private LoginButton btn_facebook_login;
    //private LoginCallback mLoginCallback;
    private CallbackManager mCallbackManager;
    private String facebookuser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        FacebookSdk.sdkInitialize(getApplicationContext());

        mContext = getApplicationContext();
        mCallbackManager = CallbackManager.Factory.create();
        btn_facebook_login = (LoginButton) findViewById(R.id.btn_facebook_login);

        LoginManager.getInstance().registerCallback(mCallbackManager,
                new FacebookCallback<LoginResult>() {
                    // 로그인 성공 시 호출 됩니다. Access Token 발급 성공.
                    @Override
                    public void onSuccess(LoginResult loginResult) {

                        Arrays.asList("public_profile", "email");
                        Log.e("Callback :: ", "onSuccess");

                        requestUserProfile(loginResult);

                    }

                    // 로그인 창을 닫을 경우, 호출됩니다.
                    @Override
                    public void onCancel() {
                        // App code
                        Log.e("Callback :: ", "onCancel");
                    }

                    // 로그인 실패 시에 호출됩니다.
                    @Override
                    public void onError(FacebookException exception) {
                        // App code
                        Log.e("Callback :: ", "onError");
                    }

                });

//        mLoginCallback = new LoginCallback();
//        btn_facebook_login.setReadPermissions(Arrays.asList("public_profile", "email"));
//        btn_facebook_login.registerCallback(mCallbackManager, mLoginCallback);

        final BootstrapEditText et_email =(BootstrapEditText)findViewById(R.id.et_email);
        final BootstrapEditText et_password = (BootstrapEditText)findViewById(R.id.et_password);


        // Hide ActionBar
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        // 로그인한 유저정보가 있는지 확인
        SharedPreferences sharedPreferences = getSharedPreferences("shared", MODE_PRIVATE);
        String value = sharedPreferences.getString("CurrentUser", "");
        Log.e("str",value);
        if(value.isEmpty()){

        }else{

            Intent intent = new Intent(getApplicationContext(),LodingActivity.class);
            startActivity(intent);
            finish();
        }

        BootstrapButton button = (BootstrapButton)findViewById(R.id.btn_login);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = et_email.getText().toString();
                String password = et_password.getText().toString();

                // 회원정보 조회하기
                SharedPreferences sharedPreferences = getSharedPreferences("shared", MODE_PRIVATE);
                String value = sharedPreferences.getString(email, "");
                Log.e("str",value);

                if(value.isEmpty()){
                    Toast.makeText(getApplicationContext(), "존재하지 않는 아이디 입니다.", Toast.LENGTH_SHORT).show();
                    Log.e("str","존재하지 않는 아이디 입니다.");
                }else{
                    Gson gson = new Gson();
                    Person person = gson.fromJson(value, Person.class);
                    String pass = person.getEt_pass();
                    Log.e("str","비밀번호 : " +person.getEt_pass());
                    Log.e("str","내가 누른 비밀번호 : " +password);

                    boolean check = password.equals(pass);
                    if(check==true){
                        //Toast.makeText(getApplicationContext(), "로그인 되었습니다.", Toast.LENGTH_SHORT).show();
                        Log.e("str","로그인 되었습니다.");
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("CurrentUser",email);
                        editor.commit();//save를 완료하라

                        Intent intent2 = new Intent(getApplicationContext(),LodingActivity.class);
                        startActivity(intent2);
                        finish();
                    }else{
                        Toast.makeText(getApplicationContext(), "비밀번호를 다시 확인해주세요.", Toast.LENGTH_SHORT).show();
                        Log.e("str","비밀번호를 다시 확인해주세요.");
                    }
                }
            }
        });

        BootstrapButton button1 = (BootstrapButton)findViewById(R.id.btn_signup);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(getApplicationContext(),SignupActivity.class);
                startActivity(intent1);
                finish();
            }
        });
    }

    public void requestUserProfile(LoginResult loginResult){
        GraphRequest request = GraphRequest.newMeRequest(
                loginResult.getAccessToken(),
                new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        try {
                            facebookuser = response.getJSONObject().getString("email").toString();
                            //String name = response.getJSONObject().getString("name").toString();         // 이름
                            Log.e("Result1", facebookuser);
                            Log.e("Result2", response.getJSONObject().toString());


                            //Person 클래스에 회원정보 담기
                            Person person = new Person();
                            person.setEt_email(facebookuser);
                            person.setEt_name("");
                            person.setEt_pass("");
                            person.setPhoto("file:///storage/sdcard1/Gallery/new/no.PNG");
                            // Gson 인스턴스 생성
                            Gson gson;
                            gson = new GsonBuilder().create();
                            // JSON 으로 변환
                            String strContact = gson.toJson(person, Person.class);

                            SharedPreferences sharedPreferences = getSharedPreferences("shared", MODE_PRIVATE);
                            SharedPreferences sp = getSharedPreferences("shared", MODE_PRIVATE);
                            SharedPreferences.Editor editor = sp.edit();
                            // 이메일을 key로 설정
                            editor.putString(facebookuser, strContact); // JSON으로 변환한 객체를 저장한다.
                            editor.putString("CurrentUser",facebookuser);
                            editor.commit(); //완료한다.

                            Intent intent = new Intent(getApplicationContext(),LodingActivity.class);
                            startActivity(intent);
                            finish();
                            //Log.e("Result", name);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                });
        Bundle parameters = new Bundle();
        parameters.putString("fields", "email");
        request.setParameters(parameters);
        request.executeAsync();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        mCallbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);

    }

}
