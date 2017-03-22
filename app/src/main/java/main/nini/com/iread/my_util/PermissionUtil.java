package main.nini.com.iread.my_util;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;

import main.nini.com.iread.base.BaseApplication;

/**
 * Created by zyf on 2017/3/12.
 */

public class PermissionUtil {

    public static final String PERMISSION_SDCARD = "android.permission.WRITE_EXTERNAL_STORAGE";
    public static final int REQUEST_SDCARD = 200;

    /**
     *
     * @param activity
     * @param permission
     * @param requestCode
     * @return 返回true的话，说明权限已经授予<br />否则去请求权限
     */
    public static boolean checkPermission(Activity activity, String permission, int requestCode){
        int result = ContextCompat.checkSelfPermission(activity,permission);
        if(result == PackageManager.PERMISSION_DENIED){
            //拒绝了，需要手动请求权限
            ActivityCompat.requestPermissions(activity,new String[]{permission},requestCode);
            return false;
        }
        return true;

    }
}
