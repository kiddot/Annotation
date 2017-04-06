package com.android.api;

import android.app.Activity;
import android.view.View;

/**
 * Created by kiddo on 17-4-6.
 */

public enum Finder {
    VIEW
            {
                @Override
                public View findView(Object source, int id){
                    return ((View) source).findViewById(id);
                }
            },
    ACTIVITY
            {
                @Override
                public View findView(Object source, int id) {
                    return ((Activity) source).findViewById(id);
                }
            };
    public abstract View findView(Object source, int id);
}
