package main.nini.com.iread.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import de.greenrobot.event.EventBus;

/**
 * Created by ${zyf} on 2016/12/8.
 */

public abstract class BaseFrag extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return inflater.inflate(setLayout(), container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
        initData();
    }

    /***
     * 绑定布局的方法
     *
     * @return 返回值为布局的id
     */
    protected abstract int setLayout();

    /***
     * 初始化组件的代码写在这里
     */
    protected abstract void initView();

    /***
     * 初始化数据的代码写在这里
     */
    protected abstract void initData();

    /***
     *
     * @param resId 组件在xml文件中声明的id
     * @param <T> 泛型，这样写可以不用强转
     * @return 返回组件对象
     */
    protected  <T extends View> T bindView(int resId) {
        return bindView(getView(),resId);
    }

    /***
     *
     * @param resId 组件在xml文件中声明的id
     * @param <T> 泛型，这样写可以不用强转
     * @return 返回组件对象
     */
    protected  <T extends View> T bindView(View view,int resId) {
        return (T) view.findViewById(resId);
    }

    protected void toast(String content){
        Toast.makeText(getContext(), content, Toast.LENGTH_SHORT).show();
    }

}
