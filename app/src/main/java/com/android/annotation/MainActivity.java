package com.android.annotation;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.android.api.ViewInjector;
import com.example.Bind;

/**
 * Created by kiddo on 17-4-6.
 */

public class MainActivity extends AppCompatActivity {
    @Bind(R.id.main_tv_text)
    TextView mTvText;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.d("MainActivity", "onCreate: ");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ViewInjector.injectView(this);
        mTvText.setText("success");
    }
}
