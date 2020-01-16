package com.jeanboy.module.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author caojianbo
 * @since 2020/1/16 17:18
 */
@Retention(RetentionPolicy.SOURCE)
@Target(ElementType.TYPE)
public @interface From {

    String value();
}
