package edu.seu.cn.lone.juc.n8;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class TestCountDownLatchController {

    @GetMapping("/order/{id}")
    public Map<String, Object> order(@PathVariable int id) throws InterruptedException {
        HashMap<String, Object> map = new HashMap<>();
        map.put("id", id);
        map.put("total", "2300.00");
        Thread.sleep(2000);
        return map;
    }

    @GetMapping("/product/{id}")
    public Map<String, Object> product(@PathVariable int id) throws InterruptedException {
        HashMap<String, Object> map = new HashMap<>();
        if (id == 1) {
            map.put("name", "小爱音响");
            map.put("price", 300);
        } else if (id == 2) {
            map.put("name", "小米手机");
            map.put("price", 2000);
        }
        map.put("id", id);
        Thread.sleep(1000);
        return map;
    }

    public Map<String, Object> logistics(@PathVariable int id) throws InterruptedException {
        HashMap<String, Object> map = new HashMap<>();
        map.put("id", id);
        map.put("name", "申通快递");
        Thread.sleep(3000);
        return map;
    }
}
