package main.nini.com.iread.fragment;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import main.nini.com.iread.R;
import main.nini.com.iread.activity.DetailBookActivity;
import main.nini.com.iread.base.BaseFrag;
import main.nini.com.iread.bean.FuzzySearchBean;
import main.nini.com.iread.my_util.Constant;
import main.nini.com.iread.my_util.InternetTool;
import main.nini.com.iread.adapter.SearchAdapter;

/**
 * Created by ${zyf} on 2016/12/8.
 */

public class SearchFragment extends BaseFrag implements View.OnClickListener, AdapterView.OnItemClickListener {

    private ListView resultList;
    private EditText contentEt;
    private TextView toTv;
    private SearchAdapter adapter;

    private static final String TAG = "SearchFragment";

    @Override
    public int setLayout() {
        return R.layout.fragment_search;
    }

    @Override
    public void initView() {
        resultList = bindView( R.id.search_result_list);
        toTv = bindView(R.id.search_to_tv);
        contentEt = bindView(R.id.search_content_et);
    }

    @Override
    public void initData() {
        toTv.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.search_to_tv:
                Editable content = contentEt.getText();
                InternetTool.getInstance().getRequest(Constant.SEARCH_ON_FUZZY + content, FuzzySearchBean.class, new Handler(new Handler.Callback() {
                    @Override
                    public boolean handleMessage(Message msg) {
                        FuzzySearchBean bean = (FuzzySearchBean) msg.obj;
                        operateData(bean);
                        return false;
                    }
                }));
                break;
        }
    }

    private void operateData(FuzzySearchBean bean) {
        Log.e(TAG, "operateData: bean：" + bean );

        if (bean != null) {
//            Log.e(TAG, "operateData: bean.getBooks() ：" + bean.getBooks() );

            if (bean.getBooks() != null && bean.getBooks().size() > 0) {
                FuzzySearchBean data = new FuzzySearchBean();
                List<FuzzySearchBean.BooksBean> booksBeen = new ArrayList<>();
                for (int i = 0; i < bean.getBooks().size(); i++) {
                    //如果该书有数据源，则将该书加入集合中
                    FuzzySearchBean.BooksBean booksBean = bean.getBooks().get(i);
                    if(booksBean.isHasCp()){
                        booksBeen.add(booksBean);
                    }
                }
                data.setBooks(booksBeen);
                adapter = new SearchAdapter(getContext(), data);
                resultList.setAdapter(adapter);
                resultList.setOnItemClickListener(this);
            }
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        FuzzySearchBean.BooksBean booksBean = (FuzzySearchBean.BooksBean) adapter.getItem(position);
        Intent toDetailBook = new Intent(getActivity(), DetailBookActivity.class);
        toDetailBook.putExtra("from",Constant.FROM_TYPE_SEARCH);
        toDetailBook.putExtra("bean",booksBean);
        startActivity(toDetailBook);
    }
}
