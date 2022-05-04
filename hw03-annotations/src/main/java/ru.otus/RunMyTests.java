package ru.otus;

import ru.otus.annotations.After;
import ru.otus.annotations.Before;
import ru.otus.annotations.Test;
import testClass.TestAnnotations;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;

public class RunMyTests {
    public static void main(String[] args) throws Exception {
        TestAnnotations testAnnotations = new TestAnnotations(1);
        TestAnnotations testAnnotationsSecondObj = new TestAnnotations(2);
        TestAnnotations testAnnotationsThirdObj = new TestAnnotations(3);
        String result = runTests(testAnnotations);
        System.out.println(result);
        String resultSecond = runTests(testAnnotationsSecondObj);
        System.out.println(resultSecond);
        String resultThird = runTests(testAnnotationsThirdObj);
        System.out.println(resultThird);
    }

    private static String runTests(Object className) throws Exception {
        Class<?> clazz = className.getClass();
        Method[] methodsAll = clazz.getDeclaredMethods();
        ArrayList<String> beforeMethod = new ArrayList<>();
        ArrayList<String> testMethod = new ArrayList<>();
        ArrayList<String> afterMethod = new ArrayList<>();

        Arrays.stream(methodsAll).forEach(method -> {
            Method annotatedMethod = null;
            String nameOfMethod = method.getName();
            method.setAccessible(true);
            try {
                annotatedMethod = clazz.getMethod(nameOfMethod);
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }

            if (annotatedMethod.isAnnotationPresent(Before.class)) {
                beforeMethod.add(nameOfMethod);
            }
            if (annotatedMethod.isAnnotationPresent(Test.class)) {
                testMethod.add(nameOfMethod);
            }
            if (annotatedMethod.isAnnotationPresent(After.class)) {
                afterMethod.add(nameOfMethod);
            }

        });

        int successTests = 0;
        int failTests = 0;
        try {
            for (int i = 0; i < beforeMethod.size(); i++) {
                Method methodBefore = clazz.getMethod(beforeMethod.get(i));
                methodBefore.invoke(className);
            }
            for (int i = 0; i < testMethod.size(); i++) {
                Method methodtest = clazz.getMethod(testMethod.get(i));
                methodtest.invoke(className);
                successTests++;
            }

        } catch (Exception ex) {
            failTests++;
            new Exception(ex);
        } finally {
            for (int i = 0; i < afterMethod.size(); i++) {
                Method methodAfter = clazz.getMethod(afterMethod.get(i));
                methodAfter.invoke(className);
            }
        }

        return ">> Успешно выполнено тестов : " + successTests + " \n" + ">>Количество тестов завершившихся ошибкой: " + failTests;
    }
}
