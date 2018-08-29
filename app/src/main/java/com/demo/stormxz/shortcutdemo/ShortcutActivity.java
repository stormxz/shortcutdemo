package com.demo.stormxz.shortcutdemo;

import android.app.PendingIntent;
import android.content.Intent;
import android.content.pm.ShortcutInfo;
import android.content.pm.ShortcutManager;
import android.graphics.drawable.Icon;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.Arrays;
import java.util.List;

public class ShortcutActivity extends AppCompatActivity implements View.OnClickListener {

    private Button mAddFirstShortcut = null;
    private Button mDisableShortcut = null;
    private Button mEnableShortcut = null;
    private Button mAddPinShortcut = null;
    private Button mUpdatePinShortcut = null;
    private Button mRemovePinShortcut = null;

    private ShortcutManager mShortManager = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shortcut);

        initView();
        mShortManager = getSystemService(ShortcutManager.class);

        Log.d("stormxzxz", "size = " + mShortManager.getDynamicShortcuts().size());
    }

    /**
     * 初始化控件
     */
    private void initView() {
        mAddFirstShortcut = findViewById(R.id.add_shortcut_first);
        mAddFirstShortcut.setOnClickListener(this);

        mDisableShortcut = findViewById(R.id.disable_shortcut);
        mDisableShortcut.setOnClickListener(this);

        mEnableShortcut = findViewById(R.id.enable_shortcut);
        mEnableShortcut.setOnClickListener(this);

        mAddPinShortcut = findViewById(R.id.add_pin_shortcut);
        mAddPinShortcut.setOnClickListener(this);

        mUpdatePinShortcut = findViewById(R.id.update_shortcut);
        mUpdatePinShortcut.setOnClickListener(this);

        mRemovePinShortcut = findViewById(R.id.remove_shortcut);
        mRemovePinShortcut.setOnClickListener(this);
    }

    /**
     * 点击事件处理
     *
     * @param v 点击的View
     */
    @Override
    public void onClick(View v) {
        int viewId = v.getId();
        switch (viewId) {
            case R.id.add_shortcut_first:
                addFirstShortcut();
                break;
            case R.id.disable_shortcut:
                disableShortcut();
                break;
            case R.id.enable_shortcut:
                enShortcut();
                break;
            case R.id.add_pin_shortcut:
                addPinShortcut();
                break;
            case R.id.update_shortcut:
                updateShortcut();
                break;
            case R.id.remove_shortcut:
                removeShortcut();
                break;
        }
    }

    /**
     *  更新
     */
    private void updateShortcut() {
        ShortcutInfo mShrotcutInfo = new ShortcutInfo.Builder(this, "jumpself")
                .setIcon(Icon.createWithResource(this, R.drawable.icon_3)) //25 使用
                .setShortLabel("update jump to self")
                .setLongLabel("update jump to self activity")
                .setIntent(new Intent("action", null, this, ShowFirstShrotcutActivity.class))
                .setDisabledMessage("can not use this shortcut")
                .setRank(0)
                .build();

        mShortManager.updateShortcuts(Arrays.asList(mShrotcutInfo));
    }

    /**
     * 跳转自身activity 以及 跳转三方
     */
    private void addFirstShortcut() {
        PersistableBundle mBundle = new PersistableBundle();
        mBundle.putInt("android.intent.extras.CAMERA_FACING", 90);
//        ShortcutInfo mShrotcutInfo = new ShortcutInfo.Builder(this, "jumpself1") //用来测试静态注册后动态添加

        ShortcutInfo mShrotcutInfo = new ShortcutInfo.Builder(this, "jumpself")
                .setIcon(Icon.createWithResource(this, R.drawable.icon_4)) //25 使用
                .setShortLabel("jump to self")
                .setLongLabel("jump to self activity")
                .setIntent(new Intent("action", null, this, ShowFirstShrotcutActivity.class))
                .setDisabledMessage("can not use this shortcut")
                .setRank(0)
                .build();

//        ShortcutInfo mShrotcutInfoCAM = new ShortcutInfo.Builder(this, "jumpcamera1") //用来测试静态注册后动态添加

        ShortcutInfo mShrotcutInfoCAM = new ShortcutInfo.Builder(this, "jumpcamera")
                .setIcon(Icon.createWithResource(this, R.drawable.icon_1))
                .setShortLabel("jump to camera")
                .setLongLabel("jump to camera activity")
                .setIntent(new Intent("android.media.action.IMAGE_CAPTURE"))
                .setDisabledMessage("can not use this shortcut")
                .setRank(1)
                .build();

        mShortManager.setDynamicShortcuts(Arrays.asList(mShrotcutInfo, mShrotcutInfoCAM));
        Toast.makeText(this, "add shortcut successful", Toast.LENGTH_SHORT).show();
    }

    /**
     *  添加pin shortcut
     */
    private void addPinShortcut() {
        if (mShortManager.isRequestPinShortcutSupported()) { //26才能使用
            List<ShortcutInfo> list = mShortManager.getPinnedShortcuts();
            for (int i = 0; i < list.size(); i++) {
                if ("pinShortcutInfo".equalsIgnoreCase(list.get(i).getId())) {
                    Toast.makeText(this, "this id has already exists but disabled", Toast.LENGTH_SHORT).show();
                    return;
                }
            }
            ShortcutInfo pinShortcutInfo = new ShortcutInfo.Builder(this, "pinShortcutInfo")
                    .setIcon(Icon.createWithResource(this, R.drawable.icon_2))
                    .setShortLabel("capture")
                    .setLongLabel("capture")
                    .setIntent(new Intent("action", null, this, ShowFirstShrotcutActivity.class))
                    .setRank(1)
                    .setDisabledMessage("can not use this shortcut")
                    .build();
            Intent pinnedShortcutCallbackIntent =
                    mShortManager.createShortcutResultIntent(pinShortcutInfo);

            PendingIntent successCallback = PendingIntent.getBroadcast(this, /* request code */ 0,
                    pinnedShortcutCallbackIntent, /* flags */ 0);

            mShortManager.requestPinShortcut(pinShortcutInfo,
                    successCallback.getIntentSender());

        }
    }

    /**
     *  使shortcut 失效
     */
    private void disableShortcut() {
        if (mShortManager.isRequestPinShortcutSupported()) {
//            mShortManager.getPinnedShortcuts();
//            mShortManager.getDynamicShortcuts();

            mShortManager.disableShortcuts(Arrays.asList("pinShortcutInfo"));
        }
    }

    /**
     *  使shortcut 有效
     */
    private void enShortcut() {
        if (mShortManager.isRequestPinShortcutSupported()) {
//            mShortManager.getPinnedShortcuts();
//            mShortManager.getDynamicShortcuts();

            mShortManager.enableShortcuts(Arrays.asList("pinShortcutInfo"));
        }
    }

    /**
     *  删除非pin 的shortcut
     */
    private void removeShortcut() {
        mShortManager.removeDynamicShortcuts(Arrays.asList("jumpself", "jumpcamera"));
    }
}
