package com.demo.stormxz.shortcutdemo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

/**
 * Created by stormxz on 2018/8/28.
 */

public class ShowFirstShrotcutActivity extends Activity {

    private TextView mTextView = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.first_shortcut_lay);

        mTextView = findViewById(R.id.show);
        mTextView.setText("i am from shortcut jump to self");
        Intent intent = getIntent();
        if (intent.hasExtra("android.intent.extras.CAMERA_FACING")) {
            mTextView.setText(intent.getIntExtra("android.intent.extras.CAMERA_FACING", -1) + "");
        }
    }
}
