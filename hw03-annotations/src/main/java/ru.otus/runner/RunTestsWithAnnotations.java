package ru.otus.runner;

import ru.otus.annotations.After;
import ru.otus.annotations.Before;
import ru.otus.annotations.Test;
import testClass.TestAnnotations;

import java.lang.reflect.Constructor;
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

            if (method.isAnnotationPresent(Before.class)) {
                stateOfTest.getBeforeMethod().add(nameOfMethod);
            }
            if (method.isAnnotationPresent(Test.class)) {
                stateOfTest.getTestMethod().add(nameOfMethod);
            }
            if (method.isAnnotationPresent(After.class)) {
                stateOfTest.getAfterMethod().add(nameOfMethod);
            }

        });
    }

    private static String runAllMethods(StateOfTest stateOfTest) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException, NoSuchFieldException, InstantiationException {
        int successTests = 0;
        int failTests = 0;

        for (int i = 0; i < stateOfTest.getTestMethod().size(); i++) {
            Constructor<TestAnnotations> constructor_clazz = (Constructor<TestAnnotations>) stateOfTest.getClazz().getConstructor(Integer.class);
            TestAnnotations instanceOfTestAnnotation = constructor_clazz.newInstance(1 + i);
            try {
                for (int j = 0; j < stateOfTest.getBeforeMethod().size(); j++) {

                    Method methodBefore = stateOfTest.getClazz().getMethod(stateOfTest.getBeforeMethod().get(j).toString());
                    methodBefore.invoke(instanceOfTestAnnotation);
                }
                Method methodtest = stateOfTest.getClazz().getMethod(stateOfTest.getTestMethod().get(i));
                var result = methodtest.invoke(instanceOfTestAnnotation);
                System.out.println(result);
                successTests++;
            } catch (Exception ex) {
                failTests++;
                new Exception(ex);
            } finally {
                for (int f = 0; f < stateOfTest.getAfterMethod().size(); f++) {
                    Method methodAfter = stateOfTest.getClazz().getMethod(stateOfTest.getAfterMethod().get(f).toString());
                    methodAfter.invoke(instanceOfTestAnnotation);
                }
            }
            System.out.println("==============================");
        }


        return ">> Успешно выполнено тестов : " + successTests + " \n" + ">>Количество тестов завершившихся ошибкой: " + failTests;
    }
}
