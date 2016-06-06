package com.james;

import spark.ModelAndView;
import spark.Spark;
import spark.template.mustache.MustacheTemplateEngine;

import java.util.HashMap;

public class Main {

    static User user;
    static HashMap savedUser = new HashMap();

    public static void main(String[] args) {

        Spark.init();

        Spark.get (
                "/",
                ((request, response) -> {
                    HashMap map = new HashMap();
                    if(user == null) {
                        return new ModelAndView(map, "login.html");
                    } else {
                        map.put("name", user.name);
                        map.put("password", user.password);
                        return new ModelAndView(map, "index.html");
                    }
                }),
                new MustacheTemplateEngine()
        );

        Spark.post(
                "/login",
                ((request, response) -> {
                    String name = request.queryParams("name");
                    String password = request.queryParams("password");
                    user = new User(name, password);
                    response.redirect("/");
                    return "";
                })
        );

        Spark.post(
                "/message",
                ((request, response) -> {

                }))
        )
    }
}
