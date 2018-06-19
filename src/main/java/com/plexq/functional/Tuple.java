package com.plexq.functional;

import java.util.Objects;

public final class Tuple {
    public static final class Tuple2<A, B> {
        public final A _1;
        public final B _2;

        public Tuple2(A a, B b) {
            this._1 = a;
            this._2 = b;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            Tuple2<?, ?> tuple2 = (Tuple2<?, ?>) o;
            return Objects.equals(_1, tuple2._1) && Objects.equals(_2, tuple2._2);
        }

        @Override
        public int hashCode() {

            return Objects.hash(_1, _2);
        }
    }

    public static final class Tuple3<A, B, C> {
        public final A _1;
        public final B _2;
        public final C _3;

        public Tuple3(A a, B b, C c) {
            this._1 = a;
            this._2 = b;
            this._3 = c;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            Tuple3<?, ?, ?> tuple3 = (Tuple3<?, ?, ?>) o;
            return Objects.equals(_1, tuple3._1) && Objects.equals(_2, tuple3._2) && Objects.equals(_3, tuple3._3);
        }

        @Override
        public int hashCode() {

            return Objects.hash(_1, _2, _3);
        }
    }

    public static <A, B> Tuple2<A, B> tuple(A a, B b) {
        return new Tuple.Tuple2<A, B>(a, b);
    }

    public static <A, B, C> Tuple3<A, B, C> tuple(A a, B b, C c) {
        return new Tuple.Tuple3<A , B, C>(a, b, c);
    }
}
