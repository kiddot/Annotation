package com.android.sample;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.example.Bind;
import static android.R.attr.id;


/**
 * Created by kiddo on 17-4-6.
 */

public class MainActivity extends AppCompatActivity{

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        setContentView(R.layout.activity_main);

    }
}
