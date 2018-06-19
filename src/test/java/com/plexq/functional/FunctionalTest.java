package com.plexq.functional;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import static java.util.function.Function.*;

import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.*;
import static com.plexq.functional.Functional.*;
import static org.hamcrest.Matchers.*;

public class FunctionalTest {

    @Rule
    public final ExpectedException exception = ExpectedException.none();

    private static List<String> emptyList = Collections.emptyList();
    private static List<String> oneList = Arrays.asList("Alpha");
    private static List<String> twoList = Arrays.asList("Alpha", "Beta");

    private static Optional<String> present = Optional.of("Value");
    private static Optional<String> empty = Optional.empty();

    private static Stream<String> emptyStream = Stream.empty();
    private static Stream<String> oneStream = Stream.of("Aleph");
    private static Stream<String> twoStream = Stream.of("Aleph", "Bet");

    private List<TestExceptionGenerator> oneException = Arrays.asList(new TestExceptionGenerator());

    class TestExceptionGenerator {
        public String getValueException() throws Exception {
            throw new IOException("Test Exception");
        }

        public List<String> getListException() throws Exception {
            throw new IOException("Test Exception");
        }

    }

    public static boolean filterException(TestExceptionGenerator v) throws Exception {
        throw new IOException("Test Exception");
    }

    @Test
    public void mapWithOneListShouldYieldAMappedList() {
        List<Integer> r = Functional.map(oneList, String::length);

        assertThat(r, is(Arrays.asList(5)));
    }

    @Test
    public void mapWithTwoListShouldYieldAMappedList() {
        List<Integer> r = Functional.map(twoList, String::length);

        assertThat(r, is(Arrays.asList(5, 4)));
    }

    @Test
    public void mapWithEmptyListShouldYieldEmptyList() {
        List<Integer> r = Functional.map(emptyList, String::length);

        assertThat(r, is(Arrays.asList()));
    }

    @Test
    public void forEachWithOneListShouldYieldAResult() {
        List<Integer> r = new ArrayList<>();

        Functional.forEach(oneList, x -> r.add(1));

        assertEquals(1, r.size());
    }

    @Test
    public void forEachWithTwoListShouldYieldAResult() {
        List<Integer> r = new ArrayList<>();

        Functional.forEach(twoList, x -> r.add(1));

        assertEquals(2, r.size());
    }

    //
    // FlatMaps and Flattens
    //

    @Test
    public void flatMapPresentAndListShouldYieldOneList() {
        List<String> r = flatMap(present, x -> Arrays.asList(x + "foo"));

        assertThat("Flat mapping a present value through a list generator should yield a List", r, is(Arrays.asList("Valuefoo")));
    }

    @Test
    public void flatMapEmptyAndListShouldYieldEmpty() {
        List<String> r = flatMap(empty, x -> Arrays.asList(x + "foo"));

        assertTrue("Flat mapping an empty Optional should yield an empty optional", r.isEmpty());
    }

    @Test
    public void flatMapPresentAndEmptyListShouldYieldEmptyList() {
        List<String> r = flatMap(present, x -> Collections.emptyList());

        assertTrue("Flat mapping a present value through an empty generator should yield an empty result", r.isEmpty());
    }

    @Test
    public void flatMapEmptyAndEmptyListShouldYieldEmptyList() {
        List<String> r = flatMap(empty, x -> Collections.emptyList());

        assertTrue("Mapping an empty value through an empty generator should yield an empty result", r.isEmpty());
    }

    @Test
    public void flatMapOneStreamThroughGeneratorShouldYieldOneStream() {
        Stream<String> r = flatMap(oneStream, x -> Optional.of(x + "foo"));

        assertThat(r.collect(Collectors.toList()), is(Arrays.asList("Alephfoo")));
    }

    @Test
    public void flatMapTwoStreamThroughGeneratorShouldYieldTwoStream() {
        Stream<String> r = flatMap(twoStream, x -> Optional.of(x + "foo"));

        assertThat(r.collect(Collectors.toList()), is(Arrays.asList("Alephfoo", "Betfoo")));
    }

    @Test
    public void flatMapOneListThroughGeneratorShouldYieldOneList() {
        List<String> r = flatMap(oneList, x -> Optional.of(x + "foo"));

        assertThat(r, is(Arrays.asList("Alphafoo")));
    }

    @Test
    public void flatMapTwoListThroughGeneratorShouldYieldTwoList() {
        List<String> r = flatMap(twoList, x -> Optional.of(x + "foo"));

        assertThat(r, is(Arrays.asList("Alphafoo", "Betafoo")));
    }

    @Test
    public void flatMapTwoListThroughNotPresentShouldYieldEmptyList() {
        List<String> r = flatMap(twoList, x -> Optional.empty());

        assertEquals(0, r.size());
    }

    @Test
    public void flattenWithPresentElememtsShouldYieldListOfSameSize() {
        List<Optional<String>> v = Arrays.asList(Optional.of("Alpha"), Optional.of("Beta"));

        List<String> r = Functional.flatten(v);

        assertThat("A flatten with Optional<T> elements should yield a List<T> with all the bare values", r, is(Arrays.asList("Alpha", "Beta")));
    }

    @Test
    public void flattenWithEmptyFirstListShouldYieldReducedList() {
        List<Optional<String>> v = Arrays.asList(Optional.empty(), Optional.of("Alpha"));

        List<String> r = Functional.flatten(v);

        assertThat("A flattened list with the first element empty should reduce", r, is(Arrays.asList("Alpha")));
    }

