package org.concurrency.core.lesson7;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * Еще один пример, с Callable, Future и ExecutorService
 * Мы создаем пул на 3 потока, добавляем туда 3 одинаковых реализации Callable
 * и получаем результаты выполнения через Future
 */
public class FutureSample1 {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(3);

        Callable<String> callable = () -> {
            String threadName = Thread.currentThread().getName();
            System.out.printf("Поток %s начал работу\n", threadName);
            Thread.sleep(1000); // секунду выполняет работу
            return threadName;
        };

        List<Future<String>> futures = new ArrayList<>(); // результаты работы потоков

        for (int i = 0; i < 3; i++) {
            Future<String> result = executorService.submit(callable); // запускаем поток в executorService
            futures.add(result); // сохраняем результат выполнения
        }

        for (Future<String> future: futures) {
            System.out.printf("Результат потока %s\n", future.get());
        }

        executorService.shutdown();
    }
}
