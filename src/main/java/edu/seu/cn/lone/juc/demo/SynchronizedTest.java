package edu.seu.cn.lone.juc.demo;

import java.util.HashMap;
import java.util.Hashtable;

public class SynchronizedTest {

    private String content;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public static void main(String[] args) {
        SynchronizedTest synchronizedTest = new SynchronizedTest();

        for (int i = 0; i < 5; i++) {
            Thread thread = new Thread(() -> {
                synchronized (synchronizedTest) {
                    synchronizedTest.setContent(Thread.currentThread().getName() + "的数据");
                    System.out.println("---------------------");
                    System.out.println(Thread.currentThread().getName()+ "------->" + synchronizedTest.getContent());
                }

            });

            thread.setName("线程" + i);
            thread.start();
        }
    }
}
