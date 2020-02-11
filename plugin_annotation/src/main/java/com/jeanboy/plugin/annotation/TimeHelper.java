package com.jeanboy.plugin.annotation;

import java.util.HashMap;
import java.util.Map;

class TimeHelper {

    private static Map<String, Long> startTimeMap = new HashMap<>();
    private static Map<String, Long> endTimeMap = new HashMap<>();

    public static void markStart(String methodName, long time) {
        startTimeMap.put(methodName, time);
    }

    public static void markEnd(String methodName, long time) {
        endTimeMap.put(methodName, time);
    }

    public static String getCostTime(String methodName) {
        long start = startTimeMap.get(methodName);
        long end = endTimeMap.get(methodName);
        return "method: " + methodName + ", cost: " + (start - end);
    }
}