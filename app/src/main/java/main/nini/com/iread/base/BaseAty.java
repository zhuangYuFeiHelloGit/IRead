package main.nini.com.iread.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

/**
 * Created by ${zyf} on 2016/12/8.
 */

public abstract class BaseAty extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(setLayout());
        initView();
        initData();
    }

    /***
     * 绑定布局的方法
     * @return 返回值为布局的id
     */
    public abstract int setLayout();

    /***
     * 绑定组件的方法
     * @param resId 组件在xml文件中声明的id
     * @param <T> 泛型，这样写可以不用强转
     * @return 返回组件对象
     */
    public <T extends View> T bindView(int resId) {
        return (T) findViewById(resId);
    }

    /***
     * 绑定组件的方法
     * @param view 组件的父View
     * @param resId 组件在xml文件中声明的id
     * @param <T> 泛型，这样写可以不用强转
     * @return 返回组件对象
     */
    public <T extends View> T bindView(View view,int resId) {
        return (T) view.findViewById(resId);
    }

    /***
     * 初始化组件的代码写在这里
     */
    public abstract void initView();

    /***
     * 初始化数据的代码写在这里
     */
    public abstract void initData();

}
