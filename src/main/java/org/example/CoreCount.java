package org.example;

/**
 * Узнаем кол-во ядер на машине
 * */

public class CoreCount {
    public static void main(String[] args) {
        System.out.println(Runtime.getRuntime().availableProcessors());
    }
}
