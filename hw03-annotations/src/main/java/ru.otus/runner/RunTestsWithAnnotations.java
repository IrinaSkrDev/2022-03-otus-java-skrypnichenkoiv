package ru.otus.runner;

import ru.otus.annotations.After;
import ru.otus.annotations.Before;
import ru.otus.annotations.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

public class RunTestsWithAnnotations {
    public static String runTests(Object className) throws Exception {
        StateOfTest stateOfTest = new StateOfTest(className);
        getTestsMethods(stateOfTest);
        return runAllMethods(stateOfTest);
    }

    private static void getTestsMethods(StateOfTest stateOfTest) {

        Method[] methodsAll = stateOfTest.getClazz().getDeclaredMethods();
        Arrays.stream(methodsAll).forEach(method -> {
            Method annotatedMethod = null;
            String nameOfMethod = method.getName();
            method.setAccessible(true);
            try {
                annotatedMethod = stateOfTest.getClazz().getMethod(nameOfMethod);
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }

            if (annotatedMethod.isAnnotationPresent(Before.class)) {
                stateOfTest.getBeforeMethod().add(nameOfMethod);
            }
            if (annotatedMethod.isAnnotationPresent(Test.class)) {
                stateOfTest.getTestMethod().add(nameOfMethod);
            }
            if (annotatedMethod.isAnnotationPresent(After.class)) {
                stateOfTest.getTestMethod().add(nameOfMethod);
            }

        });
    }

    private static String runAllMethods(StateOfTest stateOfTest) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        int successTests = 0;
        int failTests = 0;
        try {
            for (int i = 0; i < stateOfTest.getBeforeMethod().size(); i++) {
                Method methodBefore = stateOfTest.getClazz().getMethod(stateOfTest.getBeforeMethod().get(i));
                methodBefore.invoke(stateOfTest.getClassName());
            }
            for (int i = 0; i < stateOfTest.getTestMethod().size(); i++) {
                Method methodtest = stateOfTest.getClazz().getMethod(stateOfTest.getTestMethod().get(i));
                methodtest.invoke(stateOfTest.getClassName());
                successTests++;
            }

        } catch (Exception ex) {
            failTests++;
            new Exception(ex);
        } finally {
            for (int i = 0; i < stateOfTest.getAfterMethod().size(); i++) {
                Method methodAfter = stateOfTest.getClazz().getMethod(stateOfTest.getAfterMethod().get(i));
                methodAfter.invoke(stateOfTest.getClass());
            }
        }

        return ">> Успешно выполнено тестов : " + successTests + " \n" + ">>Количество тестов завершившихся ошибкой: " + failTests;
    }
}
