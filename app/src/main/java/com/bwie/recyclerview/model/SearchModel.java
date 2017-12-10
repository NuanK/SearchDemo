package com.bwie.recyclerview.model;

import com.bwie.recyclerview.net.OkHttpUtils;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by ASUS on 2017/12/9.
 */

public class SearchModel {
    public void getData(String name, String page, final ModuleListeren moduleListeren){
        String url="http://120.27.23.105/product/searchProducts?keywords="+name+"&page="+page;
        OkHttpUtils.getHttpUtils().doGet(url, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                if (moduleListeren!=null){
                    moduleListeren.onFailed(e);
                }
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String string = response.body().string();
                if(moduleListeren!=null){
                    moduleListeren.onSuccess(string);
                }
            }
        });
    }

    public interface ModuleListeren{
        void onSuccess(String response);
        void onFailed(Exception e);
    }


}

