package com.example.decompression;

//import android.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;

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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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