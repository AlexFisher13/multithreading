package org.example;

/**
 * Пример показывает примеры состояния потоков.
 * */

public class States {
    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Thread(() -> {
            for (int i = 0; i < 10000; i++) {
                System.out.println("Hello");
            }
        });
        System.out.println(thread.getState()); // NEW - не запущен
        thread.start();
        System.out.println(thread.getState()); // RUNNABLE - работает
        thread.join();
        System.out.println(thread.getState()); // TERMINATED - уничтожен
    }
    Object object = new Object();


    public synchronized void method1() {
        object.notify();
    }

    // аналогично
    public void method2() {
        synchronized (this) {
            object.notify();
        }
    }
}
