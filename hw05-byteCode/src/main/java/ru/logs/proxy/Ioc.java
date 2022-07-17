package ru.logs.proxy;

import ru.logs.annotation.Log;
import ru.logs.testedClass.TestedClasses;
import ru.logs.testedClass.TestedClassImpl;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Ioc {
    private Ioc() {
    }

    public static TestedClasses createItestedClasses() {
        InvocationHandler invocationHandler = new DemoInvocationHandler(new TestedClassImpl());
        return (TestedClasses) Proxy.newProxyInstance(Ioc.class.getClassLoader(), new Class<?>[]{TestedClasses.class}, invocationHandler);
    }

    static class DemoInvocationHandler implements InvocationHandler {
        private final TestedClasses testedClasses;
        private final Method[] methodsAll;

        DemoInvocationHandler(TestedClasses testedClasses) {
            this.testedClasses = testedClasses;
            List<Method> listM = Arrays.stream(this.testedClasses.getClass()
                    .getDeclaredMethods()).filter(met -> {
                        return met.isAnnotationPresent(Log.class);
                    }
            ).collect(Collectors.toList());
            methodsAll = new Method[listM.size()];
            listM.toArray(this.methodsAll);
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            Arrays.stream(methodsAll).forEach(met -> {
                if (met.getName().equals(method.getName())
                        && Arrays.stream(met.getParameterTypes()).toList().equals(Arrays.stream(method.getParameterTypes()).toList())
                ) {
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
