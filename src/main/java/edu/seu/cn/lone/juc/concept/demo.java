package edu.seu.cn.lone.juc.concept;

import java.math.BigDecimal;

public class demo {
    public static void main(String[] args) {
        BigDecimal a = new BigDecimal("10855770022310844201072");
        a = a.divide(new BigDecimal("1000000000000000000"));
        System.out.println(a.toPlainString());


    }
}
