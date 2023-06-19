package org.example;

/**
 * Пример показывает, что synchronized решает проблему lost update, т.к.
 * в synchronized методе может работать только один поток.
 * Программа работает медленнее, но гарантированно выдает правильный результат.
 */
public class Synchronized {
    public static final int N = 10_000_000;
    public static int counter = 0;

    public static synchronized void inc() {
        counter++;
    }

    public static void main(String[] args) throws InterruptedException {
        Thread t0 = new Thread(() -> {
            for (int i = 0; i < N; i++) inc();
        });
        t0.start();

        Thread t1 = new Thread(() -> {
            for (int i = 0; i < N; i++) inc();
        });
        t1.start();

        t0.join();  // дожидаемся до конца выполнения потоков
        t1.join();  // иначе counter будет совсем чуть-чуть

        System.out.println(counter);
    }
}

