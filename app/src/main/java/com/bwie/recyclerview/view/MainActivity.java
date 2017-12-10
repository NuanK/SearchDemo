package com.bwie.recyclerview.view;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bwie.recyclerview.R;
import com.bwie.recyclerview.adapter.MyRecyclerAdapter;
import com.bwie.recyclerview.bean.SearchBean;
import com.bwie.recyclerview.presenter.SearchPresenter;
import com.liaoinstan.springview.widget.SpringView;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity implements SearchPresenter.PresenterListener{

    private SearchPresenter searchPresenter;
    List<SearchBean.DataBean>list=new ArrayList<>();
    private Button btn_search;
    private ImageView iv;
    private EditText et_search;
    private RecyclerView recyclerView;
    private MyRecyclerAdapter myRecyclerAdapter;
    boolean flag = true;
    private SpringView springView;
    String name="笔记本";
    int num=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_search=(Button)findViewById(R.id.btn_search);
        iv=(ImageView)findViewById(R.id.iv);
        et_search=(EditText)findViewById(R.id.et_search);
        recyclerView=(RecyclerView)findViewById(R.id.recy);
//        springView=(SpringView)findViewById(R.id.spring);

        String s=String.valueOf(num);


        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        myRecyclerAdapter=new MyRecyclerAdapter(list,MainActivity.this,flag);
        recyclerView.setAdapter(myRecyclerAdapter);

        searchPresenter=new SearchPresenter(this);
        searchPresenter.getDat(name,s);
        //设置适配器
        setAdapter();
        //点击搜索事件
        btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!TextUtils.isEmpty(et_search.getText().toString())){
                    list.clear();
                    Toast.makeText(MainActivity.this,et_search.getText().toString(),Toast.LENGTH_SHORT).show();
                    searchPresenter.getDat(et_search.getText().toString(),"1");
                    setAdapter();
                }
            }
        });

        //点击切换事件
        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (flag==false){
                    flag=true;
                    iv.setBackgroundResource(R.mipmap.grid_icon);
                    recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this,LinearLayoutManager.VERTICAL,false));
                    MainActivity.this.myRecyclerAdapter =new MyRecyclerAdapter(list,MainActivity.this,flag);
                    recyclerView.setAdapter(MainActivity.this.myRecyclerAdapter);
                    MainActivity.this.myRecyclerAdapter.notifyDataSetChanged();
                }else {
                    flag=false;
                    iv.setBackgroundResource(R.mipmap.lv_icon);
                    recyclerView.setLayoutManager(new GridLayoutManager(MainActivity.this,2));
                    MainActivity.this.myRecyclerAdapter =new MyRecyclerAdapter(list,MainActivity.this,flag);
                    recyclerView.setAdapter(MainActivity.this.myRecyclerAdapter);
                    MainActivity.this.myRecyclerAdapter.notifyDataSetChanged();
                }
            }
        });

//        springView.setHeader(new DefaultHeader(this));
//        springView.setFooter(new DefaultFooter(this));
//        springView.setListener(new SpringView.OnFreshListener() {
//            @Override
//            public void onRefresh() {
//                num=1;
//                String s1 = String.valueOf(num);
//                searchPresenter.getDat(name,s1);
//                setAdapter();
//                springView.onFinishFreshAndLoad();
//            }
//
//            @Override
//            public void onLoadmore() {
//                num++;
//                String s1 = String.valueOf(num);
//                searchPresenter.getDat(name,s1);
//                Toast.makeText(MainActivity.this,s1,Toast.LENGTH_SHORT).show();
//                setAdapter();
//                springView.onFinishFreshAndLoad();
//            }
//        });

    }

    public void setAdapter(){
        if (myRecyclerAdapter==null){
            recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this,LinearLayoutManager.VERTICAL,false));
            myRecyclerAdapter=new MyRecyclerAdapter(list,MainActivity.this,flag);
            recyclerView.setAdapter(myRecyclerAdapter);
        }else {
            myRecyclerAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onSuccess(List<SearchBean.DataBean> lists) {
        list.addAll(lists);
        myRecyclerAdapter.notifyDataSetChanged();
    }

    @Override
    public void onFailed(Exception e) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        searchPresenter.detach();
    }
}
