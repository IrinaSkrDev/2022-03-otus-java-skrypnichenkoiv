package ru.otus.runner;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class StateOfTest {
    private final Object className;
    private final Class<?> clazz;
    private final List<String> beforeMethod;
    private final List<String> testMethod;
    private final List<String> afterMethod;

    public StateOfTest(Object className) {
        this.className = className;
        this.clazz = className.getClass();
        this.beforeMethod = new ArrayList<>();
        this.testMethod = new ArrayList<>();
        this.afterMethod = new ArrayList<>();
    }
}
