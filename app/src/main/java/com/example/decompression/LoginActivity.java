package com.example.decompression;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mysql.DBUtils;

public class LoginActivity extends AppCompatActivity {

    private EditText et_username;
    private EditText et_password;
//    private SharedPreferences mSharedPreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

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
                  if(DBUtils.IsExist(username)){
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