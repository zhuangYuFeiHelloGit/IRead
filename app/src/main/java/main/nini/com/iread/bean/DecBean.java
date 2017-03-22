package main.nini.com.iread.bean;

/**
 * Created by zyf on 2017/2/24.
 */

public class DecBean {


    /**
     * _id : 56e7ed7e8574058d6c28b981
     * name : 优质书源
     * link : http:8c3b4?cv=1487321262770
     * id : 56e7f5c88574058d6c28c3b4
     * currency : 20
     * unreadble : false
     * isVip : true
     */

    private String _id;
    private String name;
    private String link;
    private String id;
    private int currency;
    private boolean unreadble;
    private boolean isVip;

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getCurrency() {
        return currency;
    }

    public void setCurrency(int currency) {
        this.currency = currency;
    }

    public boolean isUnreadble() {
        return unreadble;
    }

    public void setUnreadble(boolean unreadble) {
        this.unreadble = unreadble;
    }

    public boolean isIsVip() {
        return isVip;
    }

    public void setIsVip(boolean isVip) {
        this.isVip = isVip;
    }
}
