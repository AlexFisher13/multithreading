package org.concurrency.lesson1;

/** Пример, который показывает непредсказуемость потоков,
 * то есть распределение активности между потоками будет неравномерным
 *
 * Первый (main) поток и второй спят одинаковое время и выводят запись на экран
 * и мы можем заметить что это не всегда происходит по очереди, хотя в идеале должно.
 * */
public class NotDeterminate {
    public static void main(String[] args) throws InterruptedException {
        System.out.println("Count of active threads: " + Thread.activeCount());

        new Thread(new XRunnable()).start();

        for (int i = 0; i < 100; i++) {
            System.out.println(i + " Thread: " + Thread.currentThread().getName());
            Thread.sleep(200);
        }

        System.out.println("Count of active threads: " + Thread.activeCount());
    }
}

class XRunnable implements Runnable {
    @Override
    public void run() {
        for (int i = 0; i < 100; i++) {
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
            }
            System.out.println(i + " Thread: " + Thread.currentThread().getName());
        }
    }
}