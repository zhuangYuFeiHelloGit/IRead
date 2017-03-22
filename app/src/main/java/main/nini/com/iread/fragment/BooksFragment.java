package main.nini.com.iread.fragment;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.Toast;

import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.will.filesearcher.file_searcher.FileSearcherActivity;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import main.nini.com.iread.R;
import main.nini.com.iread.activity.BooksContentActivity;
import main.nini.com.iread.adapter.BooksAdapter;
import main.nini.com.iread.base.BaseFrag;
import main.nini.com.iread.my_util.BooksDecoration;
import main.nini.com.iread.my_util.ExecutorSingle;
import main.nini.com.iread.my_util.PermissionUtil;
import main.nini.com.iread.my_util.inter.OnRecyclerClickListener;
import main.nini.com.iread.my_util.sql.MyOrmHelper;
import main.nini.com.iread.my_util.sql.NativeBookTable;

/**
 * Created by ${zyf} on 2016/12/8.
 */

public class BooksFragment extends BaseFrag implements OnRecyclerClickListener {

    private static final int REQUEST_CODE = 2001;
    private static final int WHAT_REFRESH_COMPLETED = 100;
    private static final int WHAT_OPERATE_DB_COMPLETED = 200;
    /**
     * 下拉刷新的动画持续时间
     */
    private static final int TIME_REFRESH = 2000;
    private static final String TAG = "BooksFragment";
    private Handler handler;
    private String str;
    private XRecyclerView recyclerView;
    private List<NativeBookTable> data;

    private BooksAdapter booksAdapter;
    private View footView;
    private boolean couldObtainBook = true;

    @Override
    public int setLayout() {
        return R.layout.fragment_books;
    }

    @Override
    public void initView() {
        recyclerView = bindView(R.id.books_recycler);

    }

    @Override
    public void initData() {
        data = new ArrayList<>();
        PermissionUtil.checkPermission(getActivity(), PermissionUtil.PERMISSION_SDCARD, PermissionUtil.REQUEST_SDCARD);

        handler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {

                if (msg.what == WHAT_REFRESH_COMPLETED) {
                    recyclerView.refreshComplete();
                }
                booksAdapter.notifyDataSetChanged();
                return false;
            }
        });

        booksAdapter = new BooksAdapter(data, getContext());
        booksAdapter.setListener(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(booksAdapter);

        footView = getActivity().getLayoutInflater().inflate(R.layout.foot_native_book, recyclerView, false);
        footView.findViewById(R.id.native_add_tv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (couldObtainBook) {

                    Intent intent = new Intent(getContext(), FileSearcherActivity.class);
                    //固定配置，这么写就可以
                    intent.putExtra("keyword", ".txt");
                    intent.putExtra("min", 50 * 1024);
                    intent.putExtra("theme", -1);

                    startActivityForResult(intent, REQUEST_CODE);
                } else {
                    toast("Sorry，未授权~");
                }
            }
        });
        recyclerView.addFootView(footView);
        recyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                handler.sendEmptyMessageDelayed(WHAT_REFRESH_COMPLETED, TIME_REFRESH);

            }

            @Override
            public void onLoadMore() {

            }
        });
        recyclerView.addItemDecoration(new BooksDecoration(20));

        ExecutorSingle.getInstance().execute(new Runnable() {
            @Override
            public void run() {
                booksAdapter.addAll(MyOrmHelper.getInstance(getContext()).queryBooks());
                handler.sendEmptyMessage(WHAT_OPERATE_DB_COMPLETED);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE && resultCode == FileSearcherActivity.OK && data != null) {
            final ArrayList<File> list = (ArrayList<File>) data.getSerializableExtra("data");
            Toast.makeText(getContext(), "you selected" + list.size() + "items", Toast.LENGTH_SHORT).show();
            ExecutorSingle.getInstance().execute(new Runnable() {
                @Override
                public void run() {
                    MyOrmHelper helper = MyOrmHelper.getInstance(getContext());

                    for (int i = 0; i < list.size(); i++) {
                        File file = list.get(i);
                        String name = file.getName();
                        if(!helper.containBook(name)){
                            String path = file.getPath();
                            NativeBookTable bookTable = new NativeBookTable(name, path);
                            booksAdapter.add(bookTable);
                            helper.insertBook(bookTable);
                        }
                    }

                    handler.sendEmptyMessage(WHAT_OPERATE_DB_COMPLETED);
                }
            });


        }
    }

    //读取本地图书内容
    private void initNativeBook() {
        List<File> list = new ArrayList();
        File urlFile = new File(list.get(0).getAbsolutePath());
        try {

            InputStreamReader isr = new InputStreamReader(new FileInputStream(urlFile), "UTF-8");
            BufferedReader br = new BufferedReader(isr);
            str = "";
            String mimeTypeLine = null;
            while ((mimeTypeLine = br.readLine()) != null) {
                str = str + mimeTypeLine;
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onItemClick(int pos) {
        Intent toBooksContent = new Intent(getActivity(), BooksContentActivity.class);
        toBooksContent.putExtra("book",data.get(pos));
        startActivity(toBooksContent);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PermissionUtil.REQUEST_SDCARD && grantResults[0] == PackageManager.PERMISSION_DENIED) {
            couldObtainBook = false;
            toast("您未授权，不可获得SD卡内容，可以在设置中进行更改");
        }
    }
}