    @Test
    public void flattenWithEmptyLastListShouldYieldReducedList() {
        List<Optional<String>> v = Arrays.asList(Optional.of("Alpha"), Optional.empty());

        List<String> r = Functional.flatten(v);

        assertThat("A flattened list with the last element empty should reduce", r, is(Arrays.asList("Alpha")));
    }

    @Test
    public void flattenWithEmptyOptionalsShouldYieldEmptyList() {
        List<Optional<String>> v  = Arrays.asList(Optional.empty(), Optional.empty());

        List<String> r = Functional.flatten(v);

        assertTrue("A flattened list of all empty Optional<T> values should yield an empty list", r.isEmpty());
    }

    //
    // Filters and Finds
    //

    @Test
    public void findWithTwoListShouldLocateElement() {
        Optional<String> r = Functional.find(twoList, x -> x.equals("Beta"));

        assertTrue(r.isPresent());
        assertEquals("Beta", r.get());
    }

    @Test
    public void filterWithTwoListShouldLocateElements() {
        List<String> r = Functional.filter(twoList, x -> x.contains("e"));

        assertThat(r, is(Arrays.asList("Beta")));
    }

    //
    // Exceptionals
    //

    @Test
    public void eMapShouldCorrectlyRecastException() {
        exception.expect(RuntimeException.class);

        List<String> r = Functional.eMap(oneException, TestExceptionGenerator::getValueException);
    }

    @Test
    public void eFlatMapShouldCorrectlyRecastException() {
        exception.expect(RuntimeException.class);

        List<String> r = Functional.eFlatMap(oneException, TestExceptionGenerator::getListException);
    }

    @Test
    public void eFilterShouldCorrectlyRecastException() {
        exception.expect(RuntimeException.class);

        Functional.eFilter(oneException, x -> filterException(x));
    }

    @Test
    public void eFindShouldCorrectlyRecastException() {
        exception.expect(RuntimeException.class);

        Functional.eFind(oneException, x -> filterException(x));
    }

    @Test
    public void toStreamShouldConvertPresentToAOneStream() {
        List<String> r = Functional.toStream(present).collect(Collectors.toList());

        assertNotNull("Result should be a non null, one element list", r);
        assertEquals("Size of output list should be one", 1, r.size());
    }

    @Test
    public void headOptionOfEmptyListShouldReturnNone() {
        Optional<String> o = Functional.headOption(emptyList);

        assertNotNull("Head option should return non null optional", o);
        assertFalse("Head option of empty list should return a None", o.isPresent());
    }

    @Test
    public void headOptionOfOneListShouldReturnOne() {
        Optional<String> o = Functional.headOption(oneList);

        assertNotNull("Head option of one line should return an element", o);
        assertTrue("Head option should return presetn element of one line", o.isPresent());
        assertEquals("Head of one list should return Alpha", "Alpha", o.get());
    }

    @Test
    public void orElseOfNoneShouldReturnSupplierValue() {
        Optional<String> o = Functional.orElse(empty, () -> Optional.of("value"));

        assertNotNull("orElse of empty should return not null value from supplier", o);
        assertTrue("orElse of empty with supplier should return a present value", o.isPresent());
        assertEquals("orElse of empty with supplier should return supplier value", "value", o.get());
    }

    @Test
    public void orElseOfSomeShouldReturnSomeValue() {
        Optional<String> o = Functional.orElse(present, () -> Optional.of("x"));

        assertNotNull("OrElse of some value should return some of the original value", o);
        assertTrue("orElse of some value should return a present value", o.isPresent());
        assertEquals("orElse of some value should return some of value", "Value", o.get());
    }

    @Test(expected = RuntimeException.class)
    public void usingFunctionalExceptionalWrapperShouldRecastException() {
        Functional.exceptional(x -> new File("/notarealpathhere/foo.txt").createNewFile()).apply("nothing");
    }

    @Test(expected = RuntimeException.class)
    public void usingConsumerExceptionalWrapperShouldRecastException() {
        Functional.exceptionalC(x -> new File("/notarealpathhere/foo.txt").createNewFile()).accept("nothing");
    }

    @Test(expected = RuntimeException.class)
    public void usingSupplierExceptionalWrapperShouldRecastException() {
        Functional.exceptionalS(() -> new File("/notarealpathhere/foot.txt").createNewFile()).get();
    }

    @Test(expected = RuntimeException.class)
    public void usingRunnableExceptionalWrapperShouldRecastException() {
        Functional.exceptionalR(() -> new File("/notarealpathhere/foot.txt").createNewFile()).run();
    }

    @Test
    public void usingFunctionalCompositionShouldWork() {
        /*
         * Annoyingly java doesn't permit the obvious syntax: (x -> x + "x").andThen(x -> y + "y")
         */
        // You can do this
        Function<String, String> I = identity();
        String r1 = Functional.map(oneList, I.andThen(x -> x + "x").andThen(y -> y + "y")).get(0);

        assertEquals(r1, "Alphaxy");

        // Or inline
        String r2 = Functional.map(oneList, Function.<String>identity().andThen(x -> x + "x").andThen(y -> y + "y")).get(0);
        assertEquals(r2, "Alphaxy");

        // Or This
        Function<String, String> first = x -> x + "x";
        String r3 = Functional.map(oneList, first.andThen(y -> y + "y")).get(0);
        assertEquals(r3, "Alphaxy");

        // None of these are very pretty.  Thanks Obama.
    }
}
