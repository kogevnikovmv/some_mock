package org.lanit.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AddRequestJSON {

    @JsonProperty("info")
    private Info info;
    @JsonProperty("add")
    private Add add;
    @JsonProperty("uuid")
    private String uuid;
    @JsonProperty("lastUpdate")
    private String lastUpdate;


    public void setAdd(Add add){
        this.add = add;
    }

    public Add getAdd(){
        return add;
    }

    public void setLastUpdate(String lastUpdate){
        this.lastUpdate = lastUpdate;
    }

    public String getLastUpdate(){
        return lastUpdate;
    }

    public void setUuid(String uuid){
        this.uuid = uuid;
    }

    public String getUuid(){
        return uuid;
    }

    public void setInfo(Info info){
        this.info = info;
    }

    public Info getInfo(){
        return info;
    }
}