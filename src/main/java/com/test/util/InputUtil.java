package com.test.util;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.function.Supplier;

public class InputUtil {
    private static Scanner scanner = new Scanner(System.in);

    public static String nextLine(String message) {
        System.out.print(message);
        return scanner.nextLine();
    }

    public static String nextLine(String message, String... value) {
        List<String> list = Arrays.asList(value);
        while(true) {
            System.out.print(message);
            String input = scanner.nextLine();
            if(list.contains(input)) {
                return input;
            }
            sendErrorAndWait("输入有误，请重新输入");
        }
    }

    public static int nextInt(String message) {
        while(true) {
            try {
                System.out.print(message);
                int input = scanner.nextInt();
                scanner.nextLine(); // 清空输入缓冲区
                return input;
            } catch (InputMismatchException e) {
                sendErrorAndWait("输入有误，请重新输入！");
                scanner.nextLine(); // 清空输入缓冲区
            }
        }
    }

    public static <T> T nextEntity(String message, Function<Integer, T> getter) {
        while(true) {
            try {
                System.out.print(message);
                int i = scanner.nextInt();
                T entity = getter.apply(i);
                if(entity != null) {
                    System.out.println("已选择：" + entity);
                    return entity;
                } else {
                    sendErrorAndWait("未在数据库中找到次ID数据，请重新输入！");
                }
            } catch (InputMismatchException e) {
                sendErrorAndWait("输入有误，请重新输入！");
            } finally {
                scanner.nextLine();
            }
        }
    }

    private static void sendErrorAndWait(String message){
        System.err.println(message);
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
