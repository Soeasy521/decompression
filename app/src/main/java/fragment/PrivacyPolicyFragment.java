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

    private TextView tvContent;
    private Button helpButton;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_mine_help, container, false);

        // Initialize views
        tvContent = view.findViewById(R.id.tv_content);
        helpButton = view.findViewById(R.id.help);

        // Set onClickListeners
        helpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvContent.setText("提交成功！");
            }
        });

        return view;
    }
}