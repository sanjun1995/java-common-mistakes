package org.geekbang.time.commonmistakes.lock.lockgranularity;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

@Slf4j
public class LockGranularityController {

    private static List<Integer> data = new ArrayList<>();

    private static void slow() {
        try {
            TimeUnit.MILLISECONDS.sleep(10);
        } catch (InterruptedException e) {
        }
    }

    public static int wrong() {
        long begin = System.currentTimeMillis();
        IntStream.rangeClosed(1, 1000).parallel().forEach(i -> {
            synchronized (data) {
                slow();
                data.add(i);
            }
        });
        log.info("wrong took:{}", System.currentTimeMillis() - begin);
        return data.size();
    }

    public static int right() {
        long begin = System.currentTimeMillis();
        IntStream.rangeClosed(1, 1000).parallel().forEach(i -> {
            slow();
            synchronized (data) {
                data.add(i);
            }
        });
        log.info("right took:{}", System.currentTimeMillis() - begin);
        return data.size();
    }

    public static void main(String[] args) {
        wrong();
        right();
    }
}
