package main.nini.com.iread.bean;

/**
 * Created by zyf on 2017/2/25.
 */

public class BtocIdBean {


    /**
     * _id : 56e7ed7e8574058d6c28b981
     * source : zhuishuvip
     * name : 优质书源
     * link : http://vip.zhuishushenqi.com/toc/56e7ed7e8574058d6c28b981
     * lastChapter : 第一章 五帝破空
     * isCharge : false
     * chaptersCount : 1646
     * updated : 2017-02-17T08:47:48.079Z
     * starting : true
     * host : vip.zhuishushenqi.com
     */

    private String _id;
    private String source;
    private String name;
    private String link;
    private String lastChapter;
    private boolean isCharge;
    private int chaptersCount;
    private String updated;
    private boolean starting;
    private String host;

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
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

    public String getLastChapter() {
        return lastChapter;
    }

    public void setLastChapter(String lastChapter) {
        this.lastChapter = lastChapter;
    }

    public boolean isIsCharge() {
        return isCharge;
    }

    public void setIsCharge(boolean isCharge) {
        this.isCharge = isCharge;
    }

    public int getChaptersCount() {
        return chaptersCount;
    }

    public void setChaptersCount(int chaptersCount) {
        this.chaptersCount = chaptersCount;
    }

    public String getUpdated() {
        return updated;
    }

    public void setUpdated(String updated) {
        this.updated = updated;
    }

    public boolean isStarting() {
        return starting;
    }

    public void setStarting(boolean starting) {
        this.starting = starting;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }
}
