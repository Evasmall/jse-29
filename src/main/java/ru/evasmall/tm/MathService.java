package ru.evasmall.tm;

import java.math.BigInteger;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.*;
import java.util.stream.IntStream;

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
            System.out.println();
            throw new IllegalArgumentException(e +" Аргумент не является целым числом. Ошибка формата.");
        }
    }

    /**
     * Проверка на принадлежность к множеству целых чисел
     * @param arg - Строковый аргумент
     * @return - Конвертированное число Integer формата
     */
    public int toInteger (String arg) {
        try {
            return Integer.parseInt(arg);
        } catch (Exception e) {
            System.out.println();
            throw new IllegalArgumentException(e +" Аргумент не является целым числом. Ошибка формата.");
        }
    }

    /**
     * Проверка на отрицательные числа
     * @param arg - строковый аргумент
     * @return - Неотрицательное число формата long
     */
    private long isPlus (long arg){
        if (arg < 0) {
            throw new IllegalArgumentException("Аргумент является отрицательным числом. Ошибка.");
        }
        return arg;
    }

    /**
     * Проверка на отрицательные числа
     * @param arg - строковый аргумент
     * @return - Неотрицательное число формата int
     */
    private int isPlus (int arg){
        if (arg < 0) {
            throw new IllegalArgumentException("Аргумент является отрицательным числом. Ошибка.");
        }
        return arg;
    }

    /**
     * Проверка числа потоков на отрицательные числа и равенство нулю
     * @param arg - строковый аргумент
     * @return - Неотрицательное число формата int
     */
    public int isThreadPlus (int arg){
        if (arg < 1) {
            throw new IllegalArgumentException("Число потоков не может быть отрицательным числом или равным нулю. Ошибка.");
        }
        return arg;
    }

    /**
     * Метод вычисления факториала неотрицательного целого числа формата Long.
     * @param arg - строковый аргумент
     * @return - факториал числа формата Long
     * @throws IllegalArgumentException - исключение, если одно из слагаемых не является целым или положительным числом
     */
    public long factorialLong(String arg) {
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
     * Метод вычисления факториала неотрицательного целого числа формата BigInteger.
     * @param arg - строковый аргумент
     * @param threadsCount - число потоков
     * @return - факториал числа формата BigInteger
     * @throws IllegalArgumentException - исключение, если одно из слагаемых не является целым или положительным числом
     */
    public BigInteger factorialBigInteger(String arg, int threadsCount) {
        int argInt = isPlus(toInteger(arg));
        int distance = argInt/threadsCount;
        System.out.println("Интервал = " + distance);
        if (distance < 1) {
            distance = 1;
        }
        BigInteger result = BigInteger.valueOf(1);
        List<FactorialThread> factorialThreads = new LinkedList<>();
        for (int i = 1; i <= argInt; i = i + distance) {
            int start = i;
            int end = i + distance -1;
            if(end >= argInt) {
                end = argInt;
            }
            factorialThreads.add(new FactorialThread(start, end));
        }
        for (FactorialThread factorialThread : factorialThreads) {
            Thread thread = new Thread(factorialThread);
            thread.start();
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            result = result.multiply(factorialThread.getResult());
        }
        return result;
    }

    /**
     * Метод вычисления факториала неотрицательного целого числа формата BigInteger без использования класса Thread и synchronized блоков.
     * @param arg - строковый аргумент
     * @param threadsCount - число потоков
     * @return - факториал числа формата BigInteger
     * @throws IllegalArgumentException - исключение, если одно из слагаемых не является целым или положительным числом
     */
    public BigInteger factorialNoThread(String arg, int threadsCount) {
        int argInt = isPlus(toInteger(arg));
        int distance = argInt/threadsCount;
        System.out.println("Интервал = " + distance);
        if (distance < 1) {
            distance = 1;
        }
        BigInteger result = BigInteger.valueOf(1);
        ExecutorService executorService = Executors.newFixedThreadPool(threadsCount);
        List<Callable<BigInteger>> tasks = new LinkedList<>();
        for (int i = 1; i <= argInt; i = i + distance) {
            int start = i;
            int end = i + distance -1;
            if(end >= argInt) {
                end = argInt;
            }
            tasks.add(new FactorialCallable(start, end));
        }
        try {
            List<Future<BigInteger>> futureList = executorService.invokeAll(tasks);
            for (Future<BigInteger> future : futureList) {
                result = result.multiply(future.get());
            }
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        executorService.shutdown();
        return result;
    }

    /**
     * Метод вычисления факториала неотрицательного целого числа формата BigInteger средствами Stream API.
     * @param arg - строковый аргумент
     * @return - факториал числа формата BigInteger
     * @throws IllegalArgumentException - исключение, если одно из слагаемых не является целым или положительным числом
     */
    public BigInteger factorialStream(String arg) {
        int argInt = isPlus(toInteger(arg));
        if(argInt < 2) return BigInteger.valueOf(1);
        return IntStream.rangeClosed(2, argInt).parallel().mapToObj(BigInteger::valueOf).reduce(BigInteger::multiply).get();
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
