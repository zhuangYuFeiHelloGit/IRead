package main.nini.com.iread.activity;

import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import de.greenrobot.event.EventBus;
import main.nini.com.iread.R;
import main.nini.com.iread.base.BaseAty;
import main.nini.com.iread.my_util.event.RegisterEvent;
import main.nini.com.iread.response._User;

/**
 * Created by ${zyf} on 2016/12/10.
 */

public class RegisterActivity extends BaseAty {

    private ImageView backIv;
    private EditText userEt, passwordEt, ensureEt;
    private TextView registerTv;
    private String userS, passwordS;
    private String ensureS;



    //如果账号已存在，则不允许注册
    private boolean couldRegister = false;



    @Override
    public int setLayout() {
        return R.layout.activity_register;
    }

    @Override
    public void initView() {

        backIv = bindView(R.id.back_iv);
        userEt = bindView(R.id.register_user_et);
        passwordEt = bindView(R.id.register_password_et);
        ensureEt = bindView(R.id.register_ensure_password_et);
        registerTv = bindView(R.id.register_tv);

    }

    @Override
    public void initData() {
        bindEvent();
    }

    private void bindEvent() {



        registerTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                userS = userEt.getText().toString();
                passwordS = passwordEt.getText().toString();
                ensureS = ensureEt.getText().toString();
                if (userS == null || userS.equals("")) {
                    Toast.makeText(RegisterActivity.this, "请输入用户名", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(userS.length() > 11){
                    Toast.makeText(RegisterActivity.this, "用户名过长，请修改", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(userS.length() < 6){
                    Toast.makeText(RegisterActivity.this, "用户名过短，请修改", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (passwordS == null || passwordS.equals("")) {
                    Toast.makeText(RegisterActivity.this, "请输入密码", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(passwordS.length() < 6){
                    Toast.makeText(RegisterActivity.this, "密码过短，请修改", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (ensureS == null || !passwordS.equals(ensureS)) {
                    Toast.makeText(RegisterActivity.this, "两次密码输入不一致", Toast.LENGTH_SHORT).show();
                    return;
                }

                BmobQuery<_User> query = new BmobQuery<_User>();
                query.addWhereEqualTo("username",userS);
                query.findObjects(new FindListener<_User>() {
                    @Override
                    public void done(List<_User> list, BmobException e) {
                        if(e == null){
                            if(list.size() > 0){
                                couldRegister = false;
                                Toast.makeText(RegisterActivity.this, "账号已存在，请修改", Toast.LENGTH_SHORT).show();
                            }else {
                                couldRegister = true;
                                registerUser();
                            }

                        }
                    }
                });


            }
        });

        backIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void registerUser() {
        Log.d("RegisterActivity", passwordS+"-----"+userS);
        _User user = new _User();
        user.setUsername(userS);
        user.setPassword(passwordS);
        user.signUp(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                if (s == null) {
                    Toast.makeText(RegisterActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
                    EventBus.getDefault().post(new RegisterEvent(userS, passwordS));
                    finish();
                } else {
                    Toast.makeText(RegisterActivity.this, "失败", Toast.LENGTH_SHORT).show();
                    Log.e("test", e.toString());
                }

                Log.e("test", s + "---" + e);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
