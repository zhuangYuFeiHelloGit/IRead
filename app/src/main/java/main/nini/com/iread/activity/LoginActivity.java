package main.nini.com.iread.activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.List;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.sina.weibo.SinaWeibo;
import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;
import de.greenrobot.event.ThreadMode;
import main.nini.com.iread.R;
import main.nini.com.iread.base.BaseAty;
import main.nini.com.iread.my_util.MySpUtil;
import main.nini.com.iread.my_util.event.LoginEvent;
import main.nini.com.iread.my_util.event.RegisterEvent;
import main.nini.com.iread.my_util.event.WriteMessageEvent;
import main.nini.com.iread.response._User;

/**
 * Created by ${zyf} on 2016/12/9.
 */

public class LoginActivity extends BaseAty {

    private EditText userEt, passwordEt;
    private TextView loginTv, forgetTv, registerTv,sinaLoginTv;
    private String userName, password;
    private List<_User> userList;
    private ImageView backIv;
    private static final String TAG = "LoginActivity";

    private Handler handler;
    private _User user;

    @Override
    public int setLayout() {
        return R.layout.activity_login;
    }

    @Override
    public void initView() {
        EventBus.getDefault().register(this);

        userEt = bindView(R.id.login_user_et);
        passwordEt = bindView(R.id.login_password_et);

        loginTv = bindView(R.id.login_tv);
        forgetTv = bindView(R.id.login_forget_tv);
        sinaLoginTv = bindView(R.id.login_sina_tv);
        registerTv = bindView(R.id.login_register_tv);

        backIv = bindView(R.id.back_iv);
    }

    @Override
    public void initData() {

        handler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                if(user != null){
                    //说明第三方登录成功了
                    //提示用户
                    Toast.makeText(LoginActivity.this, "登录中...", Toast.LENGTH_SHORT).show();
                    MySpUtil.saveFromSina(true);
                    user.login(new SaveListener<_User>() {
                        @Override
                        public void done(_User s, BmobException e) {
                            if(e == null){
                                //说明登录成功,不是第一次使用该微博账号登录
                                //就不需要下面注册的步骤了
                               loginSucceed(s);
                            }else {
                                //需要在后台走一遍注册
                                signUp();
                            }
                        }
                    });


                }
                return false;
            }
        });

        loginTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userName = userEt.getText().toString();
                password = passwordEt.getText().toString();
                if (userName == null || password == null || userName.equals("") || password.equals("")) {
                    //TODO 做判断，吐丝
                    Toast.makeText(LoginActivity.this, "请输入正确的用户名或密码", Toast.LENGTH_SHORT).show();
                    return;
                }

                user = new _User();
                user.setUsername(userName);
                user.setPassword(password);
                user.login(new SaveListener<_User>() {
                    @Override
                    public void done(_User s, BmobException e) {
                        if (e == null) {
//                            Toast.makeText(LoginActivity.this, "成功", Toast.LENGTH_SHORT).show();
                            //如果已经设置了昵称
                            if (s.getNickName() != null) {

                                loginSucceed(s);
                                MySpUtil.saveFromSina(false);
                            } else {
                                //如果没设置过昵称，那么说明是第一次登录，提示用户去设置昵称与头像
                                Intent toWrite = new Intent(LoginActivity.this, WriteMessageActivity.class);
                                Toast.makeText(LoginActivity.this, "请上传头像与填写昵称", Toast.LENGTH_SHORT).show();
                                startActivity(toWrite);
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
                                finish();
                            }
                            finish();
                        } else {
                            e.printStackTrace();
                            Toast.makeText(LoginActivity.this, "用户名或密码错误", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        });

        registerTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });

        backIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        sinaLoginTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toLoginSina();
            }
        });
    }

    private void signUp() {
        user.signUp(new SaveListener<_User>() {
            @Override
            public void done(_User s, BmobException e) {
//                if(e == null){
                    //微博账号信息在后台中注册了,可以直接显示就可以了
                    loginSucceed(s);
//                }else {
                    e.printStackTrace();
//                    Toast.makeText(LoginActivity.this, "注册失败", Toast.LENGTH_SHORT).show();
//                }
            }
        });
    }

    private void loginSucceed(_User user) {
        MySpUtil.saveNickName(user.getNickName());
        if(user.getIconUrl() != null){
            MySpUtil.saveIconUrl(user.getIconUrl());
        }

        Log.e(TAG, userName);
        Log.e(TAG, password);
        //更改本地的缓存信息
        MySpUtil.saveUserPasswordId(userName,password,user.getObjectId());
        MySpUtil.login();


        //那么使用EventBus将user对象传到Fragment中与MainActivity中
        EventBus.getDefault().post(new LoginEvent(user));
        // 然后直接finish登录界面就行了
        finish();
    }

    private void toLoginSina() {
        final Platform sina = ShareSDK.getPlatform(SinaWeibo.NAME);
//        1c4c1a04e069c

        sina.setPlatformActionListener(new PlatformActionListener() {
            @Override
            public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {



                //下面的key值都是固定的,mob第三方登录回调的信息中通过key可以获得对应信息
                //得到昵称
                String name = hashMap.get("name").toString();
                //得到id
                String sinaId = hashMap.get("id").toString();
                //得到头像
                String sinaIcon = hashMap.get("avatar_hd").toString();

                user = new _User();
                //默认密码设置为:123456
                password = "123456";
                //将新浪id设置为用户名
                userName = sinaId;
                user.setIconUrl(sinaIcon);
                user.setNickName(name);
                user.setUsername(userName);
                user.setPassword(password);

                //切换回主线程刷新UI
                handler.sendEmptyMessage(100);
            }

            @Override
            public void onError(Platform platform, int i, Throwable throwable) {
                //如果失败,清楚缓存信息
                sina.removeAccount(true);
            }

            @Override
            public void onCancel(Platform platform, int i) {
                //如果取消了,清楚缓存信息
                sina.removeAccount(true);
            }
        });
        //去登录
        sina.showUser(null);

    }

    private void showProgressBar(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
    }

    @Subscribe(threadMode = ThreadMode.MainThread)
    public void registerDone(RegisterEvent event) {
        Log.d(TAG, event.password+"-----"+event.user);
        userEt.setText(event.user);
        passwordEt.setText(event.password);
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }
}
