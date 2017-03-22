package main.nini.com.iread.base;

import android.app.Application;
import android.content.Context;

import cn.bmob.v3.Bmob;
import cn.sharesdk.framework.ShareSDK;

/**
 * Created by ${zyf} on 2016/12/9.
 */

public class BaseApplication extends Application {

    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
        //初始化BMob
        Bmob.initialize(this, BaseContent.BMOB_KEY);

        //初始化Mob
        ShareSDK.initSDK(this);
    }

    public static Context getAppContext(){
        return context;
    }
}
