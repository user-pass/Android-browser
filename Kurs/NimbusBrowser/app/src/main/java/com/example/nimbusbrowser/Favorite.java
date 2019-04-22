package com.example.nimbusbrowser;

public class Favorite {
    private String UrlName;
    private String UrlAddress;

    public Favorite(String uName, String uAddress){
        UrlName = uName;
        UrlAddress = uAddress;
    }

    public String getUrlName(){
        return UrlName;
    }

    public String getUrlAddress(){
        return UrlAddress;
    }
}
