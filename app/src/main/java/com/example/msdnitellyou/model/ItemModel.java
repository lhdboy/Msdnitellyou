package com.example.msdnitellyou.model;

/**
 * Created by Administrator on 2017/4/24.
 */

public class ItemModel {
    private String id;
    private String Name;

    public ItemModel(String id,String name){
        this.id=id;
        this.Name = name;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getId() {

        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
