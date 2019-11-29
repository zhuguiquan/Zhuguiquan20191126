package com.bw.zhuguiquan20191126;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import java.util.ArrayList;
import java.util.List;

/**
 * DateTime:2019/11/26 0026
 * author:朱贵全(Administrator)
 * function:
 */
public class HomeFragment extends BaseFragment {

    private PullToRefreshListView pv;
    private ImageView imm;
    int page=1;
    List<Bean.ListdataBean> listdata=new ArrayList<>();

    @Override
    protected void initData() {
        getData();
    }

    @Override
    protected void initView(View inflate) {
        //找控件
        pv = inflate.findViewById(R.id.pv);
        imm = inflate.findViewById(R.id.imm);
        //设置上拉下拉
        pv.setMode(PullToRefreshBase.Mode.BOTH);
        pv.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                page=1;
                listdata.clear();
                getData();

            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                page++;
                getData();

            }
        });


    }

    private void getData() {
        //判断有网无网
        if(NetUtils.getInstance().hasNet(getContext())){
            pv.setVisibility(View.VISIBLE);
            imm.setVisibility(View.GONE);
            String http="";
            if(page==1){
                http="http://blog.zhaoliang5156.cn/api/pengpainews/pengpai1.json";
            }else if(page==2){
                http="http://blog.zhaoliang5156.cn/api/pengpainews/pengpai2.json";
            }else{
                http="http://blog.zhaoliang5156.cn/api/pengpainews/pengpai3.json";
            }
            //调用方法
            NetUtils.getInstance().getJson(http, new NetUtils.MyCallBack() {
                @Override
                public void ongetJson(String json) {
                    //解析数据
                    Bean bean = new Gson().fromJson(json, Bean.class);
                    List<Bean.ListdataBean> newlistdata = bean.getListdata();
                    //添加数据
                    listdata.addAll(newlistdata);
                    //创建适配器
                    MyAdapter myAdapter = new MyAdapter(listdata);
                    //设置适配器
                    pv.setAdapter(myAdapter);
                    pv.onRefreshComplete();
                    //点击条目跳转
                    pv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                            Intent intent = new Intent(getActivity(),TextActivity.class);
                            intent.putExtra("key","https://www.thepaper.cn/newsDetail_forward_4923534");
                            //开启
                            startActivity(intent);
                        }
                    });
                }
            });
        }else{
            //现实隐藏
            pv.setVisibility(View.GONE);
            imm.setVisibility(View.VISIBLE);
        }


    }

    @Override
    protected int layoutId() {
        return R.layout.home;
    }
}
