package com.example.decompression;
        import android.annotation.SuppressLint;
        import android.os.Bundle;
        import android.view.View;
        import android.widget.Button;
        import androidx.appcompat.app.AppCompatActivity;
        import androidx.fragment.app.FragmentTransaction;
        import android.widget.TextView;
        import fragment.MineHelpFragment;

public class PrivacyPolicyActivity extends AppCompatActivity {
    private TextView tv_content;
    private Button btn_exit;
    private Button btn_enter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_privacy_policy);

        tv_content = findViewById(R.id.tv_content);
        btn_exit = findViewById(R.id.btn_exit);
        btn_enter = findViewById(R.id.btn_enter);

        btn_exit.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {
                tv_content.setText("404 error!");
                // Handle exit button click
            }
        });

        btn_enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv_content.setText("阅读成功！");
                // Handle enter button click
            }
        });


        // Load the fragment
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.help, new MineHelpFragment());
        transaction.commit();
    }
}