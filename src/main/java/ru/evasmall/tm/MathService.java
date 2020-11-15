package ru.evasmall.tm;

import java.util.LinkedList;
import java.util.List;

public class MathService {
    /**
     * Метод вычисления суммы двух целых чисел.
     * @param arg1 - первое слагаемое
     * @param arg2 - второе слагаемое
     * @return - сумму слагаемых
     * @throws IllegalArgumentException - исключение, если одно из слагаемых не является целым числом
     */
    public long sum(String arg1, String arg2) {
        long arg1Long = toLong(arg1);
        long arg2Long = toLong(arg2);
        return arg1Long + arg2Long;
    }

    /**
     * Проверка на принадлежность к множеству целых чисел
     * @param arg - Строковый аргумент
     * @return - Конвертированное число long формата
     */
    private long toLong (String arg) {
        try {
            return Long.parseLong(arg);
        } catch (Exception e) {
            System.out.println("Аргумент не является целым числом. Ошибка формата.");
            throw new IllegalArgumentException(e);
        }
    }

    /**
     * Проверка на отрицательные числа
     * @param arg - строковый аргумент
     * @return - Неотрицательное число формата long
     */
    private long isPlus (long arg){
        if (arg <0) {
            System.out.println("Аргумент является отрицательным числом. Ошибка.");
            throw new IllegalArgumentException();
        }
        return arg;
    }

    /**
     * Метод вычисления факториала неотрицательного целого числа.
     * @param arg - строковый аргумент
     * @return - факториал числа
     * @throws IllegalArgumentException - исключение, если одно из слагаемых не является целым или положительным числом
     */
    public long factorial(String arg) {
        long argLong = isPlus(toLong(arg));
        long result = 1;
        for (long i = 1; i <= argLong; i++) {
            try {
                result = Math.multiplyExact(result, i);
            } catch (ArithmeticException e) {
                System.out.println("Переполнение буфера. Ошибка вычислений.");
                throw new IllegalArgumentException();
            }
        }
        return result;
    }

    /**
     * Метод раскладывает неотрицательное целое число на сумму чисел Фибоначчи.
     * @param arg - Строковый аргумент
     * @return - ряд чисел
     * @throws IllegalArgumentException - исключение, если одно из слагаемых не является целым или положительным числом, а так же невозможно разложить в ряд чисел
     */
    public long[] fibonacci(String arg) {
        long argLong = isPlus(toLong(arg));
        List<Long> list = new LinkedList<>();
        list.add(0L);
        list.add(1L);
        long previousNumber = 0;
        long nextNumber = 1;
        long sum = 1;
        for (long i = 1; i < argLong; i++) {
            sum = previousNumber + nextNumber;
            previousNumber = nextNumber;
            nextNumber = sum;
            list.add(sum);
            if (argLong == sum) {
                break;
            }
            if (sum > argLong) {
                throw new IllegalArgumentException("Число невозможно разложить в ряд Фибоначчи. Ошибка.");
            }
        }
        long[] result = new long[list.size()];
        int n;
        for (n = 0; n < list.size(); n++) {
            result[n] = list.get(n);
        }
        return result;
    }

}
