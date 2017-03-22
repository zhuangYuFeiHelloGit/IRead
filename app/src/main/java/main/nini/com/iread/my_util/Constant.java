package main.nini.com.iread.my_util;

/**
 * Created by zyf on 2017/2/12.
 */

public class Constant {
    //搜索的url
    public static String SEARCH_BY_NAME =
            "http://api01pbmp.zhuishushenqi.com/book/auto-complete?query=";

    //模糊搜索的url
    public static String SEARCH_ON_FUZZY =
            "http://api01pbmp.zhuishushenqi.com/book/fuzzy-search?query=";

    public  static String data = "http://api02u58f.zhuishushenqi.com/";

    public static final int FROM_TYPE_SHELF = 0;
    public static final int FROM_TYPE_SEARCH = 1;


    public static String BOOK = "http://chapter2.zhuishushenqi.com/chapter/http://vip.zhuishushenqi.com/chapter/";//+"?cv=1487321257409";
    public static String getGetBookList(String bookId){
        return BOOK + bookId ;
    }
    public static String getTypeFromHasCp(boolean hasCp,String _id){
        if(hasCp){

            return data+"b"+"toc/"+_id+"?view=chapters";
        }
        return data+"mix-a"+"toc/"+_id;
    }


    public static String GET_BTOC_BY_ID(String bookId){
        return "http://api02u58f.zhuishushenqi.com/btoc?view=summary&book="+bookId;
    }

}
