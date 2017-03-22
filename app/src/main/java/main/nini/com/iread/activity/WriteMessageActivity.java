package main.nini.com.iread.activity;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;

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

    private static final int REQUEST_IMAGE = 100;

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
                updateUser();
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
            startActivityForResult(toTake, REQUEST_IMAGE);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(this, "未找到照相机", Toast.LENGTH_SHORT).show();
        }

        chooseLayout.setVisibility(View.GONE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE && resultCode == RESULT_OK) {
            Uri iconUri = data.getData();
            Log.e("write", iconUri.toString() + "000--------");
            Bitmap icon = (Bitmap) data.getExtras().get("data");
//            File file = new File(iconUri.toString());


            iconIv.setImageBitmap(icon);
        }
    }

    private void updateUser() {
        if (user != null) {
            final Editable nickName = nameEt.getText();
            if (nickName.length() > 7) {
                Toast.makeText(this, "昵称长度不可长于7位", Toast.LENGTH_SHORT).show();
                return;
            }

            user.setNickName(nickName.toString());
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
