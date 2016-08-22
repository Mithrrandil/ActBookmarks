/*
 * The MIT License
 *
 * Copyright 2016 Dmitry Noranovich <javaeeeee at gmail dot com>.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.greenlaw110.bookmark.model;

import act.Act;
import act.db.morphia.MorphiaDao;
import act.db.morphia.MorphiaModel;
import org.mongodb.morphia.annotations.Embedded;
import org.mongodb.morphia.annotations.Entity;
import org.osgl.util.E;
import org.osgl.util.S;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * A class to store application user data.
 *
 * @author Dmitry Noranovich <javaeeeee at gmail dot com>
 */
@Entity("users")
public class User extends MorphiaModel<User> {

    public static final String LIST_VIEW = "username, bookmarkNum as bookmarks";

    /**
     * A username to login to the application.
     */
    private String username;

    /**
     * A password to login to the application.
     */
    private String password;

    /**
     * Bookmark list of a user.
     */
    @Embedded
    private Map<Long, Bookmark> bookmarks = new HashMap<>();

    /**
     * A no-argument constructor.
     */
    private User() {
    }

    /**
     * A constructor used to create users.
     *
     * @param username
     * @param password
     */
    public User(String username, String password) {
        E.illegalArgumentIf(S.blank(username));
        E.illegalArgumentIf(S.blank(password));
        this.username = username;
        this.password = Act.crypto().passwordHash(password);
    }
    
    public String getUsername() {
        return username;
    }
    
    public void setUsername(String username) {
        E.illegalArgumentIf(S.blank(username));
        this.username = username;
    }
    
    public String getPassword() {
        return password;
    }
    
    public void setPassword(String password) {
        this.password = Act.crypto().passwordHash(password);
    }
    
    public Collection<Bookmark> getBookmarks() {
        return bookmarks.values();
    }

    public int getBookmarkNum() {
        return bookmarks.size();
    }

    public Bookmark getBookmark(Long bookmarkId) {
        return bookmarks.get(bookmarkId);
    }

    public boolean hasBookmark(Long bookmarkId) {
        return bookmarks.containsKey(bookmarkId);
    }

    public Bookmark deleteBookmark(Long bookmarkId) {
        return bookmarks.remove(bookmarkId);
    }

    /**
     * A method to add bookmarks to user's list.
     *
     * @param bookmark a bookmark to add.
     * @return the added bookmark.
     */
    public Bookmark addBookmark(Bookmark bookmark) {
        bookmarks.put(bookmark.ensureId(), bookmark);
        return bookmark;
    }

    public Bookmark updateBookmark(Long id, Bookmark bookmark) {
        bookmark.setId(id);
        bookmarks.put(id, bookmark);
        return bookmark;
    }

    public static class Dao extends MorphiaDao<User> {
        public Dao() {
            super(User.class);
        }

        public User authenticate(String username, String password) {
            return findOneBy("username, password", username, Act.crypto().passwordHash(password));
        }
    }

}
