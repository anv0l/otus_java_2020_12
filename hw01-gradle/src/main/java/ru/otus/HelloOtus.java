package ru.otus;

import com.google.common.base.Splitter;
import com.google.common.collect.Iterables;

import java.util.Scanner;

public class HelloOtus {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Введите слова, разделённые пробелами: ");
        String userInput = sc.nextLine();
        if (userInput.equals("")) {
            System.out.println("Введена пустая строка.");
        }
        else {
            Iterable<String> words = Splitter.on(' ').split(userInput);
            System.out.println("Всего слов: " + Iterables.size(words));
            System.out.println(words);
        }
    }
}