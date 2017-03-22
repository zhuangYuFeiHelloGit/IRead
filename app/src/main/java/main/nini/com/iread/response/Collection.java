package main.nini.com.iread.response;

import cn.bmob.v3.BmobObject;

/**
 * Created by zyf on 2017/2/25.
 */

public class Collection extends BmobObject {
    private String userId;
    private String bookName;
    private String bookId;
    private String bookIconUrl;
    private String author;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    public String getBookIconUrl() {
        return bookIconUrl;
    }

    public void setBookIconUrl(String bookIconUrl) {
        this.bookIconUrl = bookIconUrl;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
}
