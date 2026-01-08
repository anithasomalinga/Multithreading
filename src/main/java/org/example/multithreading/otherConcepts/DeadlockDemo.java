package org.example.multithreading.otherConcepts;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class DeadlockDemo {

    private final Lock lockOne = new ReentrantLock(true);
    private final Lock lockTwo = new ReentrantLock(true);

    public void workerOne() {
        try {
            lockOne.lock();
            System.out.println("Worker One acquired LockOne");
            Thread.sleep(200);
            lockTwo.lock();
            System.out.println("Worker One acquired LockTwo");
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            lockOne.unlock();
            lockTwo.unlock();
        }
    }
    public void workerTwo() {
        try {
            lockTwo.lock();
            System.out.println("Worker Two acquired LockTwo");
            Thread.sleep(200);
            lockOne.lock();
            System.out.println("Worker Two acquired LockOne");
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            lockTwo.unlock();
            lockOne.unlock();
        }
    }

    public static void main(String[] args) {
        DeadlockDemo demo = new DeadlockDemo();
        new Thread(demo::workerOne, "T1").start();
        new Thread(demo::workerTwo, "T2").start();
    }
}
