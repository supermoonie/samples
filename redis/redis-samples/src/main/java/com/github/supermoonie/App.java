package com.github.supermoonie;

import com.github.supermoonie.annonation.SpiderInfo;
import org.reflections.Reflections;

import java.util.Set;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main(String[] args) {
        Reflections reflections = new Reflections();
        Set<Class<?>> typesAnnotatedWith = reflections.getTypesAnnotatedWith(SpiderInfo.class);
        for (Class<?> clazz : typesAnnotatedWith) {
            System.out.println(clazz.getName());
        }
    }
}
