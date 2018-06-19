package com.plexq.functional;

import java.util.function.Supplier;

@FunctionalInterface
public interface ExceptionalRunnable extends Runnable {
    void apply() throws Exception;
    default void run() {
        try {
            apply();
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
