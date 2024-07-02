package com.example.decompression;
        import android.os.Bundle;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.Button;
        import androidx.appcompat.app.AppCompatActivity;
        import androidx.fragment.app.FragmentTransaction;
        import android.widget.TextView;
        import fragment.PrivacyPolicyFragment;

public class PrivacyPolicyActivity extends AppCompatActivity {
    private TextView tv_content;
    private Button btnExit;
    private Button btnEnter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_privacy_policy);

        btnExit = findViewById(R.id.btn_exit);
        btnEnter = findViewById(R.id.btn_enter);

        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv_content.setText("404 error!");
                // Handle exit button click
            }
        });

        btnEnter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv_content.setText("阅读成功！");
                // Handle enter button click
            }
        });


        // Load the fragment
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.help, new PrivacyPolicyFragment());
        transaction.commit();
    }
}