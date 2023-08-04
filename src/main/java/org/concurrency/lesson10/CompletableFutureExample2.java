package org.concurrency.lesson10;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
* Обработка исключений CompletableFuture
 * exceptionally - обработчик исключений
 * handle - обработчик исключений, но с дополнительной возможностью возвращать результат
 * вне зависимости от того, было ли исключение или нет.
* */
public class CompletableFutureExample2 {
    public static void main(String[] args) throws ExecutionException, InterruptedException {

         CompletableFuture.supplyAsync(() -> {
            throw new RuntimeException("Исключение внутри CompletableFuture");
        }).exceptionally(throwable -> {
            System.out.println("Обрабатываем " + throwable.getMessage());
            return throwable.getMessage();
        });


        CompletableFuture.supplyAsync(() -> {
            throw new RuntimeException("Исключение внутри CompletableFuture");
        }).handle((result, throwable) -> {
            System.out.println("Обрабатываем " + throwable.getMessage());
            return result;
        });
    }
}
