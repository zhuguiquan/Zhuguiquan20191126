package com.bw.zhuguiquan20191126;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

/**
 * DateTime:2019/11/26 0026
 * author:朱贵全(Administrator)
 * function:
 */
public class OtherFragment extends BaseFragment {

    private TextView viewById;

    @Override
    protected void initData() {
        //获取值
        Bundle arguments = getArguments();
        String key = arguments.getString("key");
        viewById.setText(key);


    }

    @Override
    protected void initView(View inflate) {
        viewById = inflate.findViewById(R.id.text);

    }

    @Override
    protected int layoutId() {
        return R.layout.other;
    }

    public static OtherFragment getInstance(String value) {
        //发送值
        OtherFragment otherFragment = new OtherFragment();
        Bundle bundle = new Bundle();
        bundle.putString("key",value);
        otherFragment.setArguments(bundle);
        return otherFragment;
    }
}
