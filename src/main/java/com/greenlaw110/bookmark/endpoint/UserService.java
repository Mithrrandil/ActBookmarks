package com.greenlaw110.bookmark.endpoint;

import act.app.CliContext;
import act.cli.Command;
import act.cli.JsonView;
import act.cli.Required;
import act.controller.Controller;
import act.util.PropertySpec;
import com.greenlaw110.bookmark.model.Bookmark;
import com.greenlaw110.bookmark.model.User;
import org.osgl.aaa.NoAuthentication;
import org.osgl.mvc.annotation.GetAction;
import org.osgl.mvc.annotation.PostAction;
import org.osgl.util.C;

import javax.inject.Inject;
import java.util.List;

@SuppressWarnings("unused")
public class UserService {

    @Inject
    private User.Dao userDao;

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
    @PropertySpec(User.LIST_VIEW)
    public List<User> list() {
        return C.list(userDao.findAll());
    }

    @Command(name = "user.show", help = "show user")
    @JsonView
    public User show(@Required String username) {
        return userDao.findOneBy("username", username);
    }

    @Command(name = "db.init", help = "init database (Note, this will clean up all existing data!)")
    public void initDb(CliContext ctx) {
        userDao.drop();
        User user = new User("Phil", "1");
        user.addBookmark(new Bookmark("http://economist.com", "Cool reading"));
        user.addBookmark(new Bookmark("http://time.com", "Some news"));
        userDao.save(user);
        ctx.println("DB initialized with sample data");
    }

}
