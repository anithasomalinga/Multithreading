package org.example.multithreading.concurrentCollection;

import java.util.concurrent.Exchanger;

// Exchange Demo - exchange data between two objects
public class ExchangerDemo {
    public static void main(String[] args) {
        Exchanger<Integer> exchanger = new Exchanger<>();
        Thread one = new Thread(new ThreadOne(exchanger));
        Thread two = new Thread(new ThreadTwo(exchanger));
        one.start();
        two.start();
    }
}

class ThreadOne implements Runnable {

    private final Exchanger<Integer> exchanger;

    public ThreadOne(Exchanger<Integer> exchanger) {
        this.exchanger = exchanger;
    }

    @Override
    public void run() {
        int dataToSend = 10;
        System.out.println("First thread is sending data " + dataToSend);
        try {
            int receivedData = exchanger.exchange(dataToSend);
            System.out.println("First thread received " + receivedData);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}

class ThreadTwo implements Runnable {

    private final Exchanger<Integer> exchanger;

    public ThreadTwo(Exchanger<Integer> exchanger) {
        this.exchanger = exchanger;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(3000);
            int dataToSend = 20;
            System.out.println("Second thread is sending data " + dataToSend);
            int receivedData = exchanger.exchange(dataToSend);
            System.out.println("Second thread received " + receivedData);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}