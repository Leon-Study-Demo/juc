package edu.seu.cn.lone.juc.n4;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author leon
 * @date 2024/1/8 22:29
 */
public class TestThreadSafe {
    static final int THREAD_NUMBER = 2;
    static final int LOOP_NUMBER = 2000000;

    public static void main(String[] args) {
        ThreadUnsafe threadUnsafe = new ThreadUnsafe();
            for (int i = 0; i < THREAD_NUMBER; i++) {
                new Thread(() -> threadUnsafe.method1(LOOP_NUMBER), "Thread" + (i + 1)).start();
        }
    }
}


class ThreadUnsafe {
    ArrayList<String> list = new ArrayList<>();

    public void method1(int loopNumber) {
        for (int i = 0; i < loopNumber; i++) {
            method2();
            method3();
        }
    }

    private void method2() {
        list.add("1");
    }

    private void method3() {
        list.remove(0);
    }
}
