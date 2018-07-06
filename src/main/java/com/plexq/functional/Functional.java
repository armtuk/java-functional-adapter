package com.plexq.functional;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import com.plexq.functional.Tuple.Tuple2;

/**
 * Glue code for functional extensions.  Use this class with great care.  This will box and unbox lists to Streams
 * and back again.  DO NOT compose calls using these wrappers, use these wrappers only at the top most level, and compose
 * rather the functions passed in to them.
 */
public final class Functional {
    public static <A, B> Function<A, B> exceptional(ExceptionalFunction<A, B> f) {
        return f;
    }

    public static <A> Consumer<A> exceptionalC(ExceptionalConsumer<A> f) {
        return f;
    }

    public static <A,B> BiConsumer<A,B> exceptionalBiC(ExceptionalBiConsumer<A,B> f) {
        return f;
    }

    public static <A> Supplier<A> exceptionalS(ExceptionalSupplier<A> f) {
        return f;
    }

    public static Runnable exceptionalR(ExceptionalRunnable f) {
        return f;
    }

    /**
     * Seperate the stream and collect concern from interaction with map function.
     * Be aware that if you chain this function itself, you'll be generating a new stream and collector for each collection.
     * Prefer instead functional composition in the function parameter
     * @param v a List object
     * @param f a function to transform each element of the list
     * @param <A> Parameterized type of the input list
     * @param <B> Parameterized type of the output of the function, and therefore also the output List
     * @return
     */
    public static <A, B> List<B> map(List<A> v, Function<A, B> f) {
        return v.stream().map(f).collect(Collectors.toList());
    }

    public static <A> void forEach(List<A> v, Consumer<A> f) {
        v.stream().forEach(f);
    }

    public static <A, B> List<B> flatMap(Optional<A> v, Function<A, List<B>> f) {
        return v.map(x -> f.apply(x).stream()).orElseGet(Stream::empty).collect(Collectors.toList());
    }

    public static <A, B> Stream<B> flatMap(Stream<A> s, Function<A, Optional<B>> f) {
        return s.flatMap(f.andThen(x -> x.map(Stream::of).orElseGet(Stream::empty)));
    }

    public static <A, B> List<B> flatMap(List<A> v, Function<A, Optional<B>> f) {
        return v.stream().flatMap(f.andThen(x -> x.map(Stream::of).orElseGet(Stream::empty))).collect(
                Collectors.toList());
    }

    public static <A> List<A> flatten(List<Optional<A>> a) {
        return a.stream().filter(Optional::isPresent).map(Optional::get).collect(Collectors.toList());
    }

    public static <A> Optional<A> find(List<A> v, Predicate<A> predicate) {
        return v.stream().filter(predicate).findAny();
    }

    public static <A> List<A> filter(List<A> v, Predicate<A> p) {
        return v.stream().filter(p).collect(Collectors.toList());
    }

    public static <A, B> List<B> eMap(List<A> v, ExceptionalFunction<A, B> f) {
        return map(v, f);
    }

    public static <A, B> List<B> eFlatMap(List<A> v, ExceptionalFunction<A, List<B>> f) {
        return v.stream().flatMap(x -> f.apply(x).stream()).collect(Collectors.toList());
    }

    // Cast an ExceptionalPredicate down to a regular predicate
    public static <A> List<A> eFilter(List<A> v, ExceptionalPredicate<A> p) {
        return filter(v, p);
    }

    // Cast an ExceptionalPredicate down to a regular predicate
    public static <A> Optional<A> eFind(List<A> v, ExceptionalPredicate<A> predicate) {
        return find(v, predicate);
    }

    public static <A> Stream<A> toStream(Optional<A> o) {
        return o.map(Stream::of).orElseGet(Stream::empty);
    }

    public static <A> Optional<A> headOption(List<A> v) {
        return v.isEmpty() ? Optional.empty() : Optional.of(v.get(0));
    }

    public static <A> Optional<A> orElse(Optional<A> opt, Supplier<Optional<A>> supplier) {
        if (opt.isPresent()) return opt;
        else return supplier.get();
    }

    public static <A> Stream<Tuple2<Integer, A>> zipWithIndex(List<A> a) {
        return IntStream.range(0, a.size()).boxed().map(x -> Tuple.tuple(x, a.get(x)));
    }
}
