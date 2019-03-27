package com.ogawa.library;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)//代表类级别上才能使用该注解
@Retention(RetentionPolicy.SOURCE)//代表
public @interface AutoParcel {
}
