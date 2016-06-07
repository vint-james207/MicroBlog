package com.james;

import spark.ModelAndView;
import spark.Session;
import spark.Spark;
import spark.template.mustache.MustacheTemplateEngine;

import java.util.HashMap;

public class Main {

    static HashMap<String, User> savedUsers = new HashMap<>();

    public static void main(String[] args) {
        Spark.staticFileLocation("Public");

        Spark.init();

        Spark.get (
                "/",
                ((request, response) -> {
                    HashMap map = new HashMap();
                    Session session = request.session();
                    String name = session.attribute("username");
                    User user = savedUsers.get(name);
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
                    User user = savedUsers.get(name);
                    if (user == null) {
                        user = new User(name, password);
                        savedUsers.put(name, user);
                    }
                    if (password.equals(user.password)) {
                        Session session = request.session();
                        session.attribute("username", name);
                    }

                        response.redirect("/");
                        return "";
                })
        );

        Spark.post(
                "/message",
                ((request, response) -> {
                    Session session = request.session();
                    String name = session.attribute("username");
                    User user = savedUsers.get(name);
                    String message = request.queryParams("message");
                    user.messageList.add(new Message(message));
                    response.redirect("/");
                    return "";
                })
        );

        Spark.post(
                "/logout",
                (request, response) -> {
                    Session session = request.session();
                    session.invalidate();
                    response.redirect("/");
                    return "";
                }
        );
    }
}
