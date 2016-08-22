package com.greenlaw110.bookmark.endpoint;

import act.app.CliContext;
import act.app.DbServiceManager;
import act.cli.Command;
import act.cli.JsonView;
import act.cli.Required;
import act.db.morphia.MorphiaService;
import act.util.PropertySpec;
import com.greenlaw110.bookmark.model.Bookmark;
import com.greenlaw110.bookmark.model.User;
import org.osgl.inject.annotation.Provided;
import org.osgl.util.C;

import javax.inject.Inject;
import java.util.List;

/**
 * The endpoint provides CLI commands for adding/visiting
 * users
 */
@SuppressWarnings("unused")
public class UserService {

    @Inject
    private User.Dao userDao;

    @Command(name = "user.add", help = "add an new user")
    @PropertySpec(User.DETAIL_VIEW)
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
    @PropertySpec(User.DETAIL_VIEW)
    @JsonView
    public User show(@Required String username) {
        return userDao.findOneBy("username", username);
    }

    @Command(name = "db.init", help = "init database (Note, this will clean up all existing data!)")
    public void initDb(
            @Provided DbServiceManager dbServiceManager,
            @Provided CliContext ctx
    ) {
        resetDatabase(dbServiceManager);

        createSample("Phil", "1", "economist.com::Cool reading", "time.com::Some news");
        createSample("Pete", "2", "github.com::A playground", "ibm.com::Be cautious");

        ctx.println("DB initialized with sample data");
    }

    private void resetDatabase(DbServiceManager dbServiceManager) {
        MorphiaService svc = dbServiceManager.dbService(DbServiceManager.DEFAULT);
        svc.ds().getDB().dropDatabase();
    }

    private void createSample(String username, String password, String... bookmarks) {
        User user = new User(username, password);
        for (String bookmark : bookmarks) {
            String[] sa = bookmark.split("::");
            String url = sa[0];
            String desc = sa[1];
            if (!url.startsWith("http")) {
                url = "http://" + url;
            }
            user.addBookmark(new Bookmark(url, desc));
        }
        userDao.save(user);
    }

}
