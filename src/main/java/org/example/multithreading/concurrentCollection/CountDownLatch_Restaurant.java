package org.example.multithreading.concurrentCollection;

import java.util.concurrent.CountDownLatch;

// Countdown Latch example
// Usecase
// Chefs are responsible for a specific dish
// Restaurant Manager is going to serve only when all the dishes are ready
public class CountDownLatch_Restaurant {
    public static void main(String[] args) throws InterruptedException {
        int noOfChefs = 3;
        CountDownLatch latch = new CountDownLatch(noOfChefs);
        new Thread(new Chef("Chef A", "Pizza", latch)).start();
        new Thread(new Chef("Chef B", "Pasta", latch)).start();
        new Thread(new Chef("Chef C", "Salad", latch)).start();
        latch.await();
        System.out.println("All the dishes are ready! Let's start serving customers.");
    }
}

class Chef implements Runnable {

    private final String name;
    private final String dish;
    private final CountDownLatch latch;

    public Chef(String name, String dish, CountDownLatch latch) {
        this.name = name;
        this.dish = dish;
        this.latch = latch;
    }

    @Override
    public void run() {
        try {
            System.out.println(name + " is preparing " + dish);
            Thread.sleep(2000);
            System.out.println(name + " has finished preparing " + dish);
            latch.countDown();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}