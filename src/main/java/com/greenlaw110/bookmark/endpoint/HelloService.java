package com.greenlaw110.bookmark.endpoint;

import org.osgl.aaa.NoAuthentication;
import org.osgl.mvc.annotation.GetAction;
import org.osgl.util.S;

@NoAuthentication
@SuppressWarnings("unused")
public class HelloService {
    /**
     * A simple greeting.
     */
    public static final String GREETING = "Hello Act Framework";

    /**
     * The resource method with a path parameter or query parameter
     * to return customized greeting.
     *
     * When `name` is not found then it will return the
     * default {@link #GREETING}
     *
     * @param name The name of a person to greet.
     * @return Customized greeting.
     */
    @GetAction("/hello/{name}")
    public String getPathParamOrQueryParamGreeting(String name) {
        return S.blank(name) ? GREETING : "Hello " + name;
    }

}
