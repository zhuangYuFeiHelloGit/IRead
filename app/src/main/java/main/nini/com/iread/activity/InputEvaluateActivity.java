package main.nini.com.iread.activity;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import de.greenrobot.event.EventBus;
import main.nini.com.iread.R;
import main.nini.com.iread.base.BaseAty;
import main.nini.com.iread.my_util.DateUtil;
import main.nini.com.iread.my_util.MySpUtil;
import main.nini.com.iread.my_util.event.EvaluateEvent;
import main.nini.com.iread.response.Evaluate;

/**
 * Created by zyf on 2017/2/25.
 */

public class InputEvaluateActivity extends BaseAty {

    private TextView saveTv;
    private EditText contentEt;

    private Intent intent;

    private String bookId;
    private String userId,userName;

    @Override
    public int setLayout() {
        return R.layout.activity_evaluate;
    }

    @Override
    public void initView() {
        contentEt = bindView(R.id.evaluate_et);
        saveTv = bindView(R.id.evaluate_save_tv);
    }

    @Override
    public void initData() {

        intent = getIntent();

        bookId = intent.getStringExtra("bookId");

        userId = MySpUtil.getObjectId();
        userName = MySpUtil.getUserName();

        saveTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String content = contentEt.getText().toString();
                if(content.equals("")){
                    Toast.makeText(InputEvaluateActivity.this, "请输入有效评论内容", Toast.LENGTH_SHORT).show();
                    return;
                }
                final Evaluate evaluate = new Evaluate();
                evaluate.setBookId(bookId);
                evaluate.setContent(content);
                evaluate.setUserId(userId);
                evaluate.setUserName(userName);
                evaluate.setUserIconUrl(MySpUtil.getIconUrl());
                evaluate.setNickName(MySpUtil.getNickName());
                evaluate.setDate(DateUtil.getStringDateShort());
                evaluate.save(new SaveListener<String>() {
                    @Override
                    public void done(String s, BmobException e) {
                        if(e == null){

                            Log.e("InputEvaluateActivity", s+"有没有");

                            EventBus.getDefault().post(new EvaluateEvent(evaluate));
                            Toast.makeText(InputEvaluateActivity.this, "保存成功", Toast.LENGTH_SHORT).show();
                            finish();
                        }else {
                            Log.e("InputEvaluateActivity", e.toString());
                        }
                    }
                });
            }
        });
    }
}
