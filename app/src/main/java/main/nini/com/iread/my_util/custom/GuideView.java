package main.nini.com.iread.my_util.custom;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import java.util.Random;

/**
 * Created by ${zyf} on 2016/12/10.
 */

public class GuideView extends View {

    private String[] titles = new String[]{"男生", "爱阅读","爱阅读","女生", "武侠","爱阅读", "言情","爱阅读", "同人", "网游"};
    private Random random;
    private Paint paint;
    private int width, height;
    private int number;
    private final int TEXT_SIZE = 60;//提示文字的大小
    private final int MAX_NUMBER = 4;//最大显示的提示数量
    private final int POST_TIME = 500;//刷新时间，毫秒

    public GuideView(Context context) {
        super(context);
        init();
    }

    private void init() {
        //初始化随机类对象，可通过它获取一定范围内的随机数
        random = new Random();
        //初始化画笔对象
        paint = new Paint();
        //设置抗锯齿
        paint.setAntiAlias(true);
        //对位图进行滤波处理
        paint.setFilterBitmap(true);
        //设置文字大小
        paint.setTextSize(TEXT_SIZE);
    }

    public GuideView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //获得组件宽度与高度
        width = getMeasuredWidth();
        height = getMeasuredHeight();
        //设置画笔颜色
        setPaintColor(paint);
        //随机获得显示数量
        number = random.nextInt(MAX_NUMBER);
        if(number == 0){
            number++;
        }
        for (int i = 0; i < number; i++) {
            // 随机获得横坐标
            int x = random.nextInt(width - 100);
            //随机获得纵坐标
            int y = random.nextInt(height - 100);

            if(x < 50){
                x+=100;
            }
            if(y < 50){
                y+=100;
            }

            //绘制文字      文字内容                                           使用这个画笔对象绘制
            canvas.drawText(titles[random.nextInt(titles.length)], x, y, paint);
        }

        //延时刷新界面
        postInvalidateDelayed(POST_TIME);
    }

    /***
     * 随机设置画笔颜色
     * @param paint  需要被设置的画笔对象
     */
    private void setPaintColor(Paint paint) {
        int r = random.nextInt(256);
        int g = random.nextInt(256);
        int b = random.nextInt(256);

        paint.setARGB(255, r, g, b);
    }
}
