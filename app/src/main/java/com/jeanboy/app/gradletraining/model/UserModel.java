package com.jeanboy.app.gradletraining.model;

import com.jeanboy.module.annotation.To;
import com.jeanboy.module.annotation.Field;

/**
 * @author caojianbo
 * @since 2020/1/16 16:46
 */
@To("user")
public class UserModel {

    @Field(name = "haha")
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
