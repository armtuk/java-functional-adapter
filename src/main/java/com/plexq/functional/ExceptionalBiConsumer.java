package com.plexq.functional;

import java.util.function.BiConsumer;

@FunctionalInterface
public interface ExceptionalBiConsumer<T,U> extends BiConsumer<T,U> {
    void apply(T t, U u) throws Exception;
    default void accept(T t, U u) {
        try {
            apply(t,u);
        }
        catch (Throwable e) {
            if (e instanceof RuntimeException) {
                throw (RuntimeException)e;
            }
            else {
                throw new RuntimeException(e);
            }
        }
    }
}