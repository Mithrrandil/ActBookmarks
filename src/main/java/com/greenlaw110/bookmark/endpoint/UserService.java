package com.greenlaw110.bookmark.endpoint;

import act.cli.Command;
import act.cli.JsonView;
import act.cli.Required;
import act.controller.Controller;
import act.util.PropertySpec;
import com.greenlaw110.bookmark.model.User;
import org.osgl.aaa.NoAuthentication;
import org.osgl.mvc.annotation.GetAction;
import org.osgl.mvc.annotation.PostAction;
import org.osgl.util.C;

import javax.inject.Inject;
import java.util.List;

public class UserService {

    private User.Dao userDao;

    @Inject
    public UserService(User.Dao userDao) {
        this.userDao = userDao;
    }

    @Command(name = "user.add", help = "add an new user")
    @JsonView
    public User addUser(
            @Required String username,
            @Required String password
    ) {
        User user = new User(username, password);
        userDao.save(user);
        return user;
    }

    @Command(name = "user.list", help = "list all users")
    public List<User> list() {
        return C.list(userDao.findAll());
    }

    @Command(name = "user.show", help = "show user")
    @JsonView
    @PropertySpec(User.DETAIL_VIEW)
    public User show(@Required String username) {
        return userDao.findOneBy("username", username);
    }

}
