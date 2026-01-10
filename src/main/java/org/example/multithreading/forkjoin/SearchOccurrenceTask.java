package org.example.multithreading.forkjoin;

import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

// RecursiveTask Use!!
public class SearchOccurrenceTask extends RecursiveTask<Integer> {

    int[] arr;
    int start;
    int end;
    int searchElement;

    public SearchOccurrenceTask(int[] arr, int start, int end, int searchElement) {
        this.arr = arr;
        this.start = start;
        this.end = end;
        this.searchElement = searchElement;
    }

    @Override
    protected Integer compute() {
//        return search(); // normal case
        int size = (end - start) + 1;
        if (size > 50) {
            int mid = (start + end) / 2;
            System.out.println("Breaking task since size is " + size);
            SearchOccurrenceTask task1 = new SearchOccurrenceTask(arr, start, mid, searchElement);
            SearchOccurrenceTask task2 = new SearchOccurrenceTask(arr, mid + 1, end, searchElement);

            task1.fork();
            task2.fork();

            return task1.join() + task2.join();
        } else {
            return search();
        }
    }

    private Integer search() {
        int count = 0;
        for (int i = start; i < end; i++) {
            if(arr[i] == searchElement) {
                count++;
            }
        }
        return count;
    }

}

class FJPDemo {
    public static void main(String[] args) {
//        int[] arr = new int[100];// normal case
        int[] arr = new int[1000];
        Random random = new Random();
        for (int i = 0; i < arr.length; i++) {
            arr[i] = random.nextInt(10) + 1;
        }
        int searchElement = random.nextInt(10) + 1;
        try (ForkJoinPool pool = new ForkJoinPool(Runtime.getRuntime().availableProcessors())) {
            SearchOccurrenceTask task = new SearchOccurrenceTask(arr, 0, arr.length, searchElement);
            System.out.println("Array size " + arr.length);
            Integer occurrence = pool.invoke(task);
            System.out.println("Array is " + Arrays.toString(arr));
            System.out.printf("%d occurred %d times", searchElement, occurrence);
        }
    }

//        SearchOccurrenceTask task = new SearchOccurrenceTask();
}