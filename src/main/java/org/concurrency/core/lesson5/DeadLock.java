package org.concurrency.core.lesson5;

/**
 * Классический пример deadlock'а
 * каждый из потоков ждет другой
* */

public class DeadLock {
    public static void main(String[] args) {
        Thread[] threads = new Thread[2];
        threads[0] = new Thread(() -> {
            try { threads[1].join();
            } catch (InterruptedException e) {}
        });

        threads[1] = new Thread(() -> {
            try { threads[0].join();
            } catch (InterruptedException e) {}
        });

        threads[0].start();
        threads[1].start();
    }
}
