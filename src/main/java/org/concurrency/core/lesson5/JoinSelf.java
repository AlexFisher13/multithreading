package org.concurrency.core.lesson5;

/**
 * Простейший вариант deadlock'а - мы ждем сами себя
 * */

public class JoinSelf {
    public static void main(String[] args) throws InterruptedException {
        Thread.currentThread().join();
    }
}
