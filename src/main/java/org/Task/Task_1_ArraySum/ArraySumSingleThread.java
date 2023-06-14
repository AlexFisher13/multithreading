package org.Task.Task_1_ArraySum;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

/** Нужно найти сумму чисел из файла (4,8млн чисел)
 *
 * Single Thread - 8ms (8 910 000 наносек)
 * Multi Thread -
 * */

public class ArraySumSingleThread {
    public static void main(String[] args) {
        String filename = "/Users/fisher/IdeaProjects/test-stream/multithreading/src/main/java/org/Task/Task_1_ArraySum/numbers.txt";
        List<Integer> numbers = readNumbersFromFile(filename);
        sum(numbers);
    }


    public static int sum(List<Integer> numbers) {
        Instant start = Instant.now();
        int sum = 0;
        for (Integer i : numbers) {
            sum+= i;
        }
        Duration between = Duration.between(start, Instant.now());
        System.out.printf("Completed in time %d ns, sum = %d\n", between.toNanos(), sum);
        return sum;
    }

    private static List<Integer> readNumbersFromFile(String filename) {
        List<Integer> numbers = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            int character;
            while ((character = br.read()) != -1) {
                int numericValue = Character.getNumericValue(character);
                numbers.add(numericValue);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return numbers;
    }
}

class MyCallable implements Callable<Integer> {
    List<Integer> numbers;

    public MyCallable(List<Integer> numbers) {
        this.numbers = numbers;
    }

    @Override
    public Integer call() throws Exception {
        return ArraySumSingleThread.sum(numbers);
    }
}
