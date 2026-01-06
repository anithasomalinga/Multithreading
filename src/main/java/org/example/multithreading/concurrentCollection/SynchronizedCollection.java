package org.example.multithreading.concurrentCollection;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

// Collections.synchronizedList
public class SynchronizedCollection {
    public static void main(String[] args) throws InterruptedException {
        // List<Integer> list = new ArrayList<>(); // not thread-safe. list size at the end is < 2000
        List<Integer> list = Collections.synchronizedList(new ArrayList<>()); // this time list size at the end is 2000

        Thread one = new Thread(() -> {
            for (int i = 0; i < 1000; i++) {
                list.add(i);
            }
        });

        Thread two = new Thread(() -> {
            for (int i = 0; i < 1000; i++) {
                list.add(i);
            }
        });

        one.start();
        two.start();

        one.join();
        two.join();

        System.out.println(list.size());
    }
}
