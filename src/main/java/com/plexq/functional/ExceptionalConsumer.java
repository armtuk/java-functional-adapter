package com.plexq.functional;

import java.util.function.Consumer;

@FunctionalInterface
public interface ExceptionalConsumer<T> extends Consumer<T> {
    void apply(T t) throws Exception;
    default void accept(T t) {
        try {
            apply(t);
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
