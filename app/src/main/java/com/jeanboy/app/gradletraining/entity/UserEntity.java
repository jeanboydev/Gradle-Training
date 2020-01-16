package com.jeanboy.app.gradletraining.entity;

import com.jeanboy.module.annotation.Field;
import com.jeanboy.module.annotation.From;

/**
 * @author caojianbo
 * @since 2020/1/16 17:14
 */
@From("user")
public class UserEntity {

    @Field(name = "haha")
    private String userName;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
