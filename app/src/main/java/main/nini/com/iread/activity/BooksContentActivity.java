package main.nini.com.iread.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.util.TypedValue;
import android.util.Xml;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;

import main.nini.com.iread.R;
import main.nini.com.iread.base.BaseAty;
import main.nini.com.iread.my_util.sql.NativeBookTable;

/**
 * Created by zyf on 2017/3/18.
 */

public class BooksContentActivity extends BaseAty implements View.OnClickListener {

    private TextView contentTv;

    private RelativeLayout operateLayout, contentLayout;
    private Button lastBtn, nextBtn, directBtn;
    private Button upBtn, modeBtn, downBtn;

    private ScrollView scrollView;

    private Intent intent;
    //装载文件信息的对象
    private NativeBookTable table;
    //用来读取文件的RandomAccessFile
    private RandomAccessFile randomAccessFile;
    private String showContent;

    //一次读1024字节的内容
    private byte[] result = new byte[1024];

    //当前文件的指针位置
    private long currentIndex;

    private Handler handler;


    @Override
    public int setLayout() {
        return R.layout.activity_books_content;
    }

    @Override
    public void initView() {
        contentTv = bindView(R.id.books_content_tv);
        scrollView = (ScrollView) findViewById(R.id.books_scroll_view);
        operateLayout = (RelativeLayout) findViewById(R.id.books_operate_layout);
        contentLayout = (RelativeLayout) findViewById(R.id.content_layout);
        lastBtn = (Button) findViewById(R.id.books_last_btn);
        nextBtn = (Button) findViewById(R.id.books_next_btn);
        directBtn = (Button) findViewById(R.id.books_direct_btn);
        upBtn = (Button) findViewById(R.id.books_up_btn);
        downBtn = (Button) findViewById(R.id.books_down_btn);
        modeBtn = (Button) findViewById(R.id.books_mode_btn);


        operateLayout.setOnClickListener(this);
        lastBtn.setOnClickListener(this);
        nextBtn.setOnClickListener(this);
        directBtn.setOnClickListener(this);
        upBtn.setOnClickListener(this);
        modeBtn.setOnClickListener(this);
        downBtn.setOnClickListener(this);
        contentTv.setOnClickListener(this);
    }

    @Override
    public void initData() {
        intent = getIntent();
        table = intent.getParcelableExtra("book");

        handler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                contentTv.setText(showContent);
                return false;
            }
        });

        try {
            //以只读模式打开文件
            randomAccessFile = new RandomAccessFile(table.getBookFile(), "r");

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        //加载要显示的内容
        loadNext();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.books_content_tv:
                operateLayout.setVisibility(View.VISIBLE);
                break;
            case R.id.books_operate_layout:
                operateLayout.setVisibility(View.GONE);
                break;
            case R.id.books_next_btn:
                loadNext();
                break;
            case R.id.books_last_btn:
                loadLast();
                break;
            case R.id.books_direct_btn:
                finish();
                break;
            case R.id.books_up_btn:
                float upSize = contentTv.getTextSize();
                Log.e("BContentActivity", "upSize:" + upSize);
                if (upSize < 60) {

                    upSize += 3;
                    contentTv.setTextSize(TypedValue.COMPLEX_UNIT_PX, upSize);
                }
                break;
            case R.id.books_mode_btn:
                changeMode();
                operateLayout.setVisibility(View.GONE);
                break;
            case R.id.books_down_btn:

                float downSize = contentTv.getTextSize();
                Log.e("BContentActivity", "downSize:" + downSize);

                if (downSize > 28) {
                    downSize -= 3;
                    contentTv.setTextSize(TypedValue.COMPLEX_UNIT_PX, downSize);
                }
                break;
        }
    }

    //加载上一页
    private void loadLast() {
        try {
            //读取上一段内容
            //先讲文件指针的角标挪到前面两个1024长度的位置
            //比如有三百个数,你查了一百个:从0-100
            //又查了一百个:从100-200
            //那么现在角标是200
            //你想查上一段的内容
            //应该从0开始查,差一百个,获取到的内容才是正确的
            //所以这里挪两个1024的长度
            randomAccessFile.seek(currentIndex - 1024 * 2);
        } catch (IOException e) {
            e.printStackTrace();
            Log.e("BooksContentActivity", "上一页错误");
        }
        loadNext();
    }

    //加载下一页
    private void loadNext() {
        if (randomAccessFile != null) {

            try {
                randomAccessFile.read(result);
                showContent = new String(result);
                currentIndex = randomAccessFile.getFilePointer();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Log.d("BooksContentActivity", "currentIndex:" + currentIndex);
            handler.sendEmptyMessage(1);
        }
    }

    private void changeMode() {
        if (isNight()) {

            toDay();
        } else {
            toNight();
        }
        checkMode();
    }

    private void checkMode() {
        //如果当前为夜间模式,那么提示信息应该是天亮了,返回true表示为夜间模式
        if (isNight()) {
            contentTv.setTextColor(Color.GRAY);
            contentTv.setBackgroundColor(Color.BLACK);
            contentLayout.setBackgroundColor(Color.BLACK);
            modeBtn.setText("天亮了");
        } else {
            Log.d("BContentActivity", "天黑了");
            modeBtn.setText("天黑了");
            contentTv.setTextColor(Color.BLACK);
            contentTv.setBackgroundColor(Color.WHITE);
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
    private boolean isNight() {
        return getPreferences(MODE_PRIVATE).getBoolean("isBlack", false);
    }

}
