package tmp;

import com.huskya.tutorial1.annotations.Inject;
import com.huskya.tutorial1.annotations.PostConstruct;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class SimpleIOCContainer {
    private Map<Class<?>, Object> beanMap = new HashMap<>();

    // 注册 Bean
    public void registerBean(Class<?> clazz) throws Exception {
        if(beanMap.containsKey(clazz)) {
            return;
        }

        Object inst = clazz.getConstructor().newInstance();
        beanMap.put(clazz, inst);

        for (Field field : clazz.getDeclaredFields()) {
            if(field.isAnnotationPresent(Inject.class)) {
                field.setAccessible(true);
                Class<?> fieldType = field.getType();
                Object dependency = beanMap.get(fieldType);

                if(dependency == null) {
                    dependency = fieldType.getDeclaredConstructor().newInstance();
                    beanMap.put(fieldType, dependency);
                }

                field.set(inst, dependency);
            }
        }

        for (Method method : clazz.getDeclaredMethods()) {
            if (method.isAnnotationPresent(PostConstruct.class)) {
                method.setAccessible(true);
                method.invoke(inst);

                break;
            }
        }

    }

    public <T> T getBean(Class<T> clazz) {
        return clazz.cast(beanMap.get(clazz));
    }
}
