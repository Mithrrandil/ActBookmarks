package com.greenlaw110.bookmark.annotation;

import com.greenlaw110.bookmark.security.GetLoginUser;
import org.osgl.inject.annotation.LoadValue;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * The annotation specify a field or parameter should be the current
 * login user
 */
@LoadValue(GetLoginUser.class)
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.PARAMETER, ElementType.FIELD})
public @interface LoginUser {
}
