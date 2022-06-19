package ru.logs.proxy;

import ru.logs.annotation.Log;
import ru.logs.testedClass.ITestedClasses;
import ru.logs.testedClass.TestedClass;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;

public class Ioc {
    private Ioc() {
    }

    public static ITestedClasses createItestedClasses() {
        InvocationHandler invocationHandler = new DemoInvocationHandler(new TestedClass());
        return (ITestedClasses) Proxy.newProxyInstance(Ioc.class.getClassLoader(), new Class<?>[]{ITestedClasses.class}, invocationHandler);
    }

    static class DemoInvocationHandler implements InvocationHandler {
        private final ITestedClasses testedClasses;

        DemoInvocationHandler(ITestedClasses testedClasses) {
            this.testedClasses = testedClasses;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            Method[] methodsAll = testedClasses.getClass().getDeclaredMethods();
            Arrays.stream(methodsAll).forEach(met -> {
                if (met.getName().equals(method.getName())
                        && Arrays.stream(met.getParameterTypes()).toList().equals(Arrays.stream(method.getParameterTypes()).toList())
                        && met.isAnnotationPresent(Log.class)) {

                    System.out.println("executed method: " + method.getName() + ", param: " + Arrays.stream(args).toList());
                }
            });

            return method.invoke(testedClasses, args);
        }

        @Override
        public String toString() {
            return "DemoInvocationHandler{" + "testedClass" + testedClasses + "}";
        }
    }
}
