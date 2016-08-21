package com.greenlaw110.bookmark.model;

import act.Act;
import act.data.Data;
import act.db.util.SequenceNumberGenerator;
import org.osgl.util.S;

@Data
public class Bookmark {

    public static final String LIST_VIEW = "id, url";
    public static final String DETAIL_VIEW = LIST_VIEW + ", description";

    private Long id;

    /**
     * The URL of a bookmark.
     */
    private String url;
    /**
     * The description of a bookmark.
     */
    private String description;

    private Bookmark() {}

    public Bookmark(String url, String description) {
        this.url = url;
        this.description = description;
        this.id = genId();
    }

    public Long ensureId() {
        if (null == id) {
            id = genId();
        }
        return id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    private static Long genId() {
        return SequenceNumberGenerator.next("bookmark");
    }
}
