package main.nini.com.iread.my_util;

import android.graphics.Color;

import java.util.Random;

/**
 * Created by zyf on 2017/3/12.
 */

public class ColorUtil {
    public static int getRandomColor(Random random){
        int red = random.nextInt(256);
        int green = random.nextInt(256);
        int blue = random.nextInt(256);
        int alpha = random.nextInt(31) + 40;

        return Color.argb(alpha,red,green,blue);
    }
}
