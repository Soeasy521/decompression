package fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.decompression.LoginActivity;
import com.example.decompression.R;
import com.example.decompression.UpdatePwdActivity;

public class MineFragment<UserInfo> extends Fragment {

    private View rootView;
    private TextView tv_username;
    private TextView tv_nickname;
    private TextView tv_update_pwd;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout once and assign to rootView
        rootView = inflater.inflate(R.layout.fragment_mine, container, false);

        // Initialize views
        tv_username = rootView.findViewById(R.id.tv_username);
        tv_nickname = rootView.findViewById(R.id.tv_nickname);
        tv_update_pwd = rootView.findViewById(R.id.tv_update_pwd);

        // Set click listener for "修改密码" button
        tv_update_pwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), UpdatePwdActivity.class);
                startActivity(intent);
            }
        });

        // Set click listener for "退出登录"
        rootView.findViewById(R.id.exit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(getContext())
                        .setTitle("温馨提示")
                        .setMessage("确定要退出登录吗?")
                        .setNegativeButton("取消", null)
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // 退出登录逻辑
                                getActivity().finish();
                                // 启动登录页面
                                Intent intent = new Intent(getActivity(), LoginActivity.class);
                                startActivity(intent);
                            }
                        })
                        .show();
            }
        });

        // Return the rootView after setting up all listeners
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

//        //设置用户数据
//        UserInfo.userInfo = UserInfo.getUserInfo();
//        tv_username.setText(userInfo.getUsername());
//        tv_nickname.setText(userInfo.getNickname());
    }
}
