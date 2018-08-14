package jd_demo01.bwei.com.demo1_jd.activity;

import android.graphics.Color;
import android.os.Build;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.jcodecraeer.xrecyclerview.XRecyclerView;

import jd_demo01.bwei.com.demo1_jd.R;
import jd_demo01.bwei.com.demo1_jd.fragment.Fragment1;
import jd_demo01.bwei.com.demo1_jd.fragment.Fragment2;
import jd_demo01.bwei.com.demo1_jd.fragment.Fragment3;
import jd_demo01.bwei.com.demo1_jd.fragment.Fragment4;

public class MainActivity extends AppCompatActivity{

    private FrameLayout frament_layout;
    private RadioGroup radio_group;
    private Fragment1 f1;
    private Fragment4 f4;
    private Fragment3 f3;
    private Fragment2 f2;
    private FragmentManager fragmentManager;
    private int statusBarHeight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //初始化控件,布局
        initView();

    }

    private void chenJinShi() {

    }

    private void initView() {
        frament_layout = findViewById(R.id.frame_layout);
        radio_group = findViewById(R.id.radio_group);

        //创建fragment
        f1 = new Fragment1();
        //初始化页面
        fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.frame_layout,f1).commit();
        radio_group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                if (f1!=null){
                    transaction.hide(f1);
                }
                if (f2!=null){
                    transaction.hide(f2);
                }
                if (f3!=null){
                    transaction.hide(f3);
                }
                if (f4!=null){
                    transaction.hide(f4);
                }
                switch (checkedId){
                    case R.id.radio01:
                        if (f1==null){
                            f1 = new Fragment1();
                            transaction.add(R.id.frame_layout,f1);
                        }else {
                            transaction.show(f1);
                        }
                        break;
                    case R.id.radio02:
                        if (f2==null){
                            f2 = new Fragment2();
                            transaction.add(R.id.frame_layout,f2);
                        }else {
                            transaction.show(f2);
                        }
                        break;
                    case R.id.radio03:
                        if (f3==null){
                            f3 = new Fragment3();
                            transaction.add(R.id.frame_layout,f3);
                        }else {
                            transaction.show(f3);
                        }
                        break;
                    case R.id.radio04:
                        if (f4==null){
                            f4 = new Fragment4();
                            transaction.add(R.id.frame_layout,f4);
                        }else {
                            transaction.show(f4);
                        }
                        break;
                }
                transaction.commit();
            }
        });

    }

    public int getStatusBarHeight() {

        return statusBarHeight;
    }
}
