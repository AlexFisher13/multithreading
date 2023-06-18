package org.example;

/** Оговорка. Разные JVM в этой ситуации могут себя вести по-разному.
 * Джошуа Блох тестировал этот некорректный код (без volatile) код на нескольких JVM
 * и почти на всех, кроме одной, она работает правильно, хотя не должна.
 * На моем m1 она работает неправильно и не должна. Когда нет volatiole у меня бесконечных цикл,
 * так как значение закэшировано. А когда есть volatile то через секунду всё ок (1).
 *
 * volatile указывает процессру не кэшировать значение поля в кэш процессора или регистры.
 * Оно будет оставать лежать в ОЗУ. Именно поэтому доступ к нему будет в 200 раз медленее.
 *
 * Что показывает пример.
 * Пока первый поток спал 1сек, второй поток мог прочитать поле ready примерно 3 миллиарда раз
 * (так как частота процессора m1pro = 3.2 ГГц, а это 3,2 млрд тактов в секунду)
 *
 * Без volatile
 * Т.к. второй поток, часто обращается к значению поля ready, то его в кэш процессора или
 * даже регистры процессора для быстрого доступа. И так как оно закэшировано, то поток не скоро обнаружит,
 * что значение изменилось.
 *
 * С volatile
 * Мы указали не кэшировать это поле. И второй поток сразу увидит когда оно изменилось.
 *
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
