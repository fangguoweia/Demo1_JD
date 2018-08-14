package jd_demo01.bwei.com.demo1_jd.utils;

import okhttp3.Call;

import java.io.IOException;

import okhttp3.Response;

/**
 * Created by 房国伟 on 2018/8/14.
 */
public interface RequestCallback {

    void failure(Call call, IOException e);
    void onResponse(Call call, Response response);
}
