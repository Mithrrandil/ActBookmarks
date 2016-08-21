package com.greenlaw110.bookmark.endpoint;

import act.controller.Controller;
import org.osgl.aaa.NoAuthentication;
import org.osgl.mvc.annotation.GetAction;

@Controller("/hello")
@NoAuthentication
@SuppressWarnings("unused")
public class HelloService {
    /**
     * A simple greeting.
     */
    public static final String GREETING = "Hello Act Framework";

    /**
     * The resource method returns a greeting.
     *
     * @return a greeting.
     */
    @GetAction
    public String getGreeting() {
        return GREETING;
    }

    /**
     * The resource method with a path parameter or query parameter
     * to return customized greeting.
     *
     * @param name The name of a person to greet.
     * @return Customized greeting.
     */
    @GetAction("to/{name}")
    public String getPathParamOrQueryParamGreeting(String name) {
        return "Hello " + name;
    }

}
