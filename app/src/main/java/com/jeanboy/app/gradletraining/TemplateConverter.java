package com.jeanboy.app.gradletraining;

import com.jeanboy.app.gradletraining.entity.UserEntity;
import com.jeanboy.app.gradletraining.model.UserModel;

import java.util.ArrayList;
import java.util.List;

/**
 * @author caojianbo
 * @since 2020/1/16 17:21
 */
public class TemplateConverter {

    public UserModel transform(UserEntity from) {
        UserModel to = new UserModel();
        to.setName(from.getUserName());
        return to;
    }

    public List<UserModel> transform(List<UserEntity> fromList) {
        if (fromList == null) return null;
        List<UserModel> toList = new ArrayList<>();
        for (UserEntity from : fromList) {
            UserModel to = transform(from);
            toList.add(to);
        }
        return toList;
    }
}
