package ru.logs.testedClass;

import ru.logs.annotation.Log;

public class TestedClass implements ITestedClasses {

    @Log
    @Override
    public void calculation(int param) {
        System.out.println("Метод был выполнен!" + param);

    }
    @Log
    @Override
    public void calculation(int param, int param1, int param2) {
        System.out.println("Метод был выполнен!" + param + param1 + param2);


    }
    @Log
    public void calculation(int param, int param1, int param2, String param3) {
        System.out.println("Метод был выполнен!" + param + param1 + param2 + param3);


    }
    @Log
    @Override
    public void calculation(int param, String param1) {
        System.out.println("Метод был выполнен!" + param + param1);


    }


    public void calculation(String param, int param1,  String param3) {
        System.out.println("Метод был выполнен!" + param + param1 +  param3);


    }
}
