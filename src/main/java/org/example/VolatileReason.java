package org.example;

/**
 * Что показывает пример.
 * Пока первый поток спит 1 секунду, второй поток его читает и для этого он это поле кэширует.
 * И в таком случае он крутится в бесконечном цикле.

 * Нюанс. Разные JVM в этой ситуации могут себя вести по-разному.
 * Джошуа Блох тестировал этот некорректный код (без volatile) код на нескольких JVM
 * и почти на всех, кроме одной, она работает правильно, хотя не должна.
 * На моем m1 она работает неправильно и не должна. Когда нет volatile у меня бесконечный цикл,
 * так как значение закэшировано. А когда есть volatile то через секунду всё ок (1).

 * volatile указывает процессору не кэшировать значение поля в кэш ядра процессора или регистры.
 * Оно будет оставаться лежать в ОЗУ. (правда чтение из ОЗУ в 200 раз медленее, чем из кэша).
 *
 * volatile создает happens before между потоками.
 * Когда первый поток будет записывать наше volatile поле ready = true,
 * он сгрузит все свои закэшированные данные в ОЗУ (в нашем случае cгрузит значение data)
 * которые выполнялись до операции с volatile.
 * Именно поэтому второй поток, увидев изменение значения ready, также увидит значение data,
 * хоть оно и не volatile.
 * А если бы у нас строчка data = 1 выполнялась после ready = true то возможно
 * первый поток не сгрузил бы значение data в ОЗУ и второй не узнал бы о его изменении.
 * */
public class VolatileReason {
    volatile static boolean ready = false;
    static int data = 0;

    public static void main(String[] args) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                sleep(1000);
                data = 1;
                ready = true;
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (!ready) {
                }
                System.out.println(data);
            }
        }).start();
    }

    private static void sleep(int mills) {
        // оборачиваем checked exception в unchecked
        try {
            Thread.sleep(mills);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
