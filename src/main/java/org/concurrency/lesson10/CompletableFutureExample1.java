package org.concurrency.lesson10;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * CompletableFuture - класс, который предоставляет удобные методы для
 * асинхронного выполнения операций и работы с результатами этих операций.
 * Всё просто создаем задачу,
 *            запускаем ее,
 *            пишем что будет делать с результатом, когда она выполнится успешно
 *            и что делать если исключение.
 */
public class CompletableFutureExample1 {

    // Пример асинхронной функции, которая выполняет вычисления и возвращает результат через некоторое время
    public static int longRunningTask(int input) {
        try {
            Thread.sleep(2000); // Имитация долгой вычислительной работы (2 секунды)
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return input * 2; // Возвращаем результат
    }

    public static void main(String[] args) {
        // Создаем CompletableFuture с помощью статического метода supplyAsync
        CompletableFuture<Integer> futureResult = CompletableFuture.supplyAsync(() -> longRunningTask(5));

        // Добавляем обработчики для результата и исключения, целую цепочку обработчиков результата
        futureResult.thenAccept(result -> System.out.println("Результат: " + result))
                        .thenAccept(result -> System.out.println("Результат: " + result))
                        .thenAccept(result -> System.out.println("Результат: " + result))
                        .thenAccept(result -> System.out.println("Результат: " + result))
                        .thenAccept(result -> System.out.println("Результат: " + result));
        futureResult.exceptionally(ex -> {
            System.out.println("Произошла ошибка: " + ex.getMessage());
            return null;
        });

        // Подождем завершения CompletableFuture, чтобы увидеть результат
        try {
            futureResult.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }
}
