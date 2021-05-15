package com.bekiruzun.todoapp.common;


import org.jeasy.random.EasyRandom;
import org.jeasy.random.EasyRandomParameters;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class TestUtils {
    private static EasyRandom generator;

    public static EasyRandom getGenerator() {
        if(generator == null) {
            EasyRandomParameters parameters = new EasyRandomParameters();
            parameters.stringLengthRange(3, 10);
            parameters.collectionSizeRange(1, 3);
            generator = new EasyRandom(parameters);
        }
        return generator;
    }

    public static <T> T objectOf(Class<T> targetClass) {
        return getGenerator().nextObject(targetClass);
    }

    public static <T> Optional<T> optionalOf(Class<T> targetClass) {
        return Optional.of(objectOf(targetClass));
    }

    public static <T> List<T> listOf(Class<T> targetClass) {
        return Arrays.asList(objectOf(targetClass));
    }

}
