package com.greenlaw110.bookmark.security;

import com.greenlaw110.bookmark.model.User;
import org.osgl.aaa.AAA;
import org.osgl.aaa.AAAContext;
import org.osgl.aaa.Principal;
import org.osgl.inject.ValueLoader;

import javax.inject.Inject;

public class GetLoginUser extends ValueLoader.Base<User> {

    @Inject
    private User.Dao userDao;

    @Override
    public User get() {
        AAAContext aaaContext = AAA.context();
        if (null != aaaContext) {
            Principal principal = aaaContext.getCurrentPrincipal();
            if (null != principal) {
                return userDao.findOneBy("username", principal.getName());
            }
        }
        return null;
    }
}
