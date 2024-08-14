package com.example.decompression;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class AboutappActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_mine_aboutapp);

        // 初始化组件
        TextView appName = findViewById(R.id.app_name);
        TextView appDescription = findViewById(R.id.app_description);
        TextView version = findViewById(R.id.version);
        TextView developerName = findViewById(R.id.developer_name);
        TextView developerEmail = findViewById(R.id.developer_email);
        TextView copyright = findViewById(R.id.copyright);
        Button btnBack = findViewById(R.id.btn_back);

        // 设置应用名称
        appName.setText("Decompression App");

        // 设置应用描述
        appDescription.setText("Decompression App is designed to help users manage stress and improve mental health through guided relaxation exercises and mindfulness practices.");

        // 设置版本号
        version.setText("Version 1.0.0");

        // 设置开发者信息
        developerName.setText("Developed by: John Doe");
        developerEmail.setText("Contact us at: john.doe@example.com");

        // 设置版权信息
        copyright.setText("© 2023 Decompression App. All rights reserved.");

        // 设置返回按钮的点击事件
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}