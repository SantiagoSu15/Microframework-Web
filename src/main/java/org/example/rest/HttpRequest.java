package org.example.rest;

import java.util.*;

public class HttpRequest {
    private String path;
    private String method;
    private Map<String, String >queryParams;

    public HttpRequest(){
        this.path = "";
        this.method = "";
        this.queryParams = new HashMap<>(){};

    }

    public String getPath(){
        return path;
    }
    public String getMethod(){
        return method;
    }
    public String getQueryParams(String key){
        return this.queryParams.get(key);
    }
    public void setPath(String path){
        this.path = path;
    }
    public void setMethod(String method){
        this.method = method;
    }
    public void setQueryParams(String clave,String valor ){
        this.queryParams.put(clave, valor);
    }
    public String getValue(String key){
        return key;
    }
}
