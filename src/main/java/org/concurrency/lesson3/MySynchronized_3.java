package org.concurrency.lesson3;

/**
 * Пример, показывает то, что второй поток не может зайти в synchronized метод,
 * пока там работает первый поток.
 */
public class MySynchronized_3 {
    public static void main(String[] args) {

        Runnable runnable = () -> {
            System.out.printf("Поток %s стартанул\n", Thread.currentThread().getName());
            syncMethod();
            System.out.printf("Поток %s финишировал\n", Thread.currentThread().getName());
        };

        new Thread(runnable, "Thread-1").start();
        new Thread(runnable, "Thread-2").start();
    }

    public synchronized static void syncMethod() {
        System.out.printf("Поток %s зашел в syncMethod\n", Thread.currentThread().getName());
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {}
        System.out.printf("Поток %s завершает syncMethod\n", Thread.currentThread().getName());
    }
}
