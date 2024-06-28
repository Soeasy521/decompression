package com.example.decompression;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class    UpdatePwdActivity extends AppCompatActivity {
    private EditText et_new_password;
    private EditText et_confirm_password;
    //private EditText btn_update_password;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_pwd);

        //初始化控件
        et_new_password=findViewById(R.id.et_new_password);
        et_confirm_password=findViewById(R.id.et_confirm_password);

        //设置修改密码点击事件
        findViewById(R.id.btn_update_password).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String new_pwd =et_new_password.getText().toString();
                String confirm_pwd =et_confirm_password.getText().toString();

                if(TextUtils.isEmpty(new_pwd)||(TextUtils.isEmpty(confirm_pwd))){
                    Toast.makeText(UpdatePwdActivity.this, "密码不能为空", Toast.LENGTH_SHORT).show();
                }else if(!new_pwd.equals(confirm_pwd)){
                    Toast.makeText(UpdatePwdActivity.this, "新密码与确认密码不一致", Toast.LENGTH_SHORT).show();
                }else {

                }


            }
        });

    }
}