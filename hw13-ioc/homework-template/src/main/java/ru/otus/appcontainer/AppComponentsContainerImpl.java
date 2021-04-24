package ru.otus.appcontainer;

import ru.otus.appcontainer.api.AppComponent;
import ru.otus.appcontainer.api.AppComponentsContainer;
import ru.otus.appcontainer.api.AppComponentsContainerConfig;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;

public class AppComponentsContainerImpl implements AppComponentsContainer {

    public static final Class<AppComponent> APP_COMPONENT_CLASS = AppComponent.class;
    private final List<Object> appComponents = new ArrayList<>();
    private final Map<String, Object> appComponentsByName = new HashMap<>();

    public AppComponentsContainerImpl(Class<?> initialConfigClass) throws InvocationTargetException, IllegalAccessException, InstantiationException, NoSuchMethodException {
        processConfig(initialConfigClass);
    }

    @Override
    public <C> C getAppComponent(Class<C> componentClass) {
        return (C) appComponents.get(appComponents.size() - 1);
    }

    @Override
    public <C> C getAppComponent(String componentName) {
        return (C) appComponentsByName.get(componentName);
    }

    private void processConfig(Class<?> configClass) throws InvocationTargetException, IllegalAccessException, InstantiationException, NoSuchMethodException {
        checkConfigClass(configClass);

        Method[] methods = configClass.getMethods();
        var appMethods = Arrays.stream(methods)
                .filter(method -> method.isAnnotationPresent(APP_COMPONENT_CLASS))
                .sorted(Comparator.comparingInt(o -> o.getAnnotation(APP_COMPONENT_CLASS).order()))
                .collect(Collectors.toList());

        var configClassInstance = configClass.getConstructor().newInstance();
        for (var appMethod : appMethods) {
            Object[] params = getInvokingParams(appMethod);
            var appComponent = appMethod.invoke(configClassInstance, params);
            appComponents.add(appComponent);
            appComponentsByName.put(appMethod.getName(), appComponent);
        }
    }

    private Object[] getInvokingParams(Method appMethod) {
        var appMethodParams = appMethod.getParameters();
        Object[] params = new Object[appMethodParams.length];
        for (var i = 0; i < appMethodParams.length; i++) {
            // params[i] = appComponentsByName.get(appMethodParams[i].getName());
            // по именам параметров не получилось, попробуем по типу
            var requiredType = appMethodParams[i].getType();
            var foundComponent = appComponents.stream()
                    .filter(o -> o.getClass().equals(requiredType))
                    .findFirst();
            if (foundComponent.isEmpty()) {
                // не нашли - пробуем по интерфейсу
                foundComponent = appComponents.stream()
                        .filter(o -> Arrays.stream(o.getClass()
                                .getInterfaces())
                                .findFirst()
                                .get()
                                .equals(requiredType))
                        .findFirst();
            }
            params[i] = foundComponent.get();
        }
        return params;
    }

    private void checkConfigClass(Class<?> configClass) {
        if (!configClass.isAnnotationPresent(AppComponentsContainerConfig.class)) {
            throw new IllegalArgumentException(String.format("Given class is not config %s", configClass.getName()));
        }
    }
}
