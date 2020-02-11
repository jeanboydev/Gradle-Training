package com.jeanboy.plugin.annotation;

import java.util.HashMap;
import java.util.Map;

class TimeHelper {

    static Map<String, Long> startTimeMap = new HashMap<>();
    static Map<String, Long> endTimeMap = new HashMap<>();

    static void markStart(String methodName, long time) {
        startTimeMap.put(methodName, time);
    }

    static void markEnd(String methodName, long time) {
        endTimeMap.put(methodName, time);
    }

    static String getCostTime(String methodName) {
        long start = startTimeMap.get(methodName);
        long end = endTimeMap.get(methodName);
        return "method: " + methodName + ", cost: " + (start - end);
    }
}