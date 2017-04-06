package com.android.annotation;

import android.content.Context;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import com.android.api.ViewInjector;
import com.example.Bind;

/**
 * Created by kiddo on 17-4-6.
 */

public class MainActivity extends AppCompatActivity {
//    @Bind(R.id.main_tv_text)
//    TextView mTvText;

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        setContentView(R.layout.activity_main);
        //ViewInjector.injectView(this);
        //mTvText.setText("success");
    }
}
