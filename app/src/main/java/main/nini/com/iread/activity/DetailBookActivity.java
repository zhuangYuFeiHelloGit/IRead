package main.nini.com.iread.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.tencent.qq.QQ;
import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;
import de.greenrobot.event.ThreadMode;
import main.nini.com.iread.R;
import main.nini.com.iread.adapter.EvaluateAdapter;
import main.nini.com.iread.base.BaseAty;
import main.nini.com.iread.bean.BtocIdBean;
import main.nini.com.iread.bean.BDirectBean;
import main.nini.com.iread.bean.FuzzySearchBean;
import main.nini.com.iread.bean.LinkBean;
import main.nini.com.iread.my_util.Constant;
import main.nini.com.iread.my_util.MySpUtil;
import main.nini.com.iread.my_util.event.CollectionChangeEvent;
import main.nini.com.iread.my_util.event.DeleteEvaluateEvent;
import main.nini.com.iread.my_util.event.EvaluateEvent;
import main.nini.com.iread.my_util.InternetTool;
import main.nini.com.iread.response.Collection;
import main.nini.com.iread.response.Evaluate;

/**
 * 图书详情页
 * Created by zyf on 2017/2/12.
 */

public class DetailBookActivity extends BaseAty implements View.OnClickListener {

    private ListView evaluateList;
    private List<Evaluate> data;
    private EvaluateAdapter adapter;
    private ImageView icon, collectionIv,sharedIv;
    private TextView titleTv, authorTv;
    private Button startBtn, toEvaluateBtn;
    private Intent intent;
    private FuzzySearchBean.BooksBean booksBean;
    private Collection collection;
    private Gson gson;

    //用来拉取网络信息的bookId
    private String realId;
    private String imgUrl;
    private boolean hasCollected = false;

    //bmob中存储的collection的id（如果该书已经被收藏了的话）
    private String collectionId;

    private static final String TAG = "DetailBookActivity";

    @Override
    public int setLayout() {
        return R.layout.activity_details_book;
    }

    @Override
    public void initView() {
        evaluateList = bindView(R.id.detail_book_evaluate_list);
        icon = bindView(R.id.detail_book_iv);
        collectionIv = bindView(R.id.detail_collection_iv);
        titleTv = bindView(R.id.detail_book_title_tv);
        authorTv = bindView(R.id.detail_book_author_tv);
        startBtn = bindView(R.id.detail_book_start_btn);
        toEvaluateBtn = bindView(R.id.detail_book_evaluate_btn);
        sharedIv = bindView(R.id.detail_shared_iv);
    }

    @Override
    public void initData() {
        EventBus.getDefault().register(this);
        gson = new Gson();

        intent = getIntent();

        int from = intent.getIntExtra("from",-1);
        switch (from){
            case Constant.FROM_TYPE_SEARCH:
                fromSearch();
                break;
            case Constant.FROM_TYPE_SHELF:
                fromShelf();
                break;
        }
//        else {
//            type = TYPE_A;
//            Log.e("DetailBook：：AAA", Constant.getTypeFromHasCp(booksBean.isHasCp(), booksBean.get_id()));
//            InternetTool.getInstance().getRequest(Constant.getTypeFromHasCp(booksBean.isHasCp(), booksBean.get_id()), LinkBean.class, new Handler(new Handler.Callback() {
//                @Override
//                public boolean handleMessage(Message msg) {
//                    linkBean = (LinkBean) msg.obj;
////                    getContent(linkBean);
//                    Gson gson = new Gson();
//                    Log.e("Detail：A：success", gson.toJson(linkBean));
//                    return false;
//                }
//            }));
//        }



        startBtn.setOnClickListener(this);
        toEvaluateBtn.setOnClickListener(this);
        collectionIv.setOnClickListener(this);
        sharedIv.setOnClickListener(this);
    }

    private void fromShelf() {
        collection = (Collection) intent.getSerializableExtra("bean");

        titleTv.setText(collection.getBookName());
        authorTv.setText(collection.getAuthor());

        imgUrl = collection.getBookIconUrl();

        realId = collection.getBookId();
        Glide.with(this).load(imgUrl).into(icon);

        //检查当前书籍是否被收藏了
        checkCollection();
        getAllEvaluateForTheBook();
    }

