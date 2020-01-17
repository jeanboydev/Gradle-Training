package com.jeanboy.app.gradletraining.entity;

import com.jeanboy.module.annotation.Field;
import com.jeanboy.module.annotation.Source;

/**
 * @author caojianbo
 * @since 2020/1/16 17:14
 */
@Source("user")
public class UserEntity {

    @Field(identity = "haha")
    private String userName;

    @Field(identity = "can")
    private boolean isCanDo;

    public boolean isCanDo() {
        return isCanDo;
    }

    public void setCanDo(boolean canDo) {
        isCanDo = canDo;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
