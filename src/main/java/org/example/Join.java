package org.example;

/** Пример показывает, что если мы не дожидаемся завершения потока с помощью join()
 * то основной поток, выполняется сильно раньше, чем созданный поток.
 * В нашем случае "Main" выводится даже раньше чем "Hello", это показывает, что
 * второй поток еще даже не успел стартовать, когда основной уже завершил свою работу.
 *
 * Метод join() нам гарантирует, что мы дождемся выполнения этого потока и только потом пойдем дальше.
 * */

public class Join {
    public static void main(String[] args) throws InterruptedException {
        Thread thread0 = new Thread(() -> {
            for (int i = 0; i < 100; i++) System.out.println("Hello");
        });
        thread0.start();
//        thread0.join();
        System.out.println("Main");
    }
}
