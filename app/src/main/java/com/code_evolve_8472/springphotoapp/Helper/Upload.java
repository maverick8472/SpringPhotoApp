package com.code_evolve_8472.springphotoapp.Helper;

/**
 * Created by Bassline_8472 on 4/3/2017.
 */
public class Upload {

    private String name;
    private String url;

    public Upload(){}
    public Upload(String name, String url) {
        this.name = name;
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }




}
