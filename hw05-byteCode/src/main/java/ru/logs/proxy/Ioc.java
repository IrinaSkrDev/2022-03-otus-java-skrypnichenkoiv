package ru.logs.proxy;

import ru.logs.annotation.Log;
import ru.logs.testedClass.TestedClasses;
import ru.logs.testedClass.TestedClassImpl;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;

public class Ioc {
    private Ioc() {
    }

    public static TestedClasses createItestedClasses() {
        InvocationHandler invocationHandler = new DemoInvocationHandler(new TestedClassImpl());
        return (TestedClasses) Proxy.newProxyInstance(Ioc.class.getClassLoader(), new Class<?>[]{TestedClasses.class}, invocationHandler);
    }

    static class DemoInvocationHandler implements InvocationHandler {
        private final TestedClasses testedClasses;
        private final Method[] methodsAll ;
        DemoInvocationHandler(TestedClasses testedClasses) {
            this.testedClasses = testedClasses;
            this.methodsAll = this.testedClasses.getClass().getDeclaredMethods();
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
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
