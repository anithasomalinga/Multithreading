package org.example.multithreading.otherConcepts;

import java.util.concurrent.atomic.AtomicInteger;

public class AtomicVariable {
    private static int count = 0;
    private static final AtomicInteger counter = new AtomicInteger(0);
    private static final AtomicInteger counter1 = new AtomicInteger(0);

    public static void main(String[] args) {
        Thread one = new Thread(() -> {
            for (int i = 0; i < 10000; i++) {
                count++;
                counter.incrementAndGet();
                counter1.getAndIncrement();
            }
        });
        Thread two = new Thread(() -> {
            for (int i = 0; i < 10000; i++) {
                count++;
                counter.incrementAndGet();
                counter1.getAndIncrement();
            }
        });
        one.start();
        two.start();
        try {
            one.join();
            two.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Count : " + count);
        System.out.println("Counter : " + counter);
        System.out.println("Counter1 : " + counter1);
    }
}
