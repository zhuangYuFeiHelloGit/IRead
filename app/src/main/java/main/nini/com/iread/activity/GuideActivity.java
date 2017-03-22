package main.nini.com.iread.activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import main.nini.com.iread.R;
import main.nini.com.iread.base.BaseAty;

/**
 * Created by ${zyf} on 2016/12/10.
 */

public class GuideActivity extends BaseAty {

    private ProgressBar progressBar;
    private Handler handler;
    private boolean need = true;

    @Override
    public int setLayout() {
        return R.layout.activity_guide;
    }

    @Override
    public void initView() {
        progressBar = bindView(R.id.guide_time_bar);

        progressBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                need = false;
                startActivity(new Intent(GuideActivity.this, MainActivity.class));
                finish();
            }
        });
    }

    @Override
    public void initData() {

        handler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                if (msg.what == 0) {
                    startActivity(new Intent(GuideActivity.this, MainActivity.class));
                    finish();
                }
                return false;
            }
        });

        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 20; i >= 0; i--) {
                    if (need) {
                        try {
                            Thread.sleep(100);
                            handler.sendEmptyMessage(i);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    } else {
                        return;
                    }
                }
            }
        }).start();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        need = false;
    }
}
