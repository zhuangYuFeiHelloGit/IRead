package main.nini.com.iread.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import main.nini.com.iread.R;
import main.nini.com.iread.bean.BContentBean;
import main.nini.com.iread.bean.BDirectBean;
import main.nini.com.iread.my_util.InternetTool;

/**
 * Created by zyf on 2017/2/25.
 */

public class BContentActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView bContentTv, titleTv;
    //含有整本书目录信息的bean类
    private BDirectBean bDirectBean;
    //含有某一章内容的bean类
    private BContentBean bContentBean;
    private Intent intent;
    private String showUri;
    private int index;
    private RelativeLayout operateLayout,contentLayout;
    private Button lastBtn, nextBtn, directBtn;
    private Button upBtn, modeBtn, downBtn;

    private ScrollView scrollView;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_b_content);
        intent = getIntent();
        bDirectBean = intent.getParcelableExtra("bean");
        index = intent.getIntExtra("index", 0);
        bContentTv = (TextView) findViewById(R.id.b_content_tv);
        titleTv = (TextView) findViewById(R.id.b_title_tv);
        scrollView = (ScrollView) findViewById(R.id.b_scroll_view);
        operateLayout = (RelativeLayout) findViewById(R.id.b_operate_layout);
        contentLayout = (RelativeLayout) findViewById(R.id.content_layout);
        lastBtn = (Button) findViewById(R.id.b_last_btn);
        nextBtn = (Button) findViewById(R.id.b_next_btn);
        directBtn = (Button) findViewById(R.id.b_direct_btn);
        upBtn = (Button) findViewById(R.id.b_up_btn);
        downBtn = (Button) findViewById(R.id.b_down_btn);
        modeBtn = (Button) findViewById(R.id.b_mode_btn);


        operateLayout.setOnClickListener(this);
        lastBtn.setOnClickListener(this);
        nextBtn.setOnClickListener(this);
        bContentTv.setOnClickListener(this);
        directBtn.setOnClickListener(this);
        upBtn.setOnClickListener(this);
        modeBtn.setOnClickListener(this);
        downBtn.setOnClickListener(this);

        checkMode();

        loadPageContent();
    }

    private void loadPageContent() {
        InternetTool.getInstance().getRequest(getPageUri(), BContentBean.class, new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                bContentBean = (BContentBean) msg.obj;
                bContentTv.setText(bContentBean.getChapter().getCpContent());
                titleTv.setText(bContentBean.getChapter().getTitle());
                scrollView.smoothScrollTo(0, 0);
                operateLayout.setVisibility(View.GONE);
                return false;
            }
        }));
    }

    private String getPageUri() {
        return "http://chapter2.zhuishushenqi.com/chapter/" + bDirectBean.getChapters().get(index).getLink();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.b_content_tv:
                operateLayout.setVisibility(View.VISIBLE);
                break;
            case R.id.b_operate_layout:
                operateLayout.setVisibility(View.GONE);
                break;
            case R.id.b_next_btn:
                if (index != bDirectBean.getChapters().size() - 1) {

                    index++;
                    loadPageContent();
                }
                break;
            case R.id.b_last_btn:
                if (index != 0) {
                    index--;
                    loadPageContent();
                }
                break;
            case R.id.b_direct_btn:
                finish();
                break;
            case R.id.b_up_btn:
                float upSize = bContentTv.getTextSize();
                Log.e("BContentActivity", "upSize:" + upSize);
                if (upSize < 45) {

                    upSize += 3;
                    bContentTv.setTextSize(TypedValue.COMPLEX_UNIT_PX,upSize);
                }
                break;
            case R.id.b_mode_btn:
                changeMode();
                operateLayout.setVisibility(View.GONE);
                break;
            case R.id.b_down_btn:

                float downSize = bContentTv.getTextSize();
                Log.e("BContentActivity", "downSize:" + downSize);

                if (downSize > 28) {
                    downSize -= 3;
                    bContentTv.setTextSize(TypedValue.COMPLEX_UNIT_PX,downSize);
                }
                break;
        }
    }

    private void changeMode() {
        if(isNight()){

            toDay();
        }else {
            toNight();
        }
        checkMode();
    }

    private void checkMode() {
        //如果当前为夜间模式,那么提示信息应该是天亮了,返回true表示为夜间模式
        if (isNight()) {
            bContentTv.setTextColor(Color.GRAY);
            bContentTv.setBackgroundColor(Color.BLACK);
            titleTv.setTextColor(Color.GRAY);
            titleTv.setBackgroundColor(Color.BLACK);
            contentLayout.setBackgroundColor(Color.BLACK);
            modeBtn.setText("天亮了");
        } else {
            Log.d("BContentActivity", "天黑了");
            modeBtn.setText("天黑了");
            bContentTv.setTextColor(Color.BLACK);
            bContentTv.setBackgroundColor(Color.WHITE);
            titleTv.setTextColor(Color.BLACK);
            titleTv.setBackgroundColor(Color.WHITE);
            contentLayout.setBackgroundColor(Color.WHITE);
        }
    }

    private void toDay() {

        //如果为true,说明是夜间模式,应该改为白天模式
        getPreferences(MODE_PRIVATE).edit().putBoolean("isBlack", false).commit();
    }


    private void toNight() {

        //如果为true,说明是夜间模式,应该改为白天模式
        getPreferences(MODE_PRIVATE).edit().putBoolean("isBlack", true).commit();
    }

    //判断当前显示状态
    private boolean isNight(){
        return getPreferences(MODE_PRIVATE).getBoolean("isBlack", false);
    }
}
