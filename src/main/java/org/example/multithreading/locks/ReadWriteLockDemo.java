package org.example.multithreading.locks;

import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ReadWriteLockDemo {
    public static void main(String[] args) {
        SharedResource resource = new SharedResource();
        // 2 Reader Threads
        for (int i = 0; i < 2; i++) {
            Thread readerThread = new Thread(() -> {
                for(int j = 0; j < 3; j++) {
                    resource.getValue();
                }
            });
            readerThread.setName("ReaderThread " + (i+1));
            readerThread.start();
        }

        // Writer Thread
        Thread writerThread = new Thread(() -> {
            for(int j = 0; j < 5; j++) {
                resource.increment();
            }
        });
        writerThread.setName("WriterThread");
        writerThread.start();
    }
}

class SharedResource {
    private int counter = 0;
    private final ReadWriteLock lock = new ReentrantReadWriteLock();

    public void increment() {
        try {
            lock.writeLock().lock();
            counter++;
            System.out.println(Thread.currentThread().getName() + " writes " + counter);
        } finally {
            lock.writeLock().unlock();
        }
    }
    public void getValue() {
        try {
            lock.readLock().lock();
            System.out.println(Thread.currentThread().getName() + " reads " + counter);
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        } finally {
            lock.readLock().unlock();
        }
    }
}
