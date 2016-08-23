package com.greenlaw110.bookmark.endpoint;

import act.controller.Controller;
import org.osgl.aaa.NoAuthentication;
import org.osgl.mvc.annotation.GetAction;
import org.osgl.util.S;

@Controller("/hello")
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
     * @param name The name of a person to greet.
     * @return Customized greeting.
     */
    @GetAction({"{name}", ""})
    public String getPathParamOrQueryParamGreeting(String name) {
        return S.blank(name) ? GREETING : "Hello " + name;
    }

}
