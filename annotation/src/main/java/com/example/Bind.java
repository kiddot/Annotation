package com.example;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * created by kiddot on 2017/04/05
 */
@Retention(RetentionPolicy.CLASS)
@Target(ElementType.FIELD)
public @interface Bind {
    int value();
}
