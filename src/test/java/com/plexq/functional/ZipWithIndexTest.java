package com.plexq.functional;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Test;
import static com.plexq.functional.Functional.*;

import com.plexq.functional.Tuple.Tuple2;
import static org.junit.Assert.*;

public class ZipWithIndexTest {

    @Test
    public void testZipWithIndexForEmptyShouldYieldEmptyList() {
        List<String> l = Collections.emptyList();

        List<Tuple2<Integer, String>> r = zipWithIndex(l).collect(Collectors.toList());

        assertEquals(0, r.size());
    }

    @Test
    public void testZipWithIndexForOneElementList() {
        List<String> l = Arrays.asList("Value");

        List<Tuple2<Integer, String>> r = zipWithIndex(l).collect(Collectors.toList());

        assertEquals(1, r.size());

        assertEquals(Tuple.tuple(0, "Value"), r.get(0));
    }

    @Test
    public void testZipWithIndexForMultiElementList() {
        List<String> l = Arrays.asList("Alpha", "Beta", "Gamma");

        List<Tuple2<Integer, String>> r = zipWithIndex(l).collect(Collectors.toList());

        assertEquals(3, r.size());

        assertEquals(Tuple.tuple(0, "Alpha"), r.get(0));
        assertEquals(Tuple.tuple(1, "Beta"), r.get(1));
        assertEquals(Tuple.tuple(2, "Gamma"), r.get(2));
    }
}
