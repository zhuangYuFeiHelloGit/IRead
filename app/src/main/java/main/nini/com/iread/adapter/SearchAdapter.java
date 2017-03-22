package main.nini.com.iread.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import main.nini.com.iread.R;
import main.nini.com.iread.bean.FuzzySearchBean;

/**
 * Created by zyf on 2017/2/12.
 */

public class SearchAdapter extends BaseAdapter {

    private Context context;
    private FuzzySearchBean bean;
    private List<FuzzySearchBean.BooksBean> books;

    public SearchAdapter(Context context, FuzzySearchBean bean) {
        this.context = context;
        this.bean = bean;
        this.books = bean.getBooks();
    }

    @Override
    public int getCount() {
        return books.size();
    }

    @Override
    public Object getItem(int position) {
        return books.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        SearchHolder holder = null;
        FuzzySearchBean.BooksBean booksBean = books.get(position);
        if(convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.item_search_frag,parent,false);
            holder = new SearchHolder(convertView);
            convertView.setTag(holder);
        }else {
            holder = (SearchHolder) convertView.getTag();
        }
        String title = booksBean.getTitle();
        String author = booksBean.getAuthor();
        if(title != null){
            holder.titleTv.setText(title);
        }
        if(author != null){
            holder.authorTv.setText(author);
        }
        return convertView;
    }

    class SearchHolder{
        TextView titleTv,authorTv;

        public SearchHolder(View view) {
            titleTv = (TextView) view.findViewById(R.id.item_search_title_tv);
            authorTv = (TextView) view.findViewById(R.id.item_search_author_tv);
        }
    }
}
