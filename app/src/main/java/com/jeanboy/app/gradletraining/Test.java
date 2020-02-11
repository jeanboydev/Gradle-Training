package com.jeanboy.app.gradletraining;


import android.util.Log;

import com.jeanboy.plugin.annotation.TimeHelper;

/**
 * Created by jeanboy on 2020/02/07 10:55.
 */
public class Test {

    public static void main(String[] args) {
        Log.e("jeanboy", "-------onCreate()-------");
    }

    public void testTime() {
        Log.e("jeanboy", "-------method() start-----------");
        TimeHelper.markStart("methodName", System.nanoTime());
        TimeHelper.markEnd("methodName", System.nanoTime());
        Log.e("jeanboy", TimeHelper.getCostTime("methodName"));
        Log.e("jeanboy", "-------method() end-----------");
    }
}
