package com.android.api;

/**
 * Created by kiddo on 17-4-6.
 */

public interface ViewInject<T> {
    void inject(T t, Object source);
}
