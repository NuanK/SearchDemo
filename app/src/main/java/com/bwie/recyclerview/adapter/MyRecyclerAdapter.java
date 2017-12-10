package com.bwie.recyclerview.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bwie.recyclerview.R;
import com.bwie.recyclerview.bean.SearchBean;

import java.util.List;



public class MyRecyclerAdapter extends RecyclerView.Adapter{

    private List<SearchBean.DataBean>list;
    Context context;
    boolean flag=true;

    public MyRecyclerAdapter(List<SearchBean.DataBean> list, Context context, boolean flag) {
        this.list = list;
        this.context = context;
        this.flag = flag;
        Log.e("*****************", "进入");
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        Log.e("----------------", "进入");
        if (flag){
            View view=View.inflate(context,R.layout.recy_item,null);
            return new ViewHolder1(view);
        }else {
            View view=View.inflate(context,R.layout.recy_item02,null);
            return new ViewHolder2(view);
        }


    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (flag){
            if (holder instanceof ViewHolder1){
                ViewHolder1 holder1=(ViewHolder1)holder;
                holder1.title.setText(list.get(position).getTitle());
                holder1.price.setText("价格："+list.get(position).getPrice());
                String[] split=list.get(position).getImages().split("\\|");
                Glide.with(context).load(split[0]).into(holder1.img1);
            }
        }else {
            if (holder instanceof ViewHolder2){
                ViewHolder2 holder2=(ViewHolder2)holder;
                holder2.title2.setText(list.get(position).getTitle());
                holder2.price2.setText("价格："+list.get(position).getPrice());
                String[] split=list.get(position).getImages().split("\\|");
                Glide.with(context).load(split[0]).into(holder2.img2);
            }
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder1 extends RecyclerView.ViewHolder {

        ImageView img1;
        TextView title;
        TextView price;


        public ViewHolder1(View itemView) {
            super(itemView);

            img1=(ImageView)itemView.findViewById(R.id.img_recy);
            title=(TextView)itemView.findViewById(R.id.title);
            price=(TextView)itemView.findViewById(R.id.price);

        }
    }

    class ViewHolder2 extends RecyclerView.ViewHolder {

        ImageView img2;
        TextView title2;
        TextView price2;


        public ViewHolder2(View itemView) {
            super(itemView);

            img2=(ImageView)itemView.findViewById(R.id.image_recy);
            title2=(TextView)itemView.findViewById(R.id.title_recy);
            price2=(TextView)itemView.findViewById(R.id.price_recy);

        }
    }

}
