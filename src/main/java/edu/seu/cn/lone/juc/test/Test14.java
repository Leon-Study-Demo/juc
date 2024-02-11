package edu.seu.cn.lone.juc.test;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

@Slf4j(topic = "c.test14")
public class Test14 {
    public static void main(String[] args) throws InterruptedException {
        test3();
    }

    private static void test3() throws InterruptedException {
        Thread t1 = new Thread(() -> {
            log.debug("park...");
            LockSupport.park();
            log.debug("unpark...");
            log.debug("打断状态: {}", Thread.currentThread().isInterrupted());

            LockSupport.park();
            log.debug("unpark...");
        }, "t1");

        t1.start();

        TimeUnit.SECONDS.sleep(1);
        t1.interrupt();
    }
}
