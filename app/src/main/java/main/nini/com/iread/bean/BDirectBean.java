package main.nini.com.iread.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by zyf on 2017/2/12.
 */

public class BDirectBean implements Parcelable{

    /**
     * _id : 56f50bb89ed59b661f4cf3e0
     * name : 优质书源
     * link : http://vip.zhuishushenqi.com/toc/56f50bb89ed59b661f4cf3e0
     * book : 54831bc7cc6633040d8a6256
     * updated : 2017-02-11T15:55:10.115Z
     * host : vip.zhuishushenqi.com
     */

    private String _id;
    private String name;
    private String link;
    private String book;
    private String updated;
    private String host;
    private List<ChaptersBean> chapters;

    protected BDirectBean(Parcel in) {
        _id = in.readString();
        name = in.readString();
        link = in.readString();
        book = in.readString();
        updated = in.readString();
        host = in.readString();
        chapters = in.createTypedArrayList(ChaptersBean.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(_id);
        dest.writeString(name);
        dest.writeString(link);
        dest.writeString(book);
        dest.writeString(updated);
        dest.writeString(host);
        dest.writeTypedList(chapters);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<BDirectBean> CREATOR = new Creator<BDirectBean>() {
        @Override
        public BDirectBean createFromParcel(Parcel in) {
            return new BDirectBean(in);
        }

        @Override
        public BDirectBean[] newArray(int size) {
            return new BDirectBean[size];
        }
    };

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

    public static class ChaptersBean implements Parcelable{
        /**
         * title : 第一章 若得来生重倚剑，屠尽奸邪笑苍天
         * link : http://vip.zhuishushenqi.com/chapter/56f50bb99ed59b661f4cf3ec?cv=1486463040840
         * id : 56f50bb99ed59b661f4cf3ec
         * currency : 20
         * unreadble : false
         * isVip : false
         */

        private String title;
        private String link;
        private String id;
        private String contentId;
        private int currency;
        private boolean unreadble;
        private boolean isVip;

        protected ChaptersBean(Parcel in) {
            title = in.readString();
            link = in.readString();
            id = in.readString();
            contentId = in.readString();
            currency = in.readInt();
            unreadble = in.readByte() != 0;
            isVip = in.readByte() != 0;
        }

        public static final Creator<ChaptersBean> CREATOR = new Creator<ChaptersBean>() {
            @Override
            public ChaptersBean createFromParcel(Parcel in) {
                return new ChaptersBean(in);
            }

            @Override
            public ChaptersBean[] newArray(int size) {
                return new ChaptersBean[size];
            }
        };

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

        /**
         * 根据link拆分成的数组，获得对应目录的内容的id
         * @return
         */
        public String getContentId() {
            return link.split("cv=")[1];
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(title);
            dest.writeString(link);
            dest.writeString(id);
            dest.writeString(contentId);
            dest.writeInt(currency);
            dest.writeByte((byte) (unreadble ? 1 : 0));
            dest.writeByte((byte) (isVip ? 1 : 0));
        }
    }
}
