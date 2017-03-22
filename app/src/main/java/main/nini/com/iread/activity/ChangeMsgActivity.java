package main.nini.com.iread.activity;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;
import main.nini.com.iread.R;
import main.nini.com.iread.base.BaseAty;
import main.nini.com.iread.my_util.MySpUtil;
import main.nini.com.iread.response._User;

/**
 * Created by ${zyf} on 2016/12/10.
 */

public class ChangeMsgActivity extends BaseAty {

    private ImageView backIv;
    private TextView ensureTv;
    private EditText agoEt, newEt;
    private Intent intent;

    @Override
    public int setLayout() {
        return R.layout.activity_change_msg;
    }

    @Override
    public void initView() {
        backIv = bindView(R.id.back_iv);
        ensureTv = bindView(R.id.ensure_tv);
        newEt = bindView(R.id.new_et);
        agoEt = bindView(R.id.ago_et);
    }

    @Override
    public void initData() {
        intent = getIntent();

        backIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        ensureTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ago = agoEt.getText().toString();
                Log.e("ChangeMsgActivity", MySpUtil.getPassword());
                if (!ago.equals(MySpUtil.getPassword())) {
                    t("请输入正确的原始密码");

                } else {
                    final String change = newEt.getText().toString();
                    if (change.equals("")) {
                        t("请输入有效的密码");
                        return;
                    }
                    _User user = new _User();
                    user.setPassword(change);
                    user.update(MySpUtil.getObjectId(), new UpdateListener() {
                        @Override
                        public void done(BmobException e) {
                            if (e == null) {
                                t("修改成功");
                                MySpUtil.saveUserPasswordId(null,change,null);
                                finish();
                            }else {
                                e.printStackTrace();
                            }
                        }
                    });
                }
            }
        });
    }

    private void t(String content) {
        Toast.makeText(this, content, Toast.LENGTH_SHORT).show();
    }
}
