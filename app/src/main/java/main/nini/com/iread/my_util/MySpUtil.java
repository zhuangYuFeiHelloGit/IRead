package main.nini.com.iread.my_util;

import android.content.Context;
import android.content.SharedPreferences;

import main.nini.com.iread.base.BaseApplication;

/**
 * Created by zyf on 2017/2/8.
 */

public class MySpUtil {

    private static final String SP_USER = "user";
    private static final String PASSWORD = "password";
    private static final String USER_NAME = "userName";
    private static final String NICK_NAME = "nickName";
    private static final String ICON_URL = "iconUrl";
    private static final String IS_FROM_SINA = "fromSina";
    private static final String OBJECT_ID = "objectId";
    private static final String HAS_LOGIN = "hasLogin";
    private static final String BOOK_PATH = "bookPath";
    private static final String BOOK_NAME = "bookName";

    public static boolean hasLogin() {
        SharedPreferences sharedPreferences = getSp(SP_USER);
        boolean hasLogin = sharedPreferences.getBoolean(HAS_LOGIN, false);
        return hasLogin;
    }

    public static void login() {
        getSp(SP_USER).edit().putBoolean(HAS_LOGIN, true).commit();
    }

    public static void logout() {
        getSp(SP_USER).edit().clear().commit();
    }

    public static SharedPreferences getSp(String name) {
        SharedPreferences sharedPreferences = BaseApplication.getAppContext().getSharedPreferences(name, Context.MODE_PRIVATE);
        return sharedPreferences;
    }

    public static void saveUserPasswordId(String userName, String password, String objectId) {
        SharedPreferences sharedPreferences = getSp(SP_USER);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        if (userName != null) {

            editor.putString(USER_NAME, userName);
        }
        if (password != null) {

            editor.putString(PASSWORD, password);
        }
        if (objectId != null) {

            editor.putString(OBJECT_ID, objectId);
        }
        editor.commit();
    }

    public static void saveNickName(String nickName){
        SharedPreferences sharedPreferences = getSp(SP_USER);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        if(nickName != null){
            editor.putString(NICK_NAME,nickName);
        }
        editor.commit();
    }

    public static void saveIconUrl(String url){
        SharedPreferences sharedPreferences = getSp(SP_USER);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        if(url != null){
            editor.putString(ICON_URL,url);
        }
        editor.commit();
    }


    public static void saveFromSina(boolean isFrom){
        SharedPreferences sharedPreferences = getSp(SP_USER);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(IS_FROM_SINA,isFrom);

        editor.commit();
    }

    public static boolean isFromSina(){
        return getSp(SP_USER).getBoolean(IS_FROM_SINA,false);
    }

    public static String getIconUrl(){
        return getSp(SP_USER).getString(ICON_URL,"");
    }

    public static String getNickName(){
        return getSp(SP_USER).getString(NICK_NAME,"");
    }

    public static String getPassword() {
        return getSp(SP_USER).getString(PASSWORD, "");
    }

    public static String getUserName() {
        return getSp(SP_USER).getString(USER_NAME, "");
    }

    public static String getObjectId() {
        return getSp(SP_USER).getString(OBJECT_ID, "");
    }

    public static void saveNativeBook(){
        SharedPreferences sharedPreferences = getSp(SP_USER);
    }


}
