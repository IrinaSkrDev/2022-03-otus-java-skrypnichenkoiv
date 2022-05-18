package ru.otus.testedClasses;

import lombok.Getter;

@Getter
public class ClassNeedForTesting {
    String myFIeld;

    public ClassNeedForTesting(String myField) {
        this.myFIeld = myField;
        System.out.println("created a class that needs to be tested!");
    }
}
