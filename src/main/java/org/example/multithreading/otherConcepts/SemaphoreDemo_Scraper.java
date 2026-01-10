package org.example.multithreading.otherConcepts;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

public class SemaphoreDemo_Scraper {
    public static void main(String[] args) {
        try (ExecutorService executorService = Executors.newCachedThreadPool()) {
            for (int i = 0; i < 15; i++) {
                executorService.execute(ScrapeService.INSTANCE::scrape);
            }
        }
    }
}

enum ScrapeService {
    INSTANCE;

    private Semaphore semaphore = new Semaphore(3);

    public void scrape() {
        try {
            semaphore.acquire();
            invokeScrapeBot();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            semaphore.release();
        }
    }

    private void invokeScrapeBot() {
        try {
            System.out.println("Scraping data...");
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
