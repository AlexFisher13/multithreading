package org.example;

/**
 * Пример показывает, что если один поток захватил мьютекс объекта в блоке synchronized
 * то второй никак не может до него добрать.
 *
 * Здесь мы создаем объект по которому мы будем синхронизироваться
 * и в блоке synchronized мы захватываем мьютекс крутим бесконечный цикл.
 * Соответственно второй поток не может взаимодействовать с этим объектом.
 * */
public class Synchronized_2 {
    public static void main(String[] args) throws InterruptedException {

        Object lock = new Object(); // объект по которому мы будем синхронизироваться

        new Thread(() -> {
            System.out.println("Thread-1 started");
            synchronized (lock) {
                System.out.println("Thread-1 took synchronized object");
                while (true);
            }
        }).start();

        Thread.sleep(1000); // если не спать 1сек, то main поток выполнится быстрее второго потока

        synchronized (lock) {
            System.out.println("Main thread took synchronized object");
            // этот принт никогда не выведется, т.к. первый поток захватил мьютекс у объекта синхронизации
        }

    }
}
