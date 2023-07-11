package org.concurrency.core.lesson1;

/**
 * Узнаем кол-во ядер на машине
 * */

public class CoreCount {
    public static void main(String[] args) {
        System.out.println(Runtime.getRuntime().availableProcessors());
    }
}
