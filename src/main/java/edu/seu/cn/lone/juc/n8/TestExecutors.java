package edu.seu.cn.lone.juc.n8;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author leon
 * @date 2024/2/11 15:56
 */
@Slf4j(topic = "c.TestExecutors")
public class TestExecutors {
    public static void main(String[] args) {
        ExecutorService pool = Executors.newSingleThreadExecutor();
        Executors.newScheduledThreadPool(1);
        pool.execute(() -> {
            log.debug("1");
            int i = 1 / 0;
        });

        pool.execute(() -> {
            log.debug("2");
        });

        pool.execute(() -> {
            log.debug("3");
        });
    }


}
