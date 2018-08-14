package jd_demo01.bwei.com.demo1_jd.fragment;


import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.google.gson.Gson;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.io.IOException;
import java.util.HashMap;

import jd_demo01.bwei.com.demo1_jd.R;
import jd_demo01.bwei.com.demo1_jd.adapter.ProductAdapter;
import jd_demo01.bwei.com.demo1_jd.bean.ProductBean;
import jd_demo01.bwei.com.demo1_jd.common.Api;
import jd_demo01.bwei.com.demo1_jd.presenter.RecyPresenter;
import jd_demo01.bwei.com.demo1_jd.utils.OkHttpUtils;
import jd_demo01.bwei.com.demo1_jd.utils.RequestCallback;
import okhttp3.Call;
import okhttp3.Response;

public class Fragment1 extends Fragment implements XRecyclerView.LoadingListener,ProductAdapter.OnItemClickListener{

    private RecyPresenter presenter;
    private XRecyclerView xrv;
    private LinearLayoutManager manager;
    private View view;
    private int page=1;//莫惹第一页
    private ProductBean productBean;
    private Handler handler = new Handler();
    private ProductAdapter productAdapter;
    private Button btn_chaxun;
    private EditText edit_txt;
    private String goodsname="手机";

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_fragment1, container, false);
        xrv = view.findViewById(R.id.xrecycler_view);
        edit_txt = view.findViewById(R.id.edit_txtC);
        btn_chaxun = view.findViewById(R.id.chaxun);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        xrv.setLoadingListener(this);
        xrv.setLoadingMoreEnabled(true);//是否支持上拉加载更多
        //请求商品列表
        requestProduct(goodsname);
        //输入进行查询
        btn_chaxun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goodsname = edit_txt.getText().toString();
                requestProduct(goodsname);
            }
        });

    }

    /**
     * 请求商品列表
     */
    private void requestProduct(String goodsname) {
        HashMap<String, String> params = new HashMap<>();
        params.put("keywords",goodsname);
        params.put("page",page+"");

        OkHttpUtils.getInstance().postData(Api.PRODUCT_URL, params, new RequestCallback() {

            private String result;

            @Override
            public void failure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) {
                String result = null;
                if (response.code() == 200){
                    try {
                        result = response.body().string();
                        System.out.println("result:"+result);
                        parseProductBean(result);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    /**
     * 解析
     * @param result
     */
    private void parseProductBean(String result) {
        productBean = new Gson().fromJson(result, ProductBean.class);
        handler.post(new Runnable() {
            @Override
            public void run() {
                fillDatas();
            }
        });
    }

    /**
     * 绘制列表 使用recyclerview
     */
    private void fillDatas() {
        System.out.println("page:"+page);
        xrv.setLayoutManager(new LinearLayoutManager(getActivity()));
        if (page==1){
            productAdapter = new ProductAdapter(getActivity(), productBean.data);
            xrv.setAdapter(productAdapter);
            xrv.refreshComplete();
        }else {
            if (productAdapter!=null){
                productAdapter.loadData(productBean.data);
                xrv.loadMoreComplete();
            }
        }
    }
    //下拉刷新
    @Override
    public void onRefresh() {
        page = 1;
        requestProduct(goodsname);
    }
    //上拉加载
    @Override
    public void onLoadMore() {
        page++;

        requestProduct(goodsname);
    }

    @Override
    public void onItemClick(int position){

        //当前产品对应的信息
        ProductBean.Product product=new ProductBean().data.get(position);
        Toast.makeText(getActivity(),product.title,Toast.LENGTH_LONG).show();
    }
}
