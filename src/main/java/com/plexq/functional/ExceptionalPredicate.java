package com.plexq.functional;

import java.util.function.Predicate;

@FunctionalInterface
public interface ExceptionalPredicate<T> extends Predicate<T> {
    boolean apply(T t) throws Exception;
    default boolean test(T t) {
        try {
            return apply(t);
        }
        catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }
}
