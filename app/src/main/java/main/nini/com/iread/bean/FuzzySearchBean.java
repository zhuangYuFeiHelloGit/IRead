package main.nini.com.iread.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by zyf on 2017/2/12.
 */

public class FuzzySearchBean implements Parcelable{

    private boolean ok;
    private List<BooksBean> books;

    public FuzzySearchBean(){

    }

    public FuzzySearchBean(Parcel in) {
        ok = in.readByte() != 0;
        books = in.createTypedArrayList(BooksBean.CREATOR);
    }

    public static final Creator<FuzzySearchBean> CREATOR = new Creator<FuzzySearchBean>() {
        @Override
        public FuzzySearchBean createFromParcel(Parcel in) {
            return new FuzzySearchBean(in);
        }

        @Override
        public FuzzySearchBean[] newArray(int size) {
            return new FuzzySearchBean[size];
        }
    };

    public boolean isOk() {
        return ok;
    }

    public void setOk(boolean ok) {
        this.ok = ok;
    }

    public List<BooksBean> getBooks() {
        return books;
    }

    public void setBooks(List<BooksBean> books) {
        this.books = books;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte((byte) (ok ? 1 : 0));
        dest.writeTypedList(books);
    }

    public static class BooksBean implements Parcelable{
        /**
         * _id : 50865988d7a545903b000009
         * hasCp : true
         * title : 斗破苍穹
         * cat : 玄幻
         * author : 天蚕土豆
         * site : zhuishuvip
         * cover : /agent/http://image.cmfu.com/books/1209977/1209977.jpg
         * shortIntro : 这里是属于斗气的世界，没有花俏艳丽的魔法，有的，仅仅是繁衍到巅峰的斗气！ 新书等级制度：斗者，斗师，大斗师，斗灵，斗王，斗皇，斗宗，斗尊，斗圣，斗帝。
         * lastChapter : 新书大主宰已发。
         * retentionRatio : 57.17
         * latelyFollower : 95563
         * wordCount : 5333548
         */

        private String _id;
        private boolean hasCp;
        private String title;
        private String cat;
        private String author;
        private String site;
        private String cover;
        private String shortIntro;
        private String lastChapter;
        private double retentionRatio;
        private int latelyFollower;
        private int wordCount;

        public BooksBean(){

        }

        protected BooksBean(Parcel in) {
            _id = in.readString();
            hasCp = in.readByte() != 0;
            title = in.readString();
            cat = in.readString();
            author = in.readString();
            site = in.readString();
            cover = in.readString();
            shortIntro = in.readString();
            lastChapter = in.readString();
            retentionRatio = in.readDouble();
            latelyFollower = in.readInt();
            wordCount = in.readInt();
        }

        public static final Creator<BooksBean> CREATOR = new Creator<BooksBean>() {
            @Override
            public BooksBean createFromParcel(Parcel in) {
                return new BooksBean(in);
            }

            @Override
            public BooksBean[] newArray(int size) {
                return new BooksBean[size];
            }
        };

        public String get_id() {
            return _id;
        }

        public void set_id(String _id) {
            this._id = _id;
        }

        public boolean isHasCp() {
            return hasCp;
        }

        public void setHasCp(boolean hasCp) {
            this.hasCp = hasCp;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getCat() {
            return cat;
        }

        public void setCat(String cat) {
            this.cat = cat;
        }

        public String getAuthor() {
            return author;
        }

        public void setAuthor(String author) {
            this.author = author;
        }

        public String getSite() {
            return site;
        }

        public void setSite(String site) {
            this.site = site;
        }

        public String getCover() {
            return cover;
        }

        public void setCover(String cover) {
            this.cover = cover;
        }

        public String getShortIntro() {
            return shortIntro;
        }

        public void setShortIntro(String shortIntro) {
            this.shortIntro = shortIntro;
        }

        public String getLastChapter() {
            return lastChapter;
        }

        public void setLastChapter(String lastChapter) {
            this.lastChapter = lastChapter;
        }

        public double getRetentionRatio() {
            return retentionRatio;
        }

        public void setRetentionRatio(double retentionRatio) {
            this.retentionRatio = retentionRatio;
        }

        public int getLatelyFollower() {
            return latelyFollower;
        }

        public void setLatelyFollower(int latelyFollower) {
            this.latelyFollower = latelyFollower;
        }

        public int getWordCount() {
            return wordCount;
        }

        public void setWordCount(int wordCount) {
            this.wordCount = wordCount;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(_id);
            dest.writeByte((byte) (hasCp ? 1 : 0));
            dest.writeString(title);
            dest.writeString(cat);
            dest.writeString(author);
            dest.writeString(site);
            dest.writeString(cover);
            dest.writeString(shortIntro);
            dest.writeString(lastChapter);
            dest.writeDouble(retentionRatio);
            dest.writeInt(latelyFollower);
            dest.writeInt(wordCount);
        }
    }
}
