package main.nini.com.iread.activity;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.icu.text.SimpleDateFormat;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.Editable;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Date;

import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;
import cn.bmob.v3.listener.UploadFileListener;
import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;
import de.greenrobot.event.ThreadMode;
import main.nini.com.iread.R;
import main.nini.com.iread.base.BaseAty;
import main.nini.com.iread.my_util.MySpUtil;
import main.nini.com.iread.my_util.event.LoginEvent;
import main.nini.com.iread.my_util.event.WriteMessageEvent;
import main.nini.com.iread.response._User;

/**
 * Created by ${zyf} on 2016/12/10.
 */

public class WriteMessageActivity extends BaseAty implements View.OnClickListener {

    private ImageView backIv, iconIv;
    private EditText nameEt;
    private TextView saveTv, fromPhoneTv, takeTv;
    private RelativeLayout chooseLayout;
    private _User user;
    private String password;
    private File file;
    private Intent intent;
    private boolean isChange;

    private static final int REQUEST_TAKE_IMAGE = 100;
    private static final int REQUEST_FROM_PHONE = 200;
    private Bitmap icon;
    private String iconPath;


    @Override
    public int setLayout() {
        return R.layout.activity_write_msg;
    }

    @Override
    public void initView() {
        EventBus.getDefault().register(this);

        backIv = bindView(R.id.back_iv);
        iconIv = bindView(R.id.write_icon_iv);
        nameEt = bindView(R.id.write_name_et);
        saveTv = bindView(R.id.write_save_tv);
        fromPhoneTv = bindView(R.id.write_from_tv);
        takeTv = bindView(R.id.write_take_tv);

        chooseLayout = bindView(R.id.write_choose_layout);
    }

