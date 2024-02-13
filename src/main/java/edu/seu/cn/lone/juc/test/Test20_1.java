package edu.seu.cn.lone.juc.test;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.Hashtable;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @author leon
 * @date 2024/2/14 00:30
 */
@Slf4j(topic = "c.Test20_1")
public class Test20_1 {
    public static void main(String[] args) throws InterruptedException {
        for (int i = 0; i < 3; i++) {
            new People().start();
        }

        TimeUnit.SECONDS.sleep(1);

        for (Integer id : MailBoxes.getIds()) {
            new Postman(id, "内容" + id).start();
        }
    }
}

@Slf4j(topic = "c.People")
class People extends Thread {
    @Override
    public void run() {
        GuardedObjectV2 guardedObjectV2 = MailBoxes.createGuardedObject();
        log.debug("收信 id: {}", guardedObjectV2.getId());
        Object mail = guardedObjectV2.get(5000);
        log.debug("收到信 id: {}，内容:{}", guardedObjectV2.getId(), mail);
    }
}

@Slf4j(topic = "c.Postman")
class Postman extends Thread {
    private int id;

    private String mail;

    public Postman(int id, String mail) {
        this.id = id;
        this.mail = mail;
    }

    @Override
    public void run() {
        GuardedObjectV2 guardedObject = MailBoxes.getGuardedObject(id);
        log.debug("送信 id: {}, 内容{}", id, mail);
        guardedObject.complete(mail);
    }
}

class MailBoxes {
    private static Map<Integer, GuardedObjectV2> boxes = new Hashtable<>();

    private static int id = 1;
    private static synchronized int generateId() {
        return id++;
    }

    // 这里加没加 synchronized 都会有 ConcurrentModificationException
    public static synchronized GuardedObjectV2 createGuardedObject() {
        GuardedObjectV2 guardedObjectV2 = new GuardedObjectV2(generateId());
        boxes.put(guardedObjectV2.getId(), guardedObjectV2);
        return guardedObjectV2;
    }

    public static Set<Integer> getIds() {
        return boxes.keySet();
    }

    public static GuardedObjectV2 getGuardedObject(int id) {
        return boxes.remove(id);
    }
}

class GuardedObjectV2 {
    // 结果
    private Object response;

    @Getter
    private int id;

    public GuardedObjectV2(int id) {
        this.id = id;
    }

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