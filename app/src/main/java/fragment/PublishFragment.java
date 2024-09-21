package fragment;

import android.os.Bundle;
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

        // 设置发布按钮点击事件
        btn_publish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                publishArticle();
            }
        });

        return view;
    }

    private void publishArticle() {
        String articleTitle = et_article_title.getText().toString();
        String articleContent = et_article_content.getText().toString();
        int userId = 1; // 示例中使用固定的用户ID，实际情况请根据需要获取

        if (articleTitle.isEmpty() || articleContent.isEmpty()) {
            Toast.makeText(getContext(), "文章标题和内容不能为空", Toast.LENGTH_SHORT).show();
            return;
        }

        // 这里假设有一个DBUtils类来处理数据库操作
        if (DBUtils.publishArticle(getContext(), userId, articleTitle, articleContent)) {
            Toast.makeText(getContext(), "文章发布成功", Toast.LENGTH_SHORT).show();
            // 返回上一个Fragment
            requireActivity().getSupportFragmentManager().popBackStack();
        } else {
            Toast.makeText(getContext(), "文章发布失败，请重试", Toast.LENGTH_SHORT).show();
        }
    }
}