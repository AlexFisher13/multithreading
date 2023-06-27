package org.example;

/**
 * JVM завершает свою работу, когда закончились все потоки, не считая демонов.
 *
 * В примере мы создаем поток в котором вечно выводим Hello,
 * и мы назначаем его демоном.
 * И он завершается сразу, когда завершается наш поток main.
 * */

public class Daemon {
    public static void main(String[] args) {
        Thread thread = new Thread(() -> {
            while (true) {
                System.out.println("Hello");
            }
        });
        thread.setDaemon(true);
        thread.start();

        System.out.println("Bye...");
    }
}
