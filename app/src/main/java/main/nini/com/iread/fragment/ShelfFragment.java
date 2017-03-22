package main.nini.com.iread.fragment;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;
import de.greenrobot.event.ThreadMode;
import main.nini.com.iread.R;
import main.nini.com.iread.activity.DetailBookActivity;
import main.nini.com.iread.adapter.ShelfAdapter;
import main.nini.com.iread.base.BaseFrag;
import main.nini.com.iread.bean.FuzzySearchBean;
import main.nini.com.iread.my_util.Constant;
import main.nini.com.iread.my_util.MySpUtil;
import main.nini.com.iread.my_util.event.CollectionChangeEvent;
import main.nini.com.iread.my_util.event.LoginEvent;
import main.nini.com.iread.my_util.event.LogoutEvent;
import main.nini.com.iread.my_util.inter.MainChangePageListener;
import main.nini.com.iread.my_util.inter.OnRecyclerClickListener;
import main.nini.com.iread.response.Collection;
import main.nini.com.iread.response._User;

/**
 * Created by ${zyf} on 2016/12/8.
 */

public class ShelfFragment extends BaseFrag implements View.OnClickListener, OnRecyclerClickListener {

    private TextView toLoginTv;
    private MainChangePageListener listener;
    private List<Collection> data;
    private ShelfAdapter adapter;
    private RecyclerView recyclerView;
    private GridLayoutManager layoutManager;
    private _User user;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.listener = (MainChangePageListener) context;
    }

    @Override
    public int setLayout() {
        return R.layout.fragment_shelf;
    }

    @Override
    public void initView() {
        EventBus.getDefault().register(this);
        recyclerView =bindView(R.id.shelf_recycler);
        toLoginTv = bindView(R.id.to_login_tv);
        toLoginTv.setOnClickListener(this);
    }

    @Override
    public void initData() {

        if(MySpUtil.hasLogin()){
            toLoginTv.setVisibility(View.GONE);
            layoutManager = new GridLayoutManager(getContext(),3);
//            layoutManager.set
            BmobQuery<Collection> query = new BmobQuery<>();
            query.addWhereEqualTo("userId",MySpUtil.getObjectId());
            query.findObjects(new FindListener<Collection>() {
                @Override
                public void done(List<Collection> list, BmobException e) {
                    data = list;
                    adapter = new ShelfAdapter(getContext());
                    adapter.setData(data);
                    adapter.setRecyclerClickListener(getListener());
                    recyclerView.setLayoutManager(layoutManager);
                    recyclerView.setAdapter(adapter);
                }
            });
        }else {
            if(data != null && data.size() > 0){

                toLoginTv.setVisibility(View.VISIBLE);
                data.clear();
                adapter.notifyDataSetChanged();
            }
        }
    }

    private OnRecyclerClickListener getListener() {
        return this;
    }


    @Subscribe(threadMode = ThreadMode.MainThread)
    public void loginIn(LoginEvent event){
        Log.e("ShelfFragment", "接收到登录消息");
        this.user = event.getUser();
        initData();
    }

    @Subscribe(threadMode = ThreadMode.MainThread)
    public void logout(LogoutEvent event){
        initData();
    }

    @Subscribe(threadMode = ThreadMode.MainThread)
    public void loginIn(CollectionChangeEvent event){
        Log.e("ShelfFragment", "收藏成功了，知道了磨磨唧唧的");
        initData();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.to_login_tv:
                //跳转到个人信息页
                listener.toChangePage(3);
                break;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onItemClick(int pos) {
        Collection collection = adapter.getItem(pos);
        Intent toDetailBook = new Intent(getActivity(), DetailBookActivity.class);
        toDetailBook.putExtra("bean",collection);
        toDetailBook.putExtra("from", Constant.FROM_TYPE_SHELF);
        startActivity(toDetailBook);
    }
}
