package main.nini.com.iread.bean;

import java.util.List;

/**
 * Created by zyf on 2017/2/24.
 */

public class LinkBean {

    /**
     * mixToc : {"_id":"5490022449f1ad546a4a70d6","book":"54900223e3168fe35bc0beac","chaptersUpdated":"2016-11-16T19:31:07.666Z","chapters":[{"title":" 楔子","link":"http://book.kanunu.org/book4/10679/188439.html","unreadble":false},{"title":" 第一章 哑舍·古镜","link":"http://book.kanunu.org/book4/10679/188440.html","unreadble":false},{"title":" 第二章nbspnbsp哑舍·香妃链","link":"http://book.kanunu.org/book4/10679/188441.html","unreadble":false},{"title":" 第三章nbspnbsp哑舍·人鱼烛","link":"http://book.kanunu.org/book4/10679/188442.html","unreadble":false},{"title":" 第四章nbspnbsp哑舍·黄粱枕","link":"http://book.kanunu.org/book4/10679/188443.html","unreadble":false},{"title":" 第五章nbspnbsp哑舍·越王剑","link":"http://book.kanunu.org/book4/10679/188444.html","unreadble":false},{"title":" 第六章nbspnbsp哑舍·山海经","link":"http://book.kanunu.org/book4/10679/188445.html","unreadble":false},{"title":" 第七章nbspnbsp哑舍·水苍玉","link":"http://book.kanunu.org/book4/10679/188446.html","unreadble":false},{"title":" 第八章nbspnbsp哑舍·巫蛊偶","link":"http://book.kanunu.org/book4/10679/188447.html","unreadble":false},{"title":" 第九章nbspnbsp哑舍·虞美人","link":"http://book.kanunu.org/book4/10679/188448.html","unreadble":false},{"title":" 第十章nbspnbspnbspnbsp哑舍·白蛇伞","link":"http://book.kanunu.org/book4/10679/188449.html","unreadble":false},{"title":" 第十一章nbspnbsp哑舍·长命锁","link":"http://book.kanunu.org/book4/10679/188450.html","unreadble":false},{"title":" 第十二章 哑舍·赤龙服","link":"http://book.kanunu.org/book4/10679/188451.html","unreadble":false}],"updated":"2016-11-16T19:31:07.666Z"}
     * ok : true
     */

    private MixTocBean mixToc;
    private boolean ok;

    public MixTocBean getMixToc() {
        return mixToc;
    }

    public void setMixToc(MixTocBean mixToc) {
        this.mixToc = mixToc;
    }

    public boolean isOk() {
        return ok;
    }

    public void setOk(boolean ok) {
        this.ok = ok;
    }

    public static class MixTocBean {
        /**
         * _id : 5490022449f1ad546a4a70d6
         * book : 54900223e3168fe35bc0beac
         * chaptersUpdated : 2016-11-16T19:31:07.666Z
         * chapters : [{"title":" 楔子","link":"http://book.kanunu.org/book4/10679/188439.html","unreadble":false},{"title":" 第一章 哑舍·古镜","link":"http://book.kanunu.org/book4/10679/188440.html","unreadble":false},{"title":" 第二章nbspnbsp哑舍·香妃链","link":"http://book.kanunu.org/book4/10679/188441.html","unreadble":false},{"title":" 第三章nbspnbsp哑舍·人鱼烛","link":"http://book.kanunu.org/book4/10679/188442.html","unreadble":false},{"title":" 第四章nbspnbsp哑舍·黄粱枕","link":"http://book.kanunu.org/book4/10679/188443.html","unreadble":false},{"title":" 第五章nbspnbsp哑舍·越王剑","link":"http://book.kanunu.org/book4/10679/188444.html","unreadble":false},{"title":" 第六章nbspnbsp哑舍·山海经","link":"http://book.kanunu.org/book4/10679/188445.html","unreadble":false},{"title":" 第七章nbspnbsp哑舍·水苍玉","link":"http://book.kanunu.org/book4/10679/188446.html","unreadble":false},{"title":" 第八章nbspnbsp哑舍·巫蛊偶","link":"http://book.kanunu.org/book4/10679/188447.html","unreadble":false},{"title":" 第九章nbspnbsp哑舍·虞美人","link":"http://book.kanunu.org/book4/10679/188448.html","unreadble":false},{"title":" 第十章nbspnbspnbspnbsp哑舍·白蛇伞","link":"http://book.kanunu.org/book4/10679/188449.html","unreadble":false},{"title":" 第十一章nbspnbsp哑舍·长命锁","link":"http://book.kanunu.org/book4/10679/188450.html","unreadble":false},{"title":" 第十二章 哑舍·赤龙服","link":"http://book.kanunu.org/book4/10679/188451.html","unreadble":false}]
         * updated : 2016-11-16T19:31:07.666Z
         */

        private String _id;
        private String book;
        private String chaptersUpdated;
        private String updated;
        private List<ChaptersBean> chapters;

        public String get_id() {
            return _id;
        }

        public void set_id(String _id) {
            this._id = _id;
        }

        public String getBook() {
            return book;
        }

        public void setBook(String book) {
            this.book = book;
        }

        public String getChaptersUpdated() {
            return chaptersUpdated;
        }

        public void setChaptersUpdated(String chaptersUpdated) {
            this.chaptersUpdated = chaptersUpdated;
        }

        public String getUpdated() {
            return updated;
        }

        public void setUpdated(String updated) {
            this.updated = updated;
        }

        public List<ChaptersBean> getChapters() {
            return chapters;
        }

        public void setChapters(List<ChaptersBean> chapters) {
            this.chapters = chapters;
        }

        public static class ChaptersBean {
            /**
             * title :  楔子
             * link : http://book.kanunu.org/book4/10679/188439.html
             * unreadble : false
             */

            private String title;
            private String link;
            private boolean unreadble;

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

            public boolean isUnreadble() {
                return unreadble;
            }

            public void setUnreadble(boolean unreadble) {
                this.unreadble = unreadble;
            }
        }
    }
}
