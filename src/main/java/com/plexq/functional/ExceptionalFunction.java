package com.plexq.functional;

import java.util.function.Function;

@FunctionalInterface
public interface ExceptionalFunction<A, B> extends Function<A, B> {
    B f(A a) throws Exception;
    default B apply(A a) {
        try { return f(a); }
        catch (Exception e) { throw new RuntimeException(e); }
    }
}
