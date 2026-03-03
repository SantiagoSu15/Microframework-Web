package org.example.rest;


import java.io.IOException;
import java.net.URISyntaxException;

import static org.example.rest.HttpServer.get;
import static org.example.rest.HttpServer.staticfiles;

public class MathService {


    public static void main(String[] args) throws IOException, URISyntaxException {
        staticfiles("/webroot/public");

        get("/pi", (req, res) -> req.getPath().substring(1) + " " + Math.PI);
        get("/eulers", (req, res) -> "Euler: " + Math.E);
        get("/hellow", (req, res) -> req.getPath().substring(1) + "Hola mundo");
        get("/saludo", (req, res) -> req.getPath().substring(1) + " " + req.getQueryParams("name"));
        HttpServer.main(args);
    }
}
