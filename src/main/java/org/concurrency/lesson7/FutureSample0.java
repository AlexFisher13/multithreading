package org.concurrency.lesson7;

import java.util.concurrent.*;

/**
 * Callable - интерфейс, реализацией которого является поток,
 * который, в отличие от Runnable, должен вернуть какой-то результат.
 * ExecutorService позволяет нам создать пул потоков.
 * Future нужен для представления результата работы потока Callable.

 * Пример показывает методы Future, т.е. методы работы с результатом.
 * isDone, isCancelled, cancel, get, get(5, sec)
 */
public class FutureSample0 {
    public static void main(String[] args) throws InterruptedException, ExecutionException {

        Callable<String> callable = () -> { // реализовываем метод call()
            String threadName = Thread.currentThread().getName();
            System.out.printf("Поток %s начал работу\n", threadName);
            TimeUnit.SECONDS.sleep(5);
            return threadName;
        };

        ExecutorService executorService = Executors.newFixedThreadPool(1);
        Future<String> future = executorService.submit(callable); // добавляем поток в пул

        while (!future.isDone()) { // Пока нет результата выполнения асинх. задачи, выполняем..
            System.out.println("Асинхронная еще не выполнена. Ожидаю...");
            TimeUnit.SECONDS.sleep(1);
        }

        if (!future.isDone()) { // Если нет результата выполнения асинх.задачи
            future.cancel(true); // то тогда отменяем ее
        }

        if (future.isCancelled()) { // Проверяем отменяли ли мы задачу
            System.out.println("Асинхронная задача была отменена");
        } else {
            System.out.printf("Поток %s выполнил асинхронную задачу\n", future.get());
        }

        try {
            future.get(5, TimeUnit.SECONDS);  //Получаем результат работы асинхронной задачи через 5 сек
            System.out.printf("Результат потока %s \n", future.get());
        } catch (TimeoutException e) {
            System.out.println("Время вышло");
        }

        executorService.shutdown(); // если не остановить, то программа не закончится
    }
}
