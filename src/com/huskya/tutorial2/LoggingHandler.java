package com.huskya.tutorial2;

import com.huskya.tutorial2.annotations.Loggable;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class LoggingHandler implements InvocationHandler {
    private final Object target;

    public LoggingHandler(Object target) {
        this.target = target;
    }

    private static boolean hasAnnotation(Method method, Class<? extends Annotation> annotationType, Object target) {
        if (method.isAnnotationPresent(annotationType)) {
            return true;
        }

        try {
            Method targetMethod = target.getClass().getMethod(method.getName(), method.getParameterTypes());
            if (targetMethod.isAnnotationPresent(annotationType)) {
                return  true;
            }
        } catch (NoSuchMethodException e) {
            // ignore
        }

        return false;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        boolean hasAnnotation = hasAnnotation(method, Loggable.class, target);
        if (hasAnnotation) {
            System.out.println("before");
            Object result = method.invoke(target, args);
            System.out.println("after");

            return result;
        } else {
            return method.invoke(target, args);
        }
    }
}

// @Before("@annotation(Loggable)")
