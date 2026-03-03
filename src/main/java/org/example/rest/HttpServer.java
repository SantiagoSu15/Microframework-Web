package org.example.rest;

import java.io.*;
import java.net.*;
import java.util.HashMap;
import java.util.Map;

public class HttpServer {
    private static Map<String, webMethod> endPoints = new HashMap<>();
    private static StaticFileConfi staticConfig = new StaticFileConfi();


    public static void get(String path,webMethod wm){
        endPoints.put(path,wm);
    }

    public static void main(String[] args) throws IOException, URISyntaxException {
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(35000);
        } catch (IOException e) {
            System.err.println("Could not listen on port: 35000.");
            System.exit(1); }
        Socket clientSocket = null;
        Boolean running = true;
        while (running){


        try {
            System.out.println("Listo para recibir ...");
            clientSocket = serverSocket.accept();
        } catch (IOException e) {
            System.err.println("Accept failed.");
            System.exit(1);
        }

        PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
        BufferedReader in = new BufferedReader(
                new InputStreamReader(
                        clientSocket.getInputStream()));
        String inputLine, outputLine;

        String pathInfo = "";
        Boolean firstLine = true;
        HttpRequest req = new HttpRequest();
        while ((inputLine = in.readLine()) != null) {
            System.out.println("Received: " + inputLine);
            if(firstLine){

                String[] realTokes = inputLine.split(" ");
                String method = realTokes[0];
                String path = realTokes[1];
                String version = realTokes[2];
                URI requestUri = new URI(path);

                pathInfo = requestUri.getPath();
                System.out.println("PathInfo: " + pathInfo);
                firstLine = false;

                String query = requestUri.getQuery();
                System.out.println("Query: " + query);
                req.setMethod(method);
                req.setPath(pathInfo);
                if (query != null) {
                    String[] parametros = query.split("\\?");
                    String[] claves = parametros[0].split("&");
                    for(String clave: claves){
                        String[] sub = clave.split("=");
                        req.setQueryParams(sub[0],sub[1]);
                    }
                }



            }
            if (!in.ready()) {
                break;
            }
        }

        HttpResponse res = new HttpResponse();
        if(endPoints.containsKey(pathInfo)){
            outputLine =
                    "HTTP/1.1 200 OK\n"
                            +"content-Type: text/html\n\r"
                            +"\n\r"
                            +"<!DOCTYPE html>"
                            + "<html>"
                            + "<head>"
                            + "<meta charset=\"UTF-8\">"
                            + "<title>Title of the document</title>\n"
                            + "</head>"
                            + "<body>"
                            + "<h1>" + endPoints.get(pathInfo).execute(req,res) + "</h1>"
                            + "</body>"
                            + "</html>" + inputLine;out.println(outputLine);
        }else{
            URL fUrl = getStaticFile(pathInfo.substring(1));
            System.out.println("File: " + fUrl);
            System.out.println("Path: " + pathInfo);
            if(fUrl != null){
                String cType = "text/html";
                if (pathInfo.endsWith(".css"))  cType = "text/css";
                if (pathInfo.endsWith(".png"))  cType = "image/png";

                byte[] fileBytes = fUrl.openStream().readAllBytes();
                OutputStream rawOut = clientSocket.getOutputStream();
                String headers = "HTTP/1.1 200 OK\r\n"
                        + "Content-Type: " + cType + "\r\n"
                        + "Content-Length: " + fileBytes.length + "\r\n"
                        + "\r\n";
                rawOut.write(headers.getBytes());
                rawOut.write(fileBytes);
                rawOut.flush();

            } else {
                out.println("HTTP/1.1 404 Not Found\r\n\r\n<h1>404 - Not Found</h1>");
            }

        }



        out.close();
        in.close();
        clientSocket.close();
        }
        serverSocket.close();
    }


    public static void staticfiles(String path){
        staticConfig.path(path);
    }

    public static URL getStaticFile(String path){
        String base = staticConfig.getLocation();
        System.out.println("Base: " + base);
        if (base == null) return null;

        String fullPath = base + "/" + path;
        System.out.println("Path: " + fullPath);
        return HttpServer.class.getClassLoader().getResource(fullPath);
    }

}