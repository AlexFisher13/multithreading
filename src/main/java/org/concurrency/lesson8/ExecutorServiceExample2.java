package org.concurrency.lesson8;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * В этом примере мы рассмотрим метод shutdownNow,
 * который немедленно завершает работу пула потоков и возвращает лист невыполенных задач.
 */
public class ExecutorServiceExample2 {
    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(5);

        for (int i = 0; i < 100; i++) {
            int taskNumber = i;
            executorService.submit(() -> {
                try {
                    TimeUnit.MILLISECONDS.sleep(10);
                } catch (InterruptedException e) {
                    System.out.println("Task is interrupted");
                }
                System.out.println(Thread.currentThread().getName() + " : Task " + taskNumber);
            });
        }

        List<Runnable> runnables = executorService.shutdownNow();
        System.out.println("Count of not finished tasks: " + runnables.size());

        ExecutorService executorService2 = Executors.newFixedThreadPool(5);
        for (Runnable task: runnables) {
            executorService2.submit(task);
        }

        executorService2.shutdown();
    }
}
