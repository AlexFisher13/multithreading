package org.example;

/**
 * Пример показывает, что неатомарность приводит lost update (когда один поток перезаписывает другой).
 *
 * В этом примере мы в двух потоках инкрементим counter, который кстати volatile,
 * а значит он лежит в ОЗУ и не кэшируется потоками.
 * С помощью join мы дожидаемся когда потоки отработают, и смотрим результат counter.
 *
 * В итоге у на counter должен быть равен N*2, т.е. 20 млн, но у нас counter немного больше N
 * Эта ситуация показывает что операция инкремента не атомарная, а по сути это 3 операции (1. чтение из ОЗУ,
 * 2. инкремент 3. запись в ОЗУ). За это время другой поток успевает прочесть тоже значение и сделать аналогичный инкремент,
 * и записать аналогичное значение.
 */

public class NonAtomic {
    public static final int N = 10_000_000;
    public static volatile int counter = 0;

    public static void main(String[] args) throws InterruptedException {
        Thread t0 = new Thread(() -> {
            for (int i = 0; i < N; i++) counter++;
        });
        t0.start();

        Thread t1 = new Thread(() -> {
            for (int i = 0; i < N; i++) counter++;
        });
        t1.start();

        t0.join();  // дожидаемся до конца выполнения потоков
        t1.join();  // иначе counter будет совсем чуть-чуть

        System.out.println(counter);
    }
}
