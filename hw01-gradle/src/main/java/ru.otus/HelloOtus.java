package ru.otus;

import com.google.common.base.Preconditions;

public class HelloOtus {
    public static void main(String... args) {
        int shoudNotBeZero = 1;
        System.out.println("Value is good : " + shoudNotBeZero);
        shoudNotBeZero = 0;
        String message = "Value shouldn't be zero!!";

        Preconditions.checkArgument(shoudNotBeZero != 0, message, shoudNotBeZero);
        System.out.println(shoudNotBeZero);

    }
}
