package org.concurrency.lesson9;

import java.util.Random;
import java.util.concurrent.ForkJoinPool;

/**
 * Пример задачи с ForkJoinPool. Найти максимальное число.
 * Мы создадим сам массив на 1млд элементов.
 * Измерим скорость итеративное время поиска и через ForkJoin.
 *
 * Для ForkJoin создадим задачу MaxFindingTask в которой будет логика вычисления и деления на подзадачи.
 * В итоге мы видим, что с чем большим массивом мы работаем, тем быстрее работает ForkJoin/
 * */
public class ForkJoinExample {
    public static int THRESHOLD;


    public static void main(String[] args) {
        int[] numbers = initNumbers(1_000_000_000);
        THRESHOLD = numbers.length / Runtime.getRuntime().availableProcessors(); //определяем порог деления на подзадачи

        long start = System.currentTimeMillis();
        System.out.println("Max number: " + findMaxNumber(numbers));
        System.out.println("Time (iterative): " + (System.currentTimeMillis() - start) + "ms\n");

        ForkJoinPool pool = new ForkJoinPool(Runtime.getRuntime().availableProcessors());
        MaxFindingTask task = new MaxFindingTask(numbers, 0, numbers.length);

        start = System.currentTimeMillis();
        System.out.println("Max number: " + pool.invoke(task));
        System.out.println("Time (forkJoin): " + (System.currentTimeMillis() - start) + "ms\n");

    }

    private static int[] initNumbers(int arraySize) {
        Random random = new Random();
        int[] numbers = new int[arraySize];
        for (int i = 0; i < arraySize; i++) {
            numbers[i] = random.nextInt(1_000_000_000);
        }
        return numbers;
    }

    // метод для простого нахождения максимум, для сравнения с ForkJoin
    private static int findMaxNumber(int[] numbers) {
        int max = numbers[0];
        for (int i = 1; i < numbers.length; i++) {
            if (numbers[i] > max) {
                max = numbers[i];
            }
        }
        return max;
    }
}
