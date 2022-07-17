package org.geekbang.time.commonmistakes.lock.lockscope;

import lombok.Getter;

import java.util.stream.IntStream;

class Data {

    public static void main(String[] args) {
//        IntStream.rangeClosed(1, 10000).parallel().forEach(i -> { new Data().wrong();});
//        IntStream.rangeClosed(1, 10000).parallel().forEach(i -> { wrong();});
        IntStream.rangeClosed(1, 10000).parallel().forEach(i -> { new Data().right(); });
        System.out.println(counter);
    }

    @Getter
    private static int counter = 0;
    private static Object locker = new Object();

    public static int reset() {
        counter = 0;
        return counter;
    }

    public static synchronized void wrong() {
        counter++;
    }

    public void right() {
        synchronized (locker) {
            counter++;
        }
    }
}