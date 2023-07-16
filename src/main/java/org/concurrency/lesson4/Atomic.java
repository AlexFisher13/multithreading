package org.concurrency.lesson4;


import java.util.concurrent.atomic.AtomicInteger;

/**
 * Пример показывает, что атомарные типы делают операцию инкремента атомарной и не кэшируемой
 * и потоки не будут перезаписывать друг друга.
 * Программа работает медленнее, но гарантированно выдает правильный результат.
 */

public class Atomic {
    public static final int N = 10_000_000;
//    public static volatile int counter = 0;
    public static AtomicInteger counter = new AtomicInteger(0);

    public static void main(String[] args) throws InterruptedException {
        Thread t0 = new Thread(() -> {
            for (int i = 0; i < N; i++) counter.incrementAndGet();
        });
        t0.start();

        Thread t1 = new Thread(() -> {
            for (int i = 0; i < N; i++) counter.incrementAndGet();
        });
        t1.start();

        t0.join();  // дожидаемся до конца выполнения потоков
        t1.join();  // иначе counter будет совсем чуть-чуть

        System.out.println(counter);
    }
}
