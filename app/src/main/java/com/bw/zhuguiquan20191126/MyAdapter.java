package com.bw.zhuguiquan20191126;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * DateTime:2019/11/26 0026
 * author:朱贵全(Administrator)
 * function:
 */
public class MyAdapter extends BaseAdapter {
    private List<Bean.ListdataBean> listdata;

    public MyAdapter(List<Bean.ListdataBean> listdata) {

        this.listdata = listdata;
    }

    @Override
    public int getCount() {
        return listdata.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        //列表优化
        ViewHolder viewHolder=null;
        if(view==null){
            view=View.inflate(viewGroup.getContext(),R.layout.item,null);
            //寻找数据
            viewHolder=new ViewHolder();
            viewHolder.imageView=view.findViewById(R.id.img);
            viewHolder.textView=view.findViewById(R.id.tv);
            view.setTag(viewHolder);
        }else{
             viewHolder = (ViewHolder) view.getTag();
        }
        //获取数据
        Bean.ListdataBean listdataBean = listdata.get(i);
        //设置数据
        viewHolder.textView.setText(listdataBean.getContent());
        NetUtils.getInstance().getPhoto(listdataBean.getImageurl(),viewHolder.imageView);
        return view;
    }
    private class ViewHolder{
        ImageView imageView;
        TextView textView;
    }
}
