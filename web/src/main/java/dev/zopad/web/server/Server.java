package dev.zopad.web.server;

import dev.zopad.durak.state.Color;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Server {
    Color color;

    public static void main(String[] args) {
        SpringApplication.run(Server.class, args);
    }



}
