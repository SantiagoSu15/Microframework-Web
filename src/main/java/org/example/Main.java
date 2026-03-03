package org.example;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.*;
import java.util.List;
import java.util.Map;

public class Main {


    public static void main(String[] args) throws MalformedURLException {
        URL google = new URL("http://is.escuelaing.edu.co:7654/respuestas/respuesta.txt?val=7&t=3#pubs");

        System.out.println("Path: "+ google.getPath());
        System.out.println("Host: "+ google.getHost());
        System.out.println("Port: "+ google.getPort());
        System.out.println("Protocolo " + google.getProtocol());
        System.out.println("Auth: "+google.getAuthority());
        System.out.println("File: "+google.getFile());
        System.out.println("Query: "+google.getQuery());
        System.out.println("Ref: "+ google.getRef());
    }
}