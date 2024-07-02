package fragment;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.decompression.R;

public class PrivacyPolicyActivity extends Fragment {

    private TextView tv_content;
    private TextView btn_exit;
    private TextView btn_enter;
    private TextView help;
    TextView textView;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
     /*   setContentView(R.layout.activity_privacy_policy);

        btn_exit = findViewByld(R.id.btn_exit);
        btn_enter = findViewByld(R.id.btn_enter);
        help = findViewByld(R.id.help);
        textView = findViewByld(R.id.tv_content);
    */
        btn_exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textView.setText("404 error!");
            }
        });
        btn_enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textView.setText("阅读成功！");
            }
        });
        help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textView.setText("提交成功！");
            }
        });


    }



}

