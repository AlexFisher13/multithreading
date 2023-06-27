package org.example;

/**
 * Пример показывает бесполезность приоритизации потоков.
 * Т.к. каждая система по своем понимает приоритетность и это непредсказуемо.
 * Нельзя проектировать системы опираясь на приоритеты.
 *
 * В нашем примере мы создаем массив из 500 потоков.
 * (по идее если создать кол-во потоков такое же как и ядер то каждый поток ляжет на ядро
 * и будет выполняться вне зависимости от приоритета, поэтому создадим явно больше потоков).
 *
 * Каждый поток будет просто инкрементить счетчик.
 * Половине из потоков я присвою максимальный приоритет, другой половине минимальный и запустил.
 * Далее с помощью флага я все остановил.
 *
 * И видим, что у нас НЕ сложилась ситуация, когда потоки с максимальным приоритетом явно работали больше.
 * Ситуация хаотичная.
 *
 * */
public class Priority {
    static volatile boolean stop = false;

    public static void main(String[] args) throws InterruptedException {
        final Thread[] threads = new Thread[500];

        for (int i = 0; i < threads.length; i++) {
            final int threadNumber = i;
            threads[i] = new Thread(() -> {
                for (long j = 0; j < 1_000_000_000_000L; j++) {
                    if (stop) {
                        System.out.println("Thread #" + threadNumber + ": " + j);
                        break;
                    }
                }
            });
        }

        for (int i = 0; i < threads.length; i++) {
            threads[i].setPriority(i < threads.length/2 ? Thread.MIN_PRIORITY : Thread.MAX_PRIORITY);
        }

        for (Thread thread : threads) {
            thread.start();
        }
//        Thread.sleep(100);
        stop = true;
    }

}
