package jd_demo01.bwei.com.demo1_jd.bean;

import java.util.List;

/**
 * Created by 房国伟 on 2018/8/14.
 */
public class ProductBean {

    public String msg;
    public String code;
    public List<Product> data;

    public class Product{
        public String title;
        public String images;
        public String pid;
    }
}
