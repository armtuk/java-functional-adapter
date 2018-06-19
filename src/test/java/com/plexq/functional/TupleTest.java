package com.plexq.functional;

import org.junit.Test;

import static org.junit.Assert.*;

public class TupleTest {
    @Test
    public void tupleCanBeInstantiatedForTwoHeterogenouslyTypedValues() {
        Tuple.Tuple2 r = Tuple.tuple("Value", 3);

        assertEquals("Value", r._1);
        assertEquals(3, r._2);
    }

    @Test
    public void tuple2ShouldBeEqualsToSameTuple2() {
        Tuple.Tuple2 a = Tuple.tuple("Value", 3);
        Tuple.Tuple2 b = Tuple.tuple("Value", 3);

        assertNotSame(a, b);
        assertEquals(a, b);
    }

    @Test
    public void tupleCanBeInstantiatedForThreeHeterogenouslyTypedValues() {
        Tuple.Tuple3 r = Tuple.tuple("Value", new Integer(3), new Long(4));

        assertEquals("Value", r._1);
        assertEquals(new Integer(3), r._2);
        assertEquals(new Long(4), r._3);
    }

    @Test
    public void tuple3ShouldBeEqualToSameTuple3() {
        Tuple.Tuple3 a = Tuple.tuple("Value", new Integer(3), new Long(4));
        Tuple.Tuple3 b = Tuple.tuple("Value", new Integer(3), new Long(4));

        assertNotSame(a, b);
        assertEquals(a, b);
    }
}
