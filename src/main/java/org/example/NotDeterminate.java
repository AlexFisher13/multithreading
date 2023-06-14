package org.example;

/** Пример, который показывает непредсказуемость потоков,
 * то есть распределение активности между потоками будет неравномерным*/
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
                Thread.sleep(300);
            } catch (InterruptedException e) {
            }
            System.out.println(i + " Thread: " + Thread.currentThread().getName());
        }
    }
}