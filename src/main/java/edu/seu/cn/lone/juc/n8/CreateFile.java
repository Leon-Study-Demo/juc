package edu.seu.cn.lone.juc.n8;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class CreateFile {
    static final String ALPHA = "abcdefghijklmnopqrstuvwxyz";

    public static void main(String[] args) {
        int length = ALPHA.length();
        int count = 200;
        List<String> list = new ArrayList<>(length * count);

        for (int i = 0; i < length; i++) {
            char ch = ALPHA.charAt(i);
            for (int i1 = 0; i1 < count; i1++) {
                list.add(String.valueOf(ch));
            }
        }

        // 打乱数组
        Collections.shuffle(list);
        for (int i = 0; i < 26; i++) {
            try(PrintWriter out = new PrintWriter(new OutputStreamWriter(new FileOutputStream("tmp/" + (i + 1) + ".txt")))) {
                String collect = String.join("\n", list.subList(i * count, (i + 1) * count));
                out.print(collect);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
