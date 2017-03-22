package main.nini.com.iread.fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;
import de.greenrobot.event.ThreadMode;
import main.nini.com.iread.R;
import main.nini.com.iread.activity.WriteMessageActivity;
import main.nini.com.iread.base.BaseFrag;
import main.nini.com.iread.activity.ChangeMsgActivity;
import main.nini.com.iread.activity.LoginActivity;
import main.nini.com.iread.my_util.MySpUtil;
import main.nini.com.iread.my_util.custom.CircleImageView;
import main.nini.com.iread.my_util.event.LoginEvent;
import main.nini.com.iread.my_util.event.LogoutEvent;
import main.nini.com.iread.my_util.event.WriteMessageEvent;
import main.nini.com.iread.response._User;

/**
 * Created by ${zyf} on 2016/12/8.
 */

public class PersonalFragment extends BaseFrag implements View.OnClickListener {

    private LinearLayout resetMsgLayout,resetPasswordLayout,logoutLayout;
    private TextView nameTv;
    private CircleImageView circleImageView;
    private _User user;
    private static final String TAG = "PersonalFragment";

    @Override
    public int setLayout() {
        return R.layout.fragment_personal;
    }

    @Override
    public void initView() {
        EventBus.getDefault().register(this);
        circleImageView = bindView(R.id.personal_icon_iv);
        nameTv = bindView(R.id.personal_name_tv);
        resetMsgLayout = bindView(R.id.personal_reset_msg_layout);
        resetPasswordLayout = bindView(R.id.personal_reset_password_layout);
        logoutLayout = bindView(R.id.personal_logout_layout);
        if(!MySpUtil.hasLogin()){
            //如果没登录，将修改密码与退出登录的视图隐藏
            changeToLogout();
        }else {
            user = new _User();
            user.setUsername(MySpUtil.getUserName());
            user.setPassword(MySpUtil.getPassword());

            //先显示本地存储的
            nameTv.setText(MySpUtil.getNickName());
            if(!MySpUtil.getIconUrl().equals("")){
                Glide.with(PersonalFragment.this)
                        .load(MySpUtil.getIconUrl())
                        .error(R.mipmap.default_head_icon)
                        .into(circleImageView);
            }

            //在网络拉去一遍,防止有变更
            user.login(new SaveListener<_User>() {
                @Override
                public void done(_User s, BmobException e) {
                    Log.e(TAG, "done: " + e);
                    if(s != null){
                        Log.e("PersonalFragment", s.getIconUrl());
                        if (s.getIconUrl() != null){

                            Glide.with(PersonalFragment.this)
                                    .load(s.getIconUrl())
                                    .error(R.mipmap.default_head_icon)
                                    .into(circleImageView);
                        }
                        nameTv.setText(s.getNickName());
                    }
                }
            });
        }
    }

    private void changeToLogin() {
        resetPasswordLayout.setVisibility(View.VISIBLE);
        resetMsgLayout.setVisibility(View.VISIBLE);
        logoutLayout.setVisibility(View.VISIBLE);

    }

    @Override
    public void initData() {
        //绑定监听事件
        bindEvent();
    }

    private void bindEvent() {
        nameTv.setOnClickListener(this);
        circleImageView.setOnClickListener(this);
        resetPasswordLayout.setOnClickListener(this);
        resetMsgLayout.setOnClickListener(this);
        logoutLayout.setOnClickListener(this);
    }

    //登录后会接收到信息
    @Subscribe(threadMode = ThreadMode.MainThread)
    public void loginIn(LoginEvent event) {
        this.user = event.getUser();
        if (user != null) {
            //设置用户名
            nameTv.setText(user.getNickName() + "");
            MySpUtil.saveNickName(user.getNickName());
            if(user.getIconUrl() != null){

                //设置头像
                Glide.with(this).load(user.getIconUrl()).into(circleImageView);
            }
            //将修改密码与退出登录的视图显示
            changeToLogin();
        }
    }

    private void changeToLogout() {

        resetPasswordLayout.setVisibility(View.INVISIBLE);
        resetMsgLayout.setVisibility(View.INVISIBLE);
        logoutLayout.setVisibility(View.INVISIBLE);
        nameTv.setText("登录/注册");
        circleImageView.setImageResource(R.mipmap.default_head_icon);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.personal_name_tv:
                if (!MySpUtil.hasLogin()) {
                    startActivity(new Intent(getContext(), LoginActivity.class));
                }
                break;
            case R.id.personal_icon_iv:
                break;
            case R.id.personal_reset_password_layout:
                if(!MySpUtil.isFromSina()){

                    startActivity(new Intent(getActivity(), ChangeMsgActivity.class));
                }else {
                    Toast.makeText(getContext(), "微博登录用户不支持修改密码", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.personal_logout_layout:
                showDialog("是否退出登录？");
                break;
            case R.id.personal_reset_msg_layout:
                if(!MySpUtil.isFromSina()){

                    toChangeMsg();
                }else {
                    Toast.makeText(getContext(), "微博登录用户不支持修改个人信息", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    private void toChangeMsg() {
        //如果没设置过昵称，那么说明是第一次登录，提示用户去设置昵称与头像
        Intent toWrite = new Intent(getContext(), WriteMessageActivity.class);
        toWrite.putExtra("isChange",true);
        startActivity(toWrite);
        user.setObjectId(MySpUtil.getObjectId());
        final _User finalUser = user;
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
                EventBus.getDefault().post(new WriteMessageEvent(finalUser));

            }
        }).start();
    }

    private void showDialog(String title){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle(title).setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                MySpUtil.logout();
                changeToLogout();
                EventBus.getDefault().post(new LogoutEvent());
            }
        }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        }).show();
    }

    @Override
    public void onDestroyView() {
        EventBus.getDefault().unregister(this);
        super.onDestroyView();
    }
}
