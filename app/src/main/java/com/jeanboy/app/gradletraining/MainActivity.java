package com.jeanboy.app.gradletraining;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.jeanboy.app.gradletraining.entity.UserEntity;
import com.jeanboy.app.gradletraining.model.UserModel;
import com.jeanboy.test.UserModelConverter;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        UserModel transform = UserModelConverter.transform(new UserEntity());
    }
}
