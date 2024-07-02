package fragment;

        import android.os.Bundle;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.Button;
        import android.widget.TextView;

        import androidx.annotation.NonNull;
        import androidx.annotation.Nullable;
        import androidx.fragment.app.Fragment;

        import com.example.decompression.R;

public class PrivacyPolicyFragment extends Fragment {
    private TextView tv_content;
    private Button btn_exit;
    private Button btn_enter;
    private Button help;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.activity_privacy_policy, container, false);

        // Initialize views
        btn_exit = view.findViewById(R.id.btn_exit);
        btn_enter = view.findViewById(R.id.btn_enter);
        help = view.findViewById(R.id.help);
        tv_content = view.findViewById(R.id.tv_content);

        // Set onClickListeners
        btn_exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tv_content.setText("404 error!");
            }
        });

        btn_enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tv_content.setText("阅读成功！");
            }
        });

        help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tv_content.setText("提交成功！");
            }
        });

        return view;
    }
}