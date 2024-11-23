package com.huskya.tutorial1;

import com.huskya.tutorial1.annotations.Component;
import com.huskya.tutorial1.annotations.Inject;
import com.huskya.tutorial1.annotations.PostConstruct;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class ApplicationContext {
    private final Map<Class<?>, Object> beanMap = new HashMap<>();

    public ApplicationContext(String packageToScan) throws Exception {
        scanPackage(packageToScan);

        for (Object bean : beanMap.values()) {
            injectDependencies(bean);
        }

        for (Object bean : beanMap.values()) {
            invokePostConstruct(bean);
        }
    }

    private void scanPackage(String packageName) throws Exception {
        String path = packageName.replace('.', '/');
        URL root = Thread.currentThread().getContextClassLoader().getResource(path);

        if (root == null) {
            throw new RuntimeException("No package found at path: " + path);
        }

        File directory = new File(root.toURI());
        if (directory.exists()) {
            scanDirectory(directory, packageName);
        }
    }

    private void scanDirectory(File directory, String packageName) throws Exception {
        for (File file : directory.listFiles()) {
            if(file.isDirectory()) {
                scanDirectory(file, packageName + "." + file.getName());
            } else if (file.getName().endsWith(".class")) {
                String className = packageName + '.' + file.getName().replace(".class", "");
                Class<?> clazz = Class.forName(className);

                if(clazz.isAnnotationPresent(Component.class)) {
                    Object instance = clazz.getDeclaredConstructor().newInstance();
                    beanMap.put(clazz, instance);
                }
            }
        }
    }

    private void injectDependencies(Object bean) throws IllegalAccessException {
        for (Field field : bean.getClass().getDeclaredFields()) {
            if(field.isAnnotationPresent(Inject.class)) {
                field.setAccessible(true);
                Object dependency = beanMap.get(field.getType());
                if (dependency != null) {
                    field.set(bean, dependency);
                }
            }
        }
    }

    private void invokePostConstruct(Object bean) throws Exception {
        for (Method method : bean.getClass().getDeclaredMethods()) {
            if (method.isAnnotationPresent(PostConstruct.class)) {
                method.setAccessible(true);
                method.invoke(bean);
            }
        }
    }

    public <T> T getBean(Class<T> clazz) {
        return clazz.cast(beanMap.get(clazz));
    }
}
