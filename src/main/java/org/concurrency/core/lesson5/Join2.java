package org.concurrency.core.lesson5;

/** Пример показывает, ч
 * */

public class Join2 {
    public static void main(String[] args) throws InterruptedException {
        Thread thread0 = new Thread(() -> {
            for (int i = 0; i < 100; i++) System.out.println("Hello");
        });
        Thread thread1 = new Thread(() -> {
            for (int i = 0; i < 100; i++) System.out.println("Bye");
        });
        thread0.start();
        thread1.start();

        thread0.join();
        thread1.join();

        System.out.println("Main");
    }
}
