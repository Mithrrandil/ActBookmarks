package com.greenlaw110.bookmark.security;

import act.aaa.ActAAAService;
import com.greenlaw110.bookmark.model.User;
import org.osgl.util.C;

import java.util.Collection;

/**
 * This class bridge the application to Act-AAA security framework
 */
public class AppSecurityService extends ActAAAService.Base<User> {

    @Override
    protected String nameOf(User user) {
        return user.getUsername();
    }

    @Override
    protected Integer privilegeOf(User user) {
        return null;
    }

    @Override
    protected Collection<String> rolesOf(User user) {
        return C.list();
    }

    @Override
    protected boolean verifyPassword(User user, char[] chars) {
        return user.passwordMatches(chars);
    }

}
