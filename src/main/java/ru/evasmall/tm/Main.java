package ru.evasmall.tm;

import java.util.Arrays;
import java.util.Scanner;
import java.util.logging.Logger;

import static ru.evasmall.tm.constant.Constants.*;

/**
 * Учебное приложение по тестированию
 */
public class Main
{
    private static Logger logger = Logger.getLogger(Main.class.getName());

    public static void main( String[] args )
    {
        final Scanner scanner = new Scanner(System.in);
        System.out.println( "*** Учебная программа по тестированию ***" );
        displayHelp();
        String command = "";
        while (!EXIT.equals(command)) {
            command = scanner.nextLine();
            run(command);
        }
    }

    private static int run(final String param) {
        Scanner scannerArg = new Scanner(System.in);
        MathService mathService = new MathService();
        if (param == null || param.isEmpty()) return -1;
        try {
            switch (param) {
                case HELP: {
                    return displayHelp();
                }
                case SUM: {
                    System.out.println("Введите первое слагаемое:");
                    String arg1 = scannerArg.nextLine();
                    System.out.println("Введите первое слагаемое:");
                    String arg2 = scannerArg.nextLine();
                    System.out.println("Сумма чисел: " + arg1 + " + " + arg2 + " = " + mathService.sum(arg1, arg2));
                    return RETURN_OK;
                }
                case FACTORIAL: {
                    System.out.println("Введите число:");
                    String arg = scannerArg.nextLine();
                    System.out.println("Факториал числа: " + arg + "! = " + mathService.factorial(arg));
                    return RETURN_OK;
                }
                case FIBONACCI: {
                    System.out.println("Введите число:");
                    String arg = scannerArg.nextLine();
                    System.out.println("Разложение числа " + arg + " на сумму чисел Фибоначчи: " + Arrays.toString(mathService.fibonacci(arg)));
                    return RETURN_OK;
                }
                case EXIT: {
                    System.out.println("Работа программы завершена Всего доброго!");
                    return RETURN_OK;
                }
                default: {
                    logger.severe("Неизвестная команда. Ошибка!");
                    return RETURN_ERROR;
                }
            }
        } catch (IllegalArgumentException e) {
            logger.severe(e.getMessage());
            return RETURN_ERROR;
        }
    }

    private static int displayHelp() {
        System.out.println("help - Перечень команд.");
        System.out.println("sum - Сумма чисел.");
        System.out.println("factorial - Вычисление факториала.");
        System.out.println("fibonacci - Разложение числа на сумму числе Фибоначчи.");
        System.out.println("exit - Завершение программы.");
        return RETURN_OK;
    }

}
