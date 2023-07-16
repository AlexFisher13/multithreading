package org.concurrency.lesson9;

import java.util.concurrent.RecursiveTask;

/**
 * Пример класса задачи для ForkJoinPool
 * Наследуя RecursiveTask мы должны реализовать метод compute - в котором будет
 * рекурсивная логика вычисления и разделения на подзадачи.
 */
public class MaxFindingTask extends RecursiveTask<Integer> {
    private int[] numbers;
    private int highIndex;
    private int lowIndex;

    public MaxFindingTask(int[] numbers, int lowIndex, int highIndex) {
        this.numbers = numbers;
        this.lowIndex = lowIndex;
        this.highIndex = highIndex;
    }

    @Override
    protected Integer compute() {
        // THRESHOLD (англ. порог) деления на подзадачи, то есть мы определяем
        // на насколько мелкие части мы будем делить наш массив
        if (highIndex - lowIndex < ForkJoinExample.THRESHOLD) {
            return findMaxNumber();
        } else {
            int middleIndex = (lowIndex + highIndex) / 2;
            // создаем подзадачи
            MaxFindingTask maxFindingTask1 = new MaxFindingTask(numbers, lowIndex, middleIndex);
            MaxFindingTask maxFindingTask2 = new MaxFindingTask(numbers, middleIndex + 1, highIndex);
            // запускаем эти подзадачи
            invokeAll(maxFindingTask1, maxFindingTask2);
            // далее ждем завершения этих потоков и берем max среди двух результатов
            return Math.max(maxFindingTask1.join(), maxFindingTask1.join());
        }
    }

    private Integer findMaxNumber() {
        int max = numbers[lowIndex];
        for (int i = lowIndex + 1; i < highIndex; i++) {
            if (numbers[i] > max) {
                max = numbers[i];
            }
        }
        return max;
    }
}
