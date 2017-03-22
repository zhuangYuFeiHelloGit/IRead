package main.nini.com.iread.bean;

/**
 * Created by zyf on 2017/2/24.
 */

public class RealIdBean {


    /**
     * _id : 54900223e3168fe35bc0beb2
     * lastChapter : 第十二章 哑舍·赤龙服
     * link : http://book.kanunu.org/book4/10679/
     * source : kanunu
     * name : 努努书坊
     * isCharge : false
     * chaptersCount : 13
     * updated : 2014-12-16T09:57:55.451Z
     * starting : true
     * host : book.kanunu.org
     */

    private String _id;
    private String lastChapter;
    private String link;
    private String source;
    private String name;
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

    public String getLastChapter() {
        return lastChapter;
    }

    public void setLastChapter(String lastChapter) {
        this.lastChapter = lastChapter;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
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
