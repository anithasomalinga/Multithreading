package org.example.multithreading.executorService;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CpuIntensiveTaskDemo {
    public static void main(String[] args) {
        int cores = Runtime.getRuntime().availableProcessors();
        try (ExecutorService service = Executors.newFixedThreadPool(cores)) {
            System.out.println("Created Thread pool with : " + cores + " cores");
            for (int i = 0; i < 20; i++) {
                service.execute(new CpuTask());
            }
        }

    }
}

class CpuTask implements Runnable {
    @Override
    public void run() {
        System.out.println("Running CPU intensive task by Thread: " + Thread.currentThread().getName());
    }

}
