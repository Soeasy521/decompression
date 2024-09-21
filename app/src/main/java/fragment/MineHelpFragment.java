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

public class MineHelpFragment extends Fragment {

    private EditText etFeedbackTitle;
    private EditText etFeedbackContent;
    private Button btnSubmitFeedback;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_mine_help, container, false);

        // 初始化视图
        etFeedbackTitle = rootView.findViewById(R.id.et_feedback_title);
        etFeedbackContent = rootView.findViewById(R.id.et_feedback_content);
        btnSubmitFeedback = rootView.findViewById(R.id.btn_submit_feedback);

        // 设置提交按钮的点击事件
        btnSubmitFeedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = etFeedbackTitle.getText().toString().trim();
                String content = etFeedbackContent.getText().toString().trim();

                if (title.isEmpty() || content.isEmpty()) {
                    Toast.makeText(getActivity(), "请填写完整的反馈信息", Toast.LENGTH_SHORT).show();
                    return;
                }

                // 处理提交逻辑
                // 例如，发送反馈到服务器
                Toast.makeText(getActivity(), "反馈已提交", Toast.LENGTH_SHORT).show();
            }
        });

        return rootView;
    }
}