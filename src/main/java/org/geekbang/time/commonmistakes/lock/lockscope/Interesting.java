package org.geekbang.time.commonmistakes.lock.lockscope;

import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;

import java.util.concurrent.Executors;
import java.util.stream.IntStream;

@Slf4j
public class Interesting {

    static volatile int a = 1;
    static volatile int b = 1;

    public static void main(String[] args) {
        Executors.newFixedThreadPool(1).submit(() -> add());
        Executors.newFixedThreadPool(1).submit(() -> compareRight());
    }

    public static synchronized void add() {
        log.info("add start");
        for (int i = 0; i < 100000; i++) {
            a++;
            b++;
        }
        log.info("add done");
    }

    public static void compare() {
        log.info("compare start");
        for (int i = 0; i < 100000; i++) {
            if (a < b) {
                log.info("a:{},b:{},{}", a, b, a > b);
                //最后的a>b应该始终是false的吗？
            }
        }
        log.info("compare done");
    }

    public static synchronized void compareRight() {
        log.info("compare start");
        for (int i = 0; i < 1000000; i++) {
            Assert.assertTrue(a == b);
            if (a < b) {
                log.info("a:{},b:{},{}", a, b, a > b);
            }
        }
        log.info("compare done");
    }
}
