package org.concurrency.lesson3;

import java.util.concurrent.Semaphore;

/**
 * Semaphore нужен когда нужно обеспечить доступ к ресурсу для НЕСКОЛЬКИХ потоков.
 *
 * Мы создаем семафор для одновременного досутпа двух потоков. И самостоятельно его инкрементим и декрементим.
 * И счетчик семафора декрементится до 0, семафор больше никого не пускает.
 */
public class SemaphoreSample {
    public static void main(String[] args) {
        Semaphore semaphore = new Semaphore(2); // даём доступ для 2-ух потоков

        Runnable runnable = () -> {
            System.out.printf("Поток %s запустился и запрашивает доступ к someMethod\n", Thread.currentThread().getName());
            try {
                semaphore.acquire(); // (англ."приобретать") уменьшает счетчик семафоры на 1
                someMethod();
            } catch (InterruptedException e) {}
            System.out.printf("Поток %s выполнил someMethod\n", Thread.currentThread().getName());
            semaphore.release(); //(англ."отпускать") увеличивает счетчик семафоры на 1
        };

        new Thread(runnable, "Thread-1").start();
        new Thread(runnable, "Thread-2").start();
        new Thread(runnable, "Thread-3").start();
    }

    private static void someMethod() throws InterruptedException {
        System.out.printf("Внутри someMethod работает  %s \n", Thread.currentThread().getName());
        Thread.sleep(3000);
    }
}
