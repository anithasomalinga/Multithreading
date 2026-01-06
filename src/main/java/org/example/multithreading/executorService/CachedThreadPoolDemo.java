package org.example.multithreading.executorService;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CachedThreadPoolDemo {
    public static void main(String[] args) {
        try (ExecutorService executorService = Executors.newCachedThreadPool()) {
            for (int i = 0; i < 100; i++) {
                executorService.execute(new TaskOne(i));
            }
        }
    }
}

class TaskOne implements Runnable {
    private final int taskId;
    public TaskOne(int taskId) {
        this.taskId = taskId;
    }
    @Override
    public void run() {
        System.out.println("Task " + taskId + " is executed by Thread " + Thread.currentThread().getName());
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
