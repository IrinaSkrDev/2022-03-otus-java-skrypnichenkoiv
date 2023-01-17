package ru.otus.appcontainer;

import ru.otus.appcontainer.api.AppComponent;
import ru.otus.appcontainer.api.AppComponentsContainer;
import ru.otus.appcontainer.api.AppComponentsContainerConfig;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;

public class AppComponentsContainerImpl implements AppComponentsContainer {

    private final List<Object> appComponents = new ArrayList<>();
    private final Map<String, Object> appComponentsByName = new HashMap<>();

    public AppComponentsContainerImpl(Class<?> initialConfigClass) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        processConfig(initialConfigClass);
    }

    private void processConfig(Class<?> configClass) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        checkConfigClass(configClass);
        // You code here... isAssignableFrom
        TreeMap<String, Integer> mapMethods = new TreeMap<>();

        Arrays.stream(configClass.getDeclaredMethods()).forEach(method -> {
            method.setAccessible(true);
            if (method.isAnnotationPresent(AppComponent.class)) {
                Integer iii = method.getAnnotation(AppComponent.class).order();
                String ss = method.getName();
                mapMethods.put(ss, iii);
            }
        });

        List<Map.Entry<String, Integer>> mapMethodsByOrder = mapMethods.entrySet().stream()
                .sorted((e1, e2) -> -e2.getValue().compareTo(e1.getValue()))
                .collect(Collectors.toList());


        Constructor<?> constructorAppConfig = configClass.getConstructor();
        constructorAppConfig.newInstance();
        var appConfigInstance = constructorAppConfig.newInstance();
        // var appConfigInstance = constructorAppConfig.newInstance();

        for (Integer i = 0; i < mapMethodsByOrder.size(); i++) {
            List<Method> methodList = Arrays.stream(configClass.getDeclaredMethods()).toList();
            Integer finalI = i;
            Method methodToExecute = methodList.stream().filter(method -> mapMethodsByOrder.get(finalI).getKey().equals(method.getName())).findFirst().get();

            methodToExecute.setAccessible(true);
            Class<?>[] listOfMethodParameters = methodToExecute.getParameterTypes();
            if (listOfMethodParameters.length == 0) {

                Object createdObject = methodToExecute.invoke(appConfigInstance);
                appComponents.add(createdObject);
                appComponentsByName.put(methodToExecute.getAnnotation(AppComponent.class).name(), createdObject);
            } else {
                Object[] parameters = new Object[listOfMethodParameters.length];

                for (int ii = 0; ii < listOfMethodParameters.length; ii++) {
                    String paramName = listOfMethodParameters[ii].getSimpleName();
                    Object objParameter = getAppComponent(listOfMethodParameters[ii]);
                    parameters[ii] = objParameter;
                }

                Object createdObject = methodToExecute.invoke(appConfigInstance, parameters);
                appComponents.add(createdObject);
                appComponentsByName.put(methodToExecute.getAnnotation(AppComponent.class).name(), createdObject);
            }
        }

    }

    private void checkConfigClass(Class<?> configClass) {
        if (!configClass.isAnnotationPresent(AppComponentsContainerConfig.class)) {
            throw new IllegalArgumentException(String.format("Given class is not config %s", configClass.getName()));
        }
    }

    @Override
    public <C> C getAppComponent(Class<C> componentClass) {
        Object result =
                appComponents.stream().filter(obj ->
                        componentClass.isAssignableFrom(obj.getClass())
                ).findFirst().get();
        return (C) result;
    }

    @Override
    public <C> C getAppComponent(String componentName) {
        Object result = appComponentsByName.get(componentName);
        return (C) result;
    }
}
