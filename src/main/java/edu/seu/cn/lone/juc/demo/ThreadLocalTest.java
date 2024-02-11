package edu.seu.cn.lone.juc.demo;

public class ThreadLocalTest {
    ThreadLocal<String> threadLocal = new InheritableThreadLocal<>();

    public String getContent() {
        return threadLocal.get();
    }

    public void setContent(String content) {
        threadLocal.set(content);
    }

    public static void main(String[] args) {
        ThreadLocalTest threadLocalTest = new ThreadLocalTest();

        for (int i = 0; i < 5; i++) {
            Thread thread = new Thread(() -> {
                threadLocalTest.setContent(Thread.currentThread().getName() + "的数据");
                System.out.println("---------------------");
                System.out.println(Thread.currentThread().getName()+ "------->" + threadLocalTest.getContent());
            });

            thread.setName("线程" + i);
            thread.start();
        }
    }
}
