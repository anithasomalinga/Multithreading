package org.example.multithreading.synchronization;

import java.util.ArrayList;
import java.util.List;

public class ProducerConsumer {
    public static void main(String[] args) {
        Worker worker = new Worker(5, 0);
        Thread producer = new Thread(() -> {
            try {
                worker.produce();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

        Thread consumer = new Thread(() -> {
            try {
                worker.consume();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

        producer.start();
        consumer.start();

    }
}

class Worker {
    private int sequence = 0;
    private final Integer top; // max the container should have
    private final Integer bottom; // minimum the container should have
    private final List<Integer> container; //shared container
    private final Object lock = new Object();

    public Worker(Integer top, Integer bottom) {
        this.top = top;
        this.bottom = bottom;
        this.container = new ArrayList<>();
    }

    public void produce() throws InterruptedException {
        synchronized (lock) {
            while (true) {
                if (container.size() == top) {
                    System.out.println("Container is full, waiting for elements to be removed...");
                    lock.wait();
                } else {
                    System.out.println(sequence + " Added to the container");
                    container.add(sequence++);
                    lock.notify();
                }
                Thread.sleep(500);
            }
        }
    }

    public void consume() throws InterruptedException {
        synchronized (lock) {
            while (true) {
                if (container.size() == bottom) {
                    System.out.println("Container is empty, waiting for elements to be added...");
                    lock.wait();
                } else {
                    System.out.println(container.remove(0) + " removed from the container");
                    lock.notify();
                }
                Thread.sleep(500);
            }
        }
    }
}
