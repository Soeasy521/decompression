package com.example.decompression;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    private EditText et_username;
    private EditText et_password;
    private SharedPreferences mSharedPreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //获取SharedPreferences
        mSharedPreferences =getSharedPreferences("user",MODE_PRIVATE);

        //初始化控件
        et_username =findViewById(R.id.et_username);
        et_password =findViewById(R.id.et_password);


      //点击注册
    findViewById(R.id.register).setOnClickListener(new View.OnClickListener(){
        @Override
        public void onClick(View v) {
            //跳转到注册页面
            Intent intent =new Intent(LoginActivity.this,RegisterActivity.class);
            startActivity(intent);

        }

     });

    //登录
      findViewById(R.id.login).setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              String username=et_username.getText().toString();
              String password=et_password.getText().toString();

              if(TextUtils.isEmpty(username) && TextUtils.isEmpty(password)){
                  Toast.makeText(LoginActivity.this, "请输入用户名和密码", Toast.LENGTH_SHORT).show();
              }else {
                  String name = mSharedPreferences.getString("username", null);
                  String pwd = mSharedPreferences.getString("password", null);
                  if(username.equals(name) && password.equals(pwd)){

                    //登录成功
                      Intent intent =new Intent(LoginActivity.this,MainActivity.class);
                      startActivity(intent);
                  }else{
                      Toast.makeText(LoginActivity.this, "用户名或密码错误", Toast.LENGTH_SHORT).show();

                  }
              }
          }
      });

    }
}