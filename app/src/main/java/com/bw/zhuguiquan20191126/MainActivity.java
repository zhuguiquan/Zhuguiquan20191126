package com.bw.zhuguiquan20191126;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;

import com.google.android.material.tabs.TabLayout;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity {

    private ImageView imageView;
    private TabLayout tb;
    private ViewPager vp;
    List<Fragment> fragmentList=new ArrayList<>();
    List<String> name=new ArrayList<>();

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        //找控件

        //哈哈哈
        imageView = findViewById(R.id.image);
        tb = findViewById(R.id.tb);
        vp = findViewById(R.id.vp);
        //创建fragment
        HomeFragment homeFragment = new HomeFragment();
        OtherFragment homeFragment1 = OtherFragment.getInstance("热点");
        OtherFragment homeFragment2 = OtherFragment.getInstance("我的");
        OtherFragment homeFragment3 = OtherFragment.getInstance("要闻");
        OtherFragment homeFragment4 = OtherFragment.getInstance("推荐");
        OtherFragment homeFragment5 = OtherFragment.getInstance("视频");
        //添加fragment
        fragmentList.add(homeFragment);
        fragmentList.add(homeFragment1);
        fragmentList.add(homeFragment2);
        fragmentList.add(homeFragment3);
        fragmentList.add(homeFragment4);
        fragmentList.add(homeFragment5);
        //添加名字
        name.add("时事");
        name.add("热点");
        name.add("我的");
        name.add("要闻");
        name.add("推荐");
        name.add("视频");
        //创建适配器
        vp.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @NonNull
            @Override
            public Fragment getItem(int position) {
                return fragmentList.get(position);
            }

            @Override
            public int getCount() {
                return fragmentList.size();
            }

            @Nullable
            @Override
            public CharSequence getPageTitle(int position) {
                return name.get(position);
            }
        });
        //关联
        tb.setupWithViewPager(vp);
        //点击切换图片
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent,100);

            }
        });

    }

    @Override
    protected int layoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Uri data1 = data.getData();
        try {
            Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), data1);
            imageView.setImageBitmap(bitmap);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
