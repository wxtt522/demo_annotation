package com.example.util;

/**
 * @Description:
 * @Author: wulh
 * @Date: 2020/8/7 15:45
 */

import java.lang.reflect.Field;

/**
 * description: TODO
// 作为反射工具类，通过对象和属性的名字获取对象属性的值，如果在当前对象属性没有找到，依次向上收集所有父类的属
// 性，直到找到属性值，没有找到返回null；
 * @author : Administrator
 * @since : 1.0
 **/
public class ClassUtil {

    public static Object getPropertyValue(Object obj, String propertyName) throws IllegalAccessException {
        Class<?> Clazz = obj.getClass();
        Field field;
        if ((field = getField(Clazz, propertyName)) == null)
            return null;
        field.setAccessible(true);
        return field.get(obj);
    }

    public static Field getField(Class<?> clazz, String propertyName) {
        if (clazz == null)
            return null;
        try {
            return clazz.getDeclaredField(propertyName);
        } catch (NoSuchFieldException e) {
            return getField(clazz.getSuperclass(), propertyName);
        }
    }
}