package edu.seu.cn.lone.juc.test;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.IntUnaryOperator;

public class Test34 {
    public static void main(String[] args) {

        AtomicInteger i = new AtomicInteger(5);
        updateAndSet(i, operand -> operand / 2);

    }
    public static void updateAndSet(AtomicInteger i, IntUnaryOperator operator) {
        while (true) {
            int prev = i.get();
            int next = operator.applyAsInt(prev);
            if (i.compareAndSet(prev, next)) {
                break;
            }
        }
    }
}