    private void fromSearch() {
        booksBean = intent.getParcelableExtra("bean");

        titleTv.setText(booksBean.getTitle());
        authorTv.setText(booksBean.getAuthor());

        imgUrl = booksBean.getCover().replace("/agent/", "");
        Glide.with(this).load(imgUrl).into(icon);
        if (booksBean.isHasCp()) {

            Log.e("DetailBook：：BBBBBB", Constant.GET_BTOC_BY_ID(booksBean.get_id()));
            InternetTool.getInstance().getRequest(Constant.GET_BTOC_BY_ID(booksBean.get_id()), new Handler(new Handler.Callback() {
                @Override
                public boolean handleMessage(Message msg) {
                    realId = (String) msg.obj;
                    //检查当前书籍是否被收藏了
                    checkCollection();
                    getAllEvaluateForTheBook();
                    Log.e("DetailBookActivity", "dddd");
//                    getContent(realId);
                    return false;
                }
            }));

        }
    }

    private void checkCollection() {
        BmobQuery<Collection> queryColl = new BmobQuery<>();
        queryColl.addWhereEqualTo("userId", MySpUtil.getObjectId());
        queryColl.addWhereEqualTo("bookId", realId);
        Log.e("123", realId);
        queryColl.findObjects(new FindListener<Collection>() {
            @Override
            public void done(List<Collection> list, BmobException e) {
                if (list.size() > 0) {
                    Log.e("123", "list.size():" + list.size());

                    collectionId = list.get(0).getObjectId();
                    hasCollected = true;
                    collectionSuccess();

                } else {
                    hasCollected = false;
                    unCollection();
                }
            }
        });
    }

    private void getAllEvaluateForTheBook() {
        BmobQuery<Evaluate> queryEva = new BmobQuery<>();
        //查询bookId为realId的数据
        queryEva.addWhereEqualTo("bookId", realId);

        //默认会返回十条，设置成100
        queryEva.setLimit(100);

        //执行查询方法
        queryEva.findObjects(new FindListener<Evaluate>() {
            @Override
            public void done(List<Evaluate> list, BmobException e) {
                if (e == null) {
                    //查询成功
                    bindAdapter(list);
                }
            }
        });
    }

    private void bindAdapter(final List<Evaluate> list) {
        //运行在主线程
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                data = list;
                adapter = new EvaluateAdapter(DetailBookActivity.this, data);
                evaluateList.setAdapter(adapter);

            }
        });
    }

//    private void getContent(String realId) {
//        InternetTool.getInstance().getRequest(Constant.getTypeFromHasCp(booksBean.isHasCp(), realId), BDirectBean.class, new Handler(new Handler.Callback() {
//            @Override
//            public boolean handleMessage(Message msg) {
//                BDirectBean bean = (BDirectBean) msg.obj;
//
//                return false;
//            }
//        }));
//    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.detail_book_start_btn:

                Intent toContent = new Intent(this, DirectActivity.class);
                Gson gson = new Gson();
                Log.e("DetailBookActivity", gson.toJson(booksBean).toString());