    @Override
    public void initData() {

        intent = getIntent();
        isChange = intent.getBooleanExtra("isChange", false);

        backIv.setOnClickListener(this);
        saveTv.setOnClickListener(this);
        chooseLayout.setOnClickListener(this);
        fromPhoneTv.setOnClickListener(this);
        takeTv.setOnClickListener(this);
        iconIv.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_iv:

                if (!isChange) {

                    showToast();
                }else {
                    finish();
                }
                break;
            case R.id.write_save_tv:
                uploadIcon();
                break;
            case R.id.write_icon_iv:
                chooseLayout.setVisibility(View.VISIBLE);
                break;
            case R.id.write_choose_layout:
                //点击则隐藏
                chooseLayout.setVisibility(View.GONE);
                break;
            case R.id.write_take_tv:
                takePhoto();
                break;
            case R.id.write_from_tv:
                choseHeadImageFromGallery();
                break;
        }
    }

    private void showToast() {
        Toast.makeText(this, "请保存信息", Toast.LENGTH_SHORT).show();

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (isChange) {
            return super.onKeyDown(keyCode, event);
        }
        showToast();
        return true;
    }

    private void takePhoto() {
        try {

            Intent toTake = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(toTake, REQUEST_TAKE_IMAGE);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(this, "未找到照相机", Toast.LENGTH_SHORT).show();
        }

        chooseLayout.setVisibility(View.GONE);
    }

    // 从本地相册选取图片作为头像
    private void choseHeadImageFromGallery() {
        Intent intentFromGallery = new Intent();
        // 设置文件类型
        intentFromGallery.setType("image/*");
        intentFromGallery.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intentFromGallery,REQUEST_FROM_PHONE);
    }

    private static final String TAG = "WriteMessageActivity";

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // 用户没有进行有效的设置操作，返回
        if (resultCode == RESULT_CANCELED) {
            Toast.makeText(getApplication(), "取消", Toast.LENGTH_LONG).show();
            return;
        }
        if (requestCode == REQUEST_TAKE_IMAGE && resultCode == RESULT_OK) {
            if(data != null){
                Bundle bundle = data.getExtras();
                icon = (Bitmap) bundle.get("data");
                createPhotoPath();
            }
        }
        if(requestCode == REQUEST_FROM_PHONE){
            Log.e(TAG, "onActivityResult: " +data );
            if(data != null){
                Uri uri = data.getData();
                Log.e(TAG, "onActivityResult: " +uri );

                //用一个String数组存储相册所有图片
                String[] filePathColumn = { MediaStore.Images.Media.DATA };
                //用一个Cursor对象的到相册的所有内容
                Cursor cursor = getContentResolver().query(uri,
                        filePathColumn, null, null, null);
                cursor.moveToFirst();
                //得到选中图片下标
                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                //得到所选的相片路径
                 iconPath = cursor.getString(columnIndex);
                file = new File(iconPath);
                icon = BitmapFactory.decodeFile(iconPath);
                iconIv.setImageBitmap(icon);
                Log.d(TAG, iconPath);
            }
        }
    }

    /**
     * 拍照获取图片, imageUri.getPath() 图片路径
     */
    public static void getPhotoFromCamera(Activity context, int requestCode, Uri imageUri) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri); // set the image file name
        context.startActivityForResult(intent, requestCode);
    }
    //创建照片的路径
    public  void createPhotoPath() {
        FileOutputStream fileOutputStream = null;
        try {
            // 获取 SD 卡根目录
            String saveDir = Environment.getExternalStorageDirectory() + "/IReadIcons";
            // 新建目录
            File dir = new File(saveDir);
            if (!dir.exists()) dir.mkdir();
            // 生成文件名
            SimpleDateFormat t = new SimpleDateFormat("yyyyMMddssSSS");
            String filename = "icon" + (t.format(new Date())) + ".jpg";
            // 新建文件
            file = new File(saveDir, filename);
            // 打开文件输出流
            fileOutputStream = new FileOutputStream(file);
            // 生成图片文件
            this.icon.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream);
            // 相片的完整路径
            this.iconPath = file.getPath();
            Log.e(TAG, "onActivityResult: " + iconPath);
            iconIv.setImageBitmap(icon);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (fileOutputStream != null) {
                try {
                    fileOutputStream.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

    }

    //获取SD卡下的一个安全路径
    public static String getPath(String name) {
        File file = new File(getSDPath() + "/" + name);
        if (!file.exists()) {
            try {
                file.mkdirs();
            } catch (Exception e) {
                e.printStackTrace();
                return "";
            }
        }
        return file.getAbsolutePath();
    }

    //获取SD卡的路径
    public static String getSDPath() {
        return isExternalStorageWritable() ? Environment
                .getExternalStorageDirectory().getPath() : Environment
                .getDownloadCacheDirectory().getPath();
    }

    private static boolean isExternalStorageWritable() {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            return true;
        }
        return false;
    }

    private void uploadIcon() {
        if (user != null) {
            final Editable nickName = nameEt.getText();
            if (nickName.length() > 7) {
                Toast.makeText(this, "昵称长度不可长于7位", Toast.LENGTH_SHORT).show();
                return;
            }

            user.setNickName(nickName.toString());
            Log.d(TAG, "file:" + file);
            final BmobFile bmobFile = new BmobFile(file);
            bmobFile.uploadblock(new UploadFileListener() {
                @Override
                public void done(BmobException e) {
                    if(e == null){
                        Log.d(TAG, bmobFile.getUrl());
                        Log.d(TAG, bmobFile.getFileUrl());
                        Log.d(TAG, bmobFile.getLocalFile().getPath());

                        updateUser(bmobFile.getUrl());
                    }else {
                        e.printStackTrace();
                    }
                }
            });
        }
    }

    private void updateUser(String iconUrl) {
        user.setIconUrl(iconUrl);
        user.update(user.getObjectId(), new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (e == null) {
                    Log.e("test:", "更新成功：" + user.getUpdatedAt());//更改本地的缓存信息
                    MySpUtil.saveUserPasswordId(user.getUsername(), password, user.getObjectId());
                    MySpUtil.login();
                    Toast.makeText(WriteMessageActivity.this, "设置成功", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Log.e("test:", "失败：" + e.toString());
                }
            }
        });
    }

    @Subscribe(threadMode = ThreadMode.MainThread)
    public void toUpdateUser(WriteMessageEvent event) {
        this.user = event.getUser();
        this.password = event.getPassword();
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().post(new LoginEvent(user));
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }
}
