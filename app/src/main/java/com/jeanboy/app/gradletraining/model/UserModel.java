package com.jeanboy.app.gradletraining.model;

import com.jeanboy.module.annotation.Product;
import com.jeanboy.module.annotation.Field;

/**
 * @author caojianbo
 * @since 2020/1/16 16:46
 */
@Product("user")
public class UserModel {

    @Field(identity = "haha")
    private String name;

    @Field(identity = "can")
    private boolean isCan;

    public boolean isCan() {
        return isCan;
    }

    public void setCan(boolean can) {
        isCan = can;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
