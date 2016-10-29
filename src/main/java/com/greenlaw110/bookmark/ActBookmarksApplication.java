package com.greenlaw110.bookmark;

import act.Version;
import act.boot.app.RunApp;

public class ActBookmarksApplication {

	public static void main(String[] args) throws Exception {
		RunApp.start("ActBookmark", Version.appVersion(), ActBookmarksApplication.class);
	}
}
