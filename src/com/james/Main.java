package com.james;

import spark.ModelAndView;
import spark.Spark;
import spark.template.mustache.MustacheTemplateEngine;

import java.util.HashMap;

public class Main {

    static User user;
    static HashMap<String, User> savedUser = new HashMap<>();

    public static void main(String[] args) {

        Spark.init();

        Spark.get (
                "/",
                ((request, response) -> {
                    HashMap map = new HashMap();
                    if(user == null) {
                        return new ModelAndView(map, "login.html");
                    } else {
                        map.put("messages", user.messageList);
                        map.put("name", user.name);
                        return new ModelAndView(map, "index.html");
                    }
                }),
                new MustacheTemplateEngine()
        );

        Spark.post(
                "/login",
                ((request, response) -> {
                    String name = request.queryParams("username");
                    String password = request.queryParams("password");
                    user = savedUser.get(name);
                    if (user == null) {
                        user = new User(name, password);
                        savedUser.put(name, user);
                    }
                    if (!password.equals(user.password)) {
                        user = null;
                    }
                        response.redirect("/");
                        return "";
                })
        );

        Spark.post(
                "/message",
                ((request, response) -> {
                    String message = request.queryParams("message");
                    user.messageList.add(new Message(message));
                    response.redirect("/");
                    return "";
                })
        );

        Spark.post(
                "/logout",
                (request, response) -> {
                    user = null;
                    response.redirect("/");
                    return "";
                }
        );
    }
}
