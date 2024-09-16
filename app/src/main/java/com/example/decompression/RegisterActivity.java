package com.example.decompression;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mysql.DBUtils;

public class RegisterActivity extends AppCompatActivity {

    private EditText et_username;
    private EditText et_password;
//    private SharedPreferences mSharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //初始化控件
        et_username =findViewById(R.id.et_username);
        et_password =findViewById(R.id.et_password);

        //注册页面的返回，不需要跳转
        findViewById(R.id.toolbar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //点击注册
        findViewById(R.id.register).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username=et_username.getText().toString();
                String password=et_password.getText().toString();

                if(TextUtils.isEmpty(username) && TextUtils.isEmpty(password)){
                    Toast.makeText(RegisterActivity.this, "请输入用户名或密码", Toast.LENGTH_SHORT).show();
                }else {

                    if(DBUtils.InsertUserInfo(username,password)){
                        Toast.makeText(RegisterActivity.this, "注册成功，请登录", Toast.LENGTH_SHORT).show();
                        Intent intent =new Intent(RegisterActivity.this,MainActivity.class);
                        startActivity(intent);
                        finish();
                    }else{
                        Toast.makeText(RegisterActivity.this, "注册失败，请联系客服人员！", Toast.LENGTH_SHORT).show();
                    }

                }
                
            }
        });


    }
}