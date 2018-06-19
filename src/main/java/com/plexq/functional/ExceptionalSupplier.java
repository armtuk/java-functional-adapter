package com.plexq.functional;

import java.util.function.Consumer;
import java.util.function.Supplier;

@FunctionalInterface
public interface ExceptionalSupplier<T> extends Supplier<T> {
    T apply() throws Exception;
    default T get() {
        try {
            return apply();
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
