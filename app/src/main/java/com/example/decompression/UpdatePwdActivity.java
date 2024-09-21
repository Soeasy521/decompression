package com.example.decompression;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.decompression.R;

public class UpdatePwdActivity extends AppCompatActivity {

    private EditText etNewPwd;
    private EditText etConfirmPwd;
    private Button btnUpdatePwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_pwd);

        // 初始化控件
        etNewPwd = findViewById(R.id.et_new_pwd);
        etConfirmPwd = findViewById(R.id.et_confirm_pwd);
        btnUpdatePwd = findViewById(R.id.btn_update_pwd);

        // 设置按钮点击事件
        btnUpdatePwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String new_pwd = etNewPwd.getText().toString().trim();
                String confirm_pwd = etConfirmPwd.getText().toString().trim();

                if (TextUtils.isEmpty(new_pwd) || TextUtils.isEmpty(confirm_pwd)) {
                    Toast.makeText(UpdatePwdActivity.this, "密码不能为空", Toast.LENGTH_SHORT).show();
                } else if (!new_pwd.equals(confirm_pwd)) {
                    Toast.makeText(UpdatePwdActivity.this, "新密码与确认密码不一致", Toast.LENGTH_SHORT).show();
                } else {
                    // 调用更新密码的方法
                    updatePassword(new_pwd);
                }
            }
        });
    }

    private void updatePassword(String newPwd) {
        // 这里可以调用你的更新密码的逻辑，例如通过网络请求或本地数据库操作
        // 假设更新密码成功
        boolean isUpdated = true; // 假设更新密码成功

        if (isUpdated) {
            Toast.makeText(UpdatePwdActivity.this, "密码修改成功", Toast.LENGTH_SHORT).show();
            // 可以在这里执行其他操作，例如关闭当前活动或跳转到其他页面
            finish();
        } else {
            Toast.makeText(UpdatePwdActivity.this, "密码修改失败", Toast.LENGTH_SHORT).show();
        }
    }
}