package org.example.multithreading.executorService;

import java.util.concurrent.*;

public class CallableFuturesDemo {
    public static void main(String[] args) throws ExecutionException, InterruptedException, TimeoutException {
        Future<Integer> result;
        try (ExecutorService service = Executors.newFixedThreadPool(2)) {
            result = service.submit(new ReturnTaskValue());
//            result.cancel(false);
//            System.out.println(result.isCancelled());
//            System.out.println(result.isDone());
            System.out.println(result.get(6, TimeUnit.SECONDS));
            System.out.println("Main thread execution completed!");
        }
    }
}

class ReturnTaskValue implements Callable<Integer> {
    @Override
    public Integer call() throws Exception {
        Thread.sleep(5000);
        return 3;
    }
}
