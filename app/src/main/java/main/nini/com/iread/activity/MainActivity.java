package main.nini.com.iread.activity;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.Subscribe;
import de.greenrobot.event.ThreadMode;
import main.nini.com.iread.adapter.MainViewPagerAdapter;
import main.nini.com.iread.R;
import main.nini.com.iread.base.BaseAty;
import main.nini.com.iread.fragment.BooksFragment;
import main.nini.com.iread.my_util.event.LoginEvent;
import main.nini.com.iread.fragment.PersonalFragment;
import main.nini.com.iread.my_util.inter.MainChangePageListener;
import main.nini.com.iread.response._User;
import main.nini.com.iread.fragment.SearchFragment;
import main.nini.com.iread.fragment.ShelfFragment;

public class MainActivity extends BaseAty implements MainChangePageListener {

    private ViewPager viewPager;
    private TabLayout tabLayout;
    private _User user;
    //每页要展示的Fragment
    private List<Fragment> data;
    //viewpager的适配器
    private MainViewPagerAdapter adapter;
    //主页标签的内容
    private String[] titles = {"图书", "书架", "搜索", "个人"};

    private int[] icons = {R.drawable.selector_tab_books
            , R.drawable.selector_tab_shelf
            , R.drawable.selector_tab_search
            , R.drawable.selector_tab_personal};

    @Override
    public int setLayout() {
        return R.layout.activity_main;
    }

    @Override
    public void initView() {

        viewPager = bindView(R.id.main_viewpager);
        tabLayout = bindView(R.id.main_tabs);

    }

    @Override
    public void initData() {
        data = new ArrayList<>();
        data.add(new BooksFragment());
        data.add(new ShelfFragment());
        data.add(new SearchFragment());
        data.add(new PersonalFragment());

        adapter = new MainViewPagerAdapter(getSupportFragmentManager(), data);
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(4);
//        tabLayout.setTabTextColors(android.R.color.darker_gray,android.R.color.holo_green_light);
        tabLayout.setupWithViewPager(viewPager);
        for (int i = 0; i < tabLayout.getTabCount(); i++) {
            View tabView = getLayoutInflater().inflate(R.layout.view_main_tab, null);
            TextView titleTv = (TextView) tabView.findViewById(R.id.mian_tab_title);
            ImageView icon = (ImageView) tabView.findViewById(R.id.main_tab_icon);
            icon.setImageResource(icons[i]);
            titleTv.setText(titles[i]);
            tabLayout.getTabAt(i).setCustomView(tabView);
        }


    }

    @Subscribe(threadMode = ThreadMode.MainThread)
    public void loginIn(LoginEvent event) {
        Log.e("MainActivity", "接收到登录消息");
        this.user = event.getUser();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void toChangePage(int position) {
        viewPager.setCurrentItem(position);
    }
}
