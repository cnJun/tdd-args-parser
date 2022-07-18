package com.sdk4.args;

import com.google.common.collect.Maps;

import java.lang.reflect.Constructor;
import java.lang.reflect.Parameter;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Args {

    public static <T> T parse(Class<T> optionsClass, String... args) {
        Constructor<?> constructor = optionsClass.getDeclaredConstructors()[0];
        try {
            List<String> arguments = Arrays.stream(args).collect(Collectors.toList());
            Object[] values = Arrays.stream(constructor.getParameters())
                    .map(it -> parseOption(arguments, it))
                    .toArray();
            return (T) constructor.newInstance(values);
        } catch (IllegalOptionException e) {
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    private static Object parseOption(List<String> arguments, Parameter parameter) {
        if (!parameter.isAnnotationPresent(Option.class)) {
            throw new IllegalOptionException(parameter.getName());
        }

        return PARSERS.get(parameter.getType()).parse(arguments, parameter.getAnnotation(Option.class));
    }

    private static Map<Class<?>, OptionParser> PARSERS = Maps.newHashMap();

    static {
        PARSERS.put(boolean.class, OptionParsers.bool());
        PARSERS.put(int.class, OptionParsers.unary(0, Integer::parseInt));
        PARSERS.put(String.class, OptionParsers.unary("", String::valueOf));
        PARSERS.put(String[].class, OptionParsers.list(String[]::new, String::valueOf));
        PARSERS.put(Integer[].class, OptionParsers.list(Integer[]::new, Integer::parseInt));
    }

}