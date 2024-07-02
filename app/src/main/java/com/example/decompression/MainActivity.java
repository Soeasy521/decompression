package com.example.decompression;

//import android.app.FragmentTransaction;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import fragment.CommunityFragment;
import fragment.HomeFragment;
import fragment.MineFragment;





public class MainActivity extends AppCompatActivity {

    private HomeFragment mHomeFragment;
    private CommunityFragment mCommunityFragment;
    private MineFragment mMineFragment;

    private BottomNavigationView mBottomNavigationView;



    private TextView tv_data;
    private Button btn_get_data; //声明组件
    private Button btn_get_all; //声明组件

    private final Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what){
                case 0x11:
                case 0x12:
                    String s = (String) msg.obj;
                    tv_data.setText(s);
                    break;
            }
            return true;
        }
    });








    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


//        // 控件的初始化
//        btn_get_data = findViewById(R.id.btn_get_data);
//        btn_get_all = findViewById(R.id.btn_get_all);
//        tv_data = findViewById(R.id.tv_data);
//        setListener();

        //初始化控件
        mBottomNavigationView=findViewById(R.id.bottomNavigationView);
        //mBottomNavigationView设置点击事件
        mBottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId()==R.id.home){
                    selectedFragment(0);

                }else if (item.getItemId()==R.id.community){
                    selectedFragment(1);


                }else if (item.getItemId()==R.id.mine){
                    selectedFragment(2);

                }

                return true;
            }
        });

        //默认首页选中
        selectedFragment(0);
    }



//    //设置监听
//    private void setListener() {
//        // 按钮点击事件
//        btn_get_data.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // 创建一个线程来连接数据库并获取数据库中对应表的数据
//                new Thread(new Runnable() {
//                    @Override
//                    public void run() {
//                        // 调用数据库工具类DBUtils的getInfoByName方法获取数据库表中数据
//                        HashMap<String, Object> map = DBUtils.getInfoByName("张三");
//                        Message message = handler.obtainMessage();
//                        if(map != null){
//                            String s = "";
//                            for (String key : map.keySet()){
//                                s += key + ":" + map.get(key) + "\n";
//                            }
//                            message.what = 0x12;
//                            message.obj = s;
//                        }else {
//                            message.what = 0x11;
//                            message.obj = "查询结果为空";
//                        }
//                        // 发消息通知主线程更新UI
//                        handler.sendMessage(message);
//                    }
//                }).start();
//
//            }
//        });
//
//        // 按钮点击事件
//        btn_get_all.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // 创建一个线程来连接数据库并获取数据库中对应表的数据
//                new Thread(new Runnable() {
//                    @Override
//                    public void run() {
//                        // 调用数据库工具类DBUtils的getInfoByName方法获取数据库表中数据
//                        HashMap<String, Object> map = DBUtils.getAllInfo();
//                        Message message = handler.obtainMessage();
//                        if(map != null){
//                            String s = "";
//                            for (String key : map.keySet()){
//                                s += key + ":" + map.get(key) + "\n";
//                            }
//                            message.what = 0x12;
//                            message.obj = s;
//                        }else {
//                            message.what = 0x11;
//                            message.obj = "查询结果为空";
//                        }
//                        // 发消息通知主线程更新UI
//                        handler.sendMessage(message);
//                    }
//                }).start();
//
//            }
//        });
//    }
//



    private void selectedFragment(int position){

        androidx.fragment.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        hideFragment(fragmentTransaction);

        if (position ==0){
            if (mHomeFragment==null){
                mHomeFragment = new HomeFragment();
                fragmentTransaction.add(R.id.content,mHomeFragment);
            }else {
                fragmentTransaction.show(mHomeFragment);
            }

        }else if (position==1){
            if(mCommunityFragment==null){
                mCommunityFragment=new CommunityFragment();
                fragmentTransaction.add(R.id.content,mCommunityFragment);
            }else {
                fragmentTransaction.show(mCommunityFragment);
            }

        }else {
            if(mMineFragment==null){
                mMineFragment=new MineFragment();
                fragmentTransaction.add(R.id.content,mMineFragment);
            }else {
                fragmentTransaction.show(mMineFragment);
            }
        }


        //一定要提交
        fragmentTransaction.commit();

    }
    //隐藏另外两个页面
    private void hideFragment(FragmentTransaction fragmentTransaction){
        if (mHomeFragment!=null){
            fragmentTransaction.hide(mHomeFragment);

        }

        if (mCommunityFragment!=null){
            fragmentTransaction.hide(mCommunityFragment);
        }

        if (mMineFragment!=null){
            fragmentTransaction.hide(mMineFragment);
        }
    }






}