//                Log.e("DetailBookActivity", booksBean.get_id());
                toContent.putExtra("realId", realId);
                startActivity(toContent);
                break;
            case R.id.detail_book_evaluate_btn:
                if (!MySpUtil.hasLogin()) {
                    Toast.makeText(this, "登录账号后可使用评论功能", Toast.LENGTH_SHORT).show();
                    return;
                }
                Intent toEvaluate = new Intent(this, InputEvaluateActivity.class);
                toEvaluate.putExtra("bookId", realId);

                startActivity(toEvaluate);

                break;
            case R.id.detail_collection_iv:
                if (!MySpUtil.hasLogin()) {
                    Toast.makeText(this, "登录账号后可使用收藏功能", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (hasCollected) {
                    Collection collection = new Collection();
                    collection.delete(collectionId, new UpdateListener() {
                        @Override
                        public void done(BmobException e) {
                            if(e == null){
                                EventBus.getDefault().post(new CollectionChangeEvent());
                                Toast.makeText(DetailBookActivity.this, "删除成功", Toast.LENGTH_SHORT).show();
                                unCollection();
                            }
                        }
                    });
                } else {

                    final Collection collection = new Collection();
                    collection.setUserId(MySpUtil.getObjectId());
                    collection.setBookId(realId);
                    collection.setAuthor(booksBean.getAuthor());
                    collection.setBookName(booksBean.getTitle());
                    collection.setBookIconUrl(imgUrl);
                    collection.save(new SaveListener<String>() {
                        @Override
                        public void done(String s, BmobException e) {
                            if (e == null) {
                                collectionId = collection.getObjectId();
                                EventBus.getDefault().post(new CollectionChangeEvent());
                                collectionSuccess();
                            } else {
                                Log.e("DetailBookActivity", e.toString());
                                Toast.makeText(DetailBookActivity.this, "收藏失败，请检查网络状况", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }

                break;
            case R.id.detail_shared_iv:
                showMyDialog();
                break;
        }
    }

    private void unCollection() {
        collectionIv.setImageResource(R.mipmap.un_collection);
        hasCollected = false;
    }
    private void collectionSuccess() {
        collectionIv.setImageResource(R.mipmap.has_collection);
        hasCollected = true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MainThread)
    public void onEvaluateListUpdate(EvaluateEvent evaluateEvent) {
        getAllEvaluateForTheBook();
        Log.e("DetailBookActivity", evaluateEvent.getEvaluate().getContent());
    }

    @Subscribe(threadMode = ThreadMode.MainThread)
    public void onEvaluateListUpdate(DeleteEvaluateEvent deleteEvaluateEvent) {


        showMyDialog(deleteEvaluateEvent);


    }

    private void sharedMsg(){
        QQ.ShareParams shareParams = new QQ.ShareParams();
//        下面这四个参数是必须的
        shareParams.setTitle("IRead");
        shareParams.setTitleUrl(imgUrl);
        shareParams.setText("推荐一本绝世好书");
        shareParams.setImageUrl(imgUrl);

        Platform qq = ShareSDK.getPlatform(QQ.NAME);
        qq.setPlatformActionListener(new PlatformActionListener() {
            @Override
            public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
                Log.e(TAG, "完成");
                Toast.makeText(DetailBookActivity.this, "分享成功", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(Platform platform, int i, Throwable throwable) {
                Log.e(TAG, "失败");
            }

            @Override
            public void onCancel(Platform platform, int i) {
                Toast.makeText(DetailBookActivity.this, "取消分享", Toast.LENGTH_SHORT).show();
            }
        });

        qq.share(shareParams);
    }

    private void showMyDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setIcon(R.mipmap.delete).setTitle("重大发现").setMessage("要和QQ上的小伙伴分享这绝世好书吗？").setPositiveButton("乐于分享", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                //去分享
                sharedMsg();
            }
        }).setNegativeButton("先睹为快", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        }).show();
    }

    private void showMyDialog(final DeleteEvaluateEvent deleteEvaluateEvent) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setIcon(R.mipmap.delete).setTitle("确定要删除该评论吗？").setMessage("注：删除操作不可逆。").setPositiveButton("说删就删", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Evaluate evaluate = deleteEvaluateEvent.getEvaluate();
                evaluate.delete(evaluate.getObjectId(), new UpdateListener() {
                    @Override
                    public void done(BmobException e) {
                        if (e == null) {
                            Log.e("DetailBookActivity", "删除成功");
                            data.remove(deleteEvaluateEvent.getPosition());
                            adapter.notifyDataSetChanged();
                            Toast.makeText(DetailBookActivity.this, "扔到了海的尽头🌊", Toast.LENGTH_SHORT).show();
                        } else {
                            Log.e("DetailBookActivity", e.toString());
                        }
                    }
                });
            }
        }).setNegativeButton("我就点点", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(DetailBookActivity.this, "别慌，还没扔呢👻", Toast.LENGTH_SHORT).show();
            }
        }).show();
    }
}
