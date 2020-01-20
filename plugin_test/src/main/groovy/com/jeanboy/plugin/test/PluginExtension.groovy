package com.jeanboy.plugin.test

/**
 * 自定义插件参数传递
 */
class PluginExtension {

    String param1
    String param2

    PluginExtension() {
    }

    @Override
    String toString() {
        return "PluginExtension{" +
                "param1='" + param1 + '\'' +
                ", param2='" + param2 + '\'' +
                '}'
    }
}