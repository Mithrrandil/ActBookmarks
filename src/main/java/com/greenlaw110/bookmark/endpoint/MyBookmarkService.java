package com.greenlaw110.bookmark.endpoint;

import act.app.ActionContext;
import act.controller.Controller;
import com.greenlaw110.bookmark.annotation.LoginUser;
import com.greenlaw110.bookmark.model.Bookmark;
import com.greenlaw110.bookmark.model.User;
import org.osgl.$;
import org.osgl.http.H;
import org.osgl.mvc.annotation.DeleteAction;
import org.osgl.mvc.annotation.PostAction;
import org.osgl.mvc.annotation.PutAction;

import javax.inject.Inject;

/**
 * Operate on login user's bookmarks
 */
@Controller("/my/bookmarks")
@SuppressWarnings("unused")
public class MyBookmarkService extends Controller.Util {

    @Inject
    @LoginUser
    private User me;

    @Inject
    private User.Dao userDao;


    /**
     * A method to add a bookmark.
     * @param bookmark
     * @param context
     * @return the bookmark been added
     */
    @PostAction
    public Bookmark addBookmark(Bookmark bookmark, ActionContext context) {
        me.addBookmark(bookmark);
        userDao.save(me);
        context.resp().status(H.Status.CREATED);
        return bookmark;
    }

    /**
     * A method to edit a bookmark.
     *
     * @param bookmarkId
     * @param bookmark
     * @return the patched bookmark
     * status code.
     */
    @PutAction("{bookmarkId}")
    public Bookmark editBookmark(Long bookmarkId, Bookmark bookmark) {
        notFoundIfNot(me.hasBookmark(bookmarkId));
        badRequestIfNot($.eq(bookmarkId, bookmark.getId()));
        me.addBookmark(bookmark);
        userDao.save(me);
        return bookmark;
    }

    /**
     * A method to delete a bookmark identified by id.
     *
     * @param bookmarkId The id of the bookmark to be deleted.
     * @return ResponseEntity containing a deleted bookmark, if found, and
     * status code.
     */
    @DeleteAction("{bookmarkId}")
    public Bookmark deleteBookmark(String bookmarkId) {
        Bookmark bookmark = me.deleteBookmark(bookmarkId);
        notFoundIfNull(bookmark);
        return bookmark;
    }

}
