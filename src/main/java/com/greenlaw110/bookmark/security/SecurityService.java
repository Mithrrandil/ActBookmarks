package com.greenlaw110.bookmark.security;

import act.aaa.ActAAAService;
import act.util.SingletonBase;
import com.greenlaw110.bookmark.model.User;
import org.osgl.aaa.Principal;
import org.osgl.aaa.impl.SimplePrincipal;
import org.osgl.util.E;

import javax.inject.Inject;

@SuppressWarnings("unused")
public class SecurityService extends SingletonBase implements ActAAAService {

    private User.Dao userDao;

    @Inject
    public SecurityService(User.Dao userDao) {
        this.userDao = userDao;
    }

    @Override
    public void save(Principal principal) {
        throw E.unsupport();
    }

    @Override
    public void removeAllPrincipals() {
        throw E.unsupport();
    }

    @Override
    public Principal findByName(String name) {
        User user = userDao.findOneBy("username", name);
        return null == user ? null : getPrincipal(user);
    }

    @Override
    public Principal authenticate(String email, String password) {
        User user = userDao.authenticate(email, password);
        return null == user ? null : getPrincipal(user);
    }

    private Principal getPrincipal(User user) {
        SimplePrincipal.Builder pb = new SimplePrincipal.Builder(user.getUsername());
        return pb.toPrincipal();
    }
}
