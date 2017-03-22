package main.nini.com.iread.activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import main.nini.com.iread.R;
import main.nini.com.iread.adapter.ContentDirectAdapter;
import main.nini.com.iread.base.BaseAty;
import main.nini.com.iread.bean.BDirectBean;
import main.nini.com.iread.my_util.Constant;
import main.nini.com.iread.my_util.InternetTool;

/**
 * 这是显示图书目录的页面
 * Created by zyf on 2017/2/12.
 */

public class DirectActivity extends BaseAty {

    //显示目录的listView
    private ListView directList;
    //适配器
    private ContentDirectAdapter adapter;

    private Intent intent;
    //用来获取图书资源的图书id
    private String realId;
    //打log用的tag
    private static final String TAG = "DirectActivity";
    //用来接收网络拉去的图书信息
    private BDirectBean bean;

    @Override
    public int setLayout() {
        return R.layout.activity_book_direct;
    }

    @Override
    public void initView() {
        directList = bindView(R.id.book_direct_list);
    }

    @Override
    public void initData() {
        //获得跳转的intent对象
        intent = getIntent();
        //从intent对象中获得图书id
        realId = intent.getStringExtra("realId");
        //根据id网络拉取图书信息的方法
        getBContent(realId);

        //行点击事件
        directList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if(bean.getChapters().get(position).isIsVip()){
                    Toast.makeText(DirectActivity.this, "暂不支持浏览vip章节", Toast.LENGTH_SHORT).show();
                    return;
                }
                //跳转的intent对象
                Intent toBContent = new Intent(DirectActivity.this,BContentActivity.class);
                //将图书信息对象传递到内容页面
                toBContent.putExtra("bean",bean);
                //传递过去点击的第几行，也就是要显示第几章
                toBContent.putExtra("index",position);
                //跳转页面
                startActivity(toBContent);
            }
        });
    }

    //将拉取到的数据显示在ListView上
    private void operateData(BDirectBean bean) {
        adapter = new ContentDirectAdapter(this,bean.getChapters());
        directList.setAdapter(adapter);

    }

    /**
     * 根据图书id网络拉取图书信息的方法
     * @param realId
     */
    private void getBContent(String realId) {

        //拉取网络数据
        InternetTool.getInstance().getRequest(Constant.getTypeFromHasCp(true, realId), BDirectBean.class, new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                bean = (BDirectBean) msg.obj;
                operateData(bean);
                return false;
            }
        }));
    }

}
