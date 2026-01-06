package org.example.multithreading.executorService;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class FixedThreadPoolDemo {
    public static void main(String[] args) {
        try (ExecutorService executorService = Executors.newFixedThreadPool(2)) {
            for (int i = 0; i < 7; i++) {
                executorService.execute(new Task(i));
            }
        }
    }
}

class Work implements Runnable {
    private final int workId;
    public Work(int workId) {
        this.workId = workId;
    }
    @Override
    public void run() {
        System.out.println("Task id " + workId + " is executed by Thread " + Thread.currentThread().getName());
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
