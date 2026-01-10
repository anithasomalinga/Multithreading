package org.example.multithreading.forkjoin;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

// Recursive Action Demo!!
public class WorkLoadSplitter extends RecursiveAction {
    long workload;

    public WorkLoadSplitter(long workload) {
        this.workload = workload;
    }

    @Override
    protected void compute() {
        if (workload > 16) {
            System.out.println("Work load too big!! Thus splitting : " + workload);
            long firstLoad = workload/2;
            long secondLoad = workload - firstLoad;

            WorkLoadSplitter wl1 = new WorkLoadSplitter(firstLoad);
            WorkLoadSplitter wl2 = new WorkLoadSplitter(secondLoad);

            wl1.fork();
            wl2.fork();
        } else {
            System.out.println("Work load within limits!! Task being executed for workload: " + workload);
        }
    }
}

class WorkLoadDemo {
    public static void main(String[] args) {
        try (ForkJoinPool pool = new ForkJoinPool(Runtime.getRuntime().availableProcessors())) {
            WorkLoadSplitter splitter = new WorkLoadSplitter(128);
            pool.invoke(splitter);
        }
    }
}
