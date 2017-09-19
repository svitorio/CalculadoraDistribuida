package com.example.desenvolverdor.calculator;

/**
 * Created by desenvolverdor on 15/09/17.
 */

public class Server {

    String name, host;

    public Server(String name, String host) {
        this.name = name;
        this.host = host;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }
}
