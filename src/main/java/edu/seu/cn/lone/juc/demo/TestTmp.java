package edu.seu.cn.lone.juc.demo;

import java.util.HashMap;

public class TestTmp {
    public static void main(String[] args) {
        HashMap<String, String> map = new HashMap<>(4);
        map.put("string1", "林俊杰");
        map.put("string2", "周杰");
        map.put("string2", "周杰伦");
        map.put("string3", "张敬轩");
        map.put("string4", "陈奕迅");

        System.out.println(map.get("string2"));

        map.forEach((key, value) -> {
            System.out.println(key + "------>" + value);
        });
    }
}
