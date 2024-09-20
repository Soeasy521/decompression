package com.example.decompression;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.decompression.R;
//import com.example.decompression.DBUtils;
import com.example.mysql.DBUtils;

public class PublishFragment extends Fragment {

    private EditText et_article_title;
    private EditText et_article_content;
    private Button btn_publish;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_publish, container, false);

        // 初始化控件
        et_article_title = view.findViewById(R.id.et_article_title);
        et_article_content = view.findViewById(R.id.et_article_content);
        btn_publish = view.findViewById(R.id.btn_publish);

        // 设置发布功能
        btn_publish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                publishArticle();
            }
        });

        return view;
    }

    private void publishArticle() {
        String title = et_article_title.getText().toString();
        String content = et_article_content.getText().toString();

        if (TextUtils.isEmpty(title) || TextUtils.isEmpty(content)) {
            Toast.makeText(getContext(), "标题和内容不能为空", Toast.LENGTH_SHORT).show();
            return;
        }

        // 假设 userId 已经从当前登录用户的上下文中获取
        int userId = 1; // 示例中使用固定的用户ID，实际情况请根据需要获取

        if (DBUtils.getInstance(getContext()).publishArticle(getContext(), userId, title, content)) {
            Toast.makeText(getContext(), "发布成功", Toast.LENGTH_SHORT).show();
            // 清空输入框
            et_article_title.setText("");
            et_article_content.setText("");
        } else {
            Toast.makeText(getContext(), "发布失败，请重试", Toast.LENGTH_SHORT).show();
        }
    }
}