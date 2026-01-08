package org.example.multithreading.locks;

import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLockDemo {
    private final ReentrantLock lock = new ReentrantLock();
    private int sharedData = 0;

    private void methodA() {
        try {
            lock.lock();
            sharedData++;
            System.out.println("Method A: sharedData = " + sharedData);
            methodB();
        } finally {
            lock.unlock();
        }
    }

    private void methodB() {
        try {
            lock.lock();
            sharedData--;
            System.out.println("Method B: sharedData = " + sharedData);
        } finally {
            lock.unlock();
        }
    }

    public static void main(String[] args) {
        ReentrantLockDemo demo = new ReentrantLockDemo();
        // create and start multiple threads
        for (int i = 0; i < 5; i++) {
            new Thread(demo::methodA).start();
        }
    }
}
