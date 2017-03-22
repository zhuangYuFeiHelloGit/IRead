package main.nini.com.iread.bean;

import java.util.List;

/**
 * Created by zyf on 2017/2/24.
 */

public class BBookBean {

    /**
     * _id : 56e7ed7e8574058d6c28b981
     * name : 优质书源
     * link : http://vip.zhuishushenqi.com/toc/56e7ed7e8574058d6c28b981
     * book : 50865988d7a545903b000009
     * chapters : [{"title":"第一章 陨落的天才","link":"http://vip.zhuishushenqi.com/chapter/56e7ed818574058d6c28b98c?cv=1487321257409","id":"56e7ed818574058d6c28b98c","currency":15,"unreadble":false,"isVip":false},{"title":"第二章 斗气大陆","link":"http://vip.zhuishushenqi.com/chapter/56e7ed858574058d6c28b98f?cv=1487321257415","id":"56e7ed858574058d6c28b98f","currency":20,"unreadble":false,"isVip":false},{"title":"第三章 客人","link":"http://vip.zhuishushenqi.com/chapter/56e7ed898574058d6c28b990?cv=1487321257420","id":"56e7ed898574058d6c28b990","currency":15,"unreadble":false,"isVip":false},{"title":"第四章 云岚宗","link":"http://vip.zhuishushenqi.com/chapter/56e7ed898574058d6c28b992?cv=1487321257425","id":"56e7ed898574058d6c28b992","currency":10,"unreadble":false,"isVip":false},{"title":"第五章 聚气散","link":"http://vip.zhuishushenqi.com/chapter/56e7ed8a8574058d6c28b995?cv=1487321257430","id":"56e7ed8a8574058d6c28b995","currency":10,"unreadble":false,"isVip":false},{"title":"第六章 炼药师","link":"http://vip.zhuishushenqi.com/chapter/56e7ed998574058d6c28b998?cv=1487321257435","id":"56e7ed998574058d6c28b998","currency":15,"unreadble":false,"isVip":false},{"title":"第七章 休！","link":"http://vip.zhuishushenqi.com/chapter/56e7eda58574058d6c28b999?cv=1487321257438","id":"56e7eda58574058d6c28b999","currency":20,"unreadble":false,"isVip":false},{"title":"第八章 神秘的老者","link":"http://vip.zhuishushenqi.com/chapter/56e7eda68574058d6c28b99c?cv=1487321257442","id":"56e7eda68574058d6c28b99c","currency":15,"unreadble":false,"isVip":false},{"title":"第九章 药老！","id":"56e7edb58574058d6c28b99e","link":"http://vip.zhuishushenqi.com/chapter/56e7edb58574058d6c28b99e?cv=1487322843511","currency":20,"unreadble":false,"isVip":false}]
     * updated : 2017-02-17T08:47:48.079Z
     * host : vip.zhuishushenqi.com
     */

    private String _id;
    private String name;
    private String link;
    private String book;
    private String updated;
    private String host;
    private List<ChaptersBean> chapters;

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

    public String getBook() {
        return book;
    }

    public void setBook(String book) {
        this.book = book;
    }

    public String getUpdated() {
        return updated;
    }

    public void setUpdated(String updated) {
        this.updated = updated;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public List<ChaptersBean> getChapters() {
        return chapters;
    }

    public void setChapters(List<ChaptersBean> chapters) {
        this.chapters = chapters;
    }

    public static class ChaptersBean {
        /**
         * title : 第一章 陨落的天才
         * link : http://vip.zhuishushenqi.com/chapter/56e7ed818574058d6c28b98c?cv=1487321257409
         * id : 56e7ed818574058d6c28b98c
         * currency : 15
         * unreadble : false
         * isVip : false
         */

        private String title;
        private String link;
        private String id;
        private int currency;
        private boolean unreadble;
        private boolean isVip;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
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
}
