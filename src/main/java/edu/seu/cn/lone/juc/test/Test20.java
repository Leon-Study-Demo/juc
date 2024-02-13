package edu.seu.cn.lone.juc.test;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/**
 * @author leon
 * @date 2024/2/11 21:39
 */
@Slf4j(topic = "c.Test20")
public class Test20 {

    public static void main(String[] args) {
        // 线程1等待线程2的下载结果
        GuardedObject guardedObject = new GuardedObject();
        new Thread(() -> System.out.println(guardedObject.get(2000)), "t1").start();


        new Thread(() -> {
            try {
                TimeUnit.SECONDS.sleep(5);
                guardedObject.complete("hello world");
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }, "t2").start();
    }


}

class GuardedObject {
    // 结果
    private Object response;

    public synchronized Object get(long timeout) {
        long begin = System.currentTimeMillis();
        long passed = 0;
        while (response == null) {
            // 当前循环应该等待的时间
            long waitTime = timeout - passed;

            if (waitTime <= 0) {
                break;
            }
            try {
                wait(waitTime);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            passed = System.currentTimeMillis() - begin;
        }
        return response;
    }


    public synchronized void complete(Object response) {
        this.response = response;
        notifyAll();
    }
}
