package com.yanko20.blocspot.model;

import com.yanko20.blocspot.ui.UIUtils;

import io.realm.RealmModel;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.RealmClass;

/**
 * Created by ykomizor on 3/22/2017.
 */


@RealmClass
public class Category implements RealmModel{

    @PrimaryKey
    private String name;
    private int color;

    public Category(){}

    public Category(String name){
        setName(name);
        setColor(UIUtils.generateRandomColor());
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }
}
