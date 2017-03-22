package main.nini.com.iread.bean;

import java.util.List;

/**
 * Created by zyf on 2017/2/12.
 */

public class SearchBean {

    /**
     * keywords : ["天蚕土豆","天域苍穹","天道图书馆","天骄战纪","天运老猫","天唐锦绣","天荒仙庭","天影","天启之门","天衣有风"]
     * ok : true
     */

    private boolean ok;
    private List<String> keywords;

    public boolean isOk() {
        return ok;
    }

    public void setOk(boolean ok) {
        this.ok = ok;
    }

    public List<String> getKeywords() {
        return keywords;
    }

    public void setKeywords(List<String> keywords) {
        this.keywords = keywords;
    }
}
