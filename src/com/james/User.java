package com.james;

import java.util.ArrayList;

/**
 * Created by jamesyburr on 6/6/16.
 */
public class User {
    String name;
    String password;
    ArrayList<Message> messageList;

    public User(String name, String password) {
        this.name = name;
        this.password = password;
        messageList = new ArrayList();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public ArrayList<Message> getMessageList() {
        return messageList;
    }

    public void setMessageList(ArrayList<Message> messageList) {
        this.messageList = messageList;
    }
}

