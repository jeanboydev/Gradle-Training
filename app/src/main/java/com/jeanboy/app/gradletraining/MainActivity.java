package com.jeanboy.app.gradletraining;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.jeanboy.plugin.annotation.TimeMark;


public class MainActivity extends AppCompatActivity {

    @TimeMark
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        test();
    }

    @TimeMark
    private void test() {
        System.out.println("======test========");
    }


}
