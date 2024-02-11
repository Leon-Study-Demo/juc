package edu.seu.cn.lone.juc.n7;

import lombok.extern.slf4j.Slf4j;

import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;

@Slf4j(topic = "c.Test1")
public class Test1 {
    public static void main(String[] args) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        for (int i = 0; i < 10; i++) {
            new Thread(() ->  {
                    try {
                        TemporalAccessor parse = dateTimeFormatter.parse("1951-04-21");
                        log.debug("{}", parse);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

            }).start();
        }
    }
}
