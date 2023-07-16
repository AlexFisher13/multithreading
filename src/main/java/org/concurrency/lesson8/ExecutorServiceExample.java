package org.concurrency.lesson8;

import java.util.concurrent.*;

/**
 * В этом примере мы видим создание пула и наполнение его 100 задачами.
 * Пробуем создать single, cached и fixed threadPool.
 * Судя по выводу в консоль cachedThreadPool решил создать 100 потоков, под 100 задач.
 */
public class ExecutorServiceExample {
    public static void main(String[] args) {
        ExecutorService executorService = Executors.newCachedThreadPool();
//        ExecutorService executorService = Executors.newFixedThreadPool(5);
//        ExecutorService executorService = Executors.newSingleThreadExecutor();

        for (int i = 0; i < 100; i++) {
            int taskNumber = i;
            executorService.submit(() -> {
                try {
                    TimeUnit.MILLISECONDS.sleep(10);
                } catch (InterruptedException ignored) {}
                System.out.println(Thread.currentThread().getName() + " : Task " + taskNumber);
            });
        }

        executorService.shutdown();

    }
}
