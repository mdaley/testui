package com.sequsoft.testui.utils;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class UtilsTest {

    public static class B {

        String c;
        int d;

        public B() {}

        public B(String c, int d) {
            this.c = c;
            this.d = d;
        }

        public String getC() {
            return c;
        }

        public void setC(String c) {
            this.c = c;
        }

        public int getD() {
            return d;
        }

        public void setD(int d) {
            this.d = d;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            B b = (B) o;
            return d == b.d &&
                    Objects.equals(c, b.c);
        }

        @Override
        public int hashCode() {
            return Objects.hash(c, d);
        }
    }

    public static class SomeYaml {
        String a;
        List<B> b;

        public SomeYaml() {}

        public SomeYaml(String a, List<B> b) {
            this.a = a;
            this.b = b;
        }

        public String getA() {
            return a;
        }

        public void setA(String a) {
            this.a = a;
        }

        public List<B> getB() {
            return b;
        }

        public void setB(List<B> bs) {
            this.b = bs;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            SomeYaml someYaml = (SomeYaml) o;
            return Objects.equals(a, someYaml.a) &&
                    Objects.equals(b, someYaml.b);
        }

        @Override
        public int hashCode() {
            return Objects.hash(a, b);
        }
    }

    @Test
    public void yaml_can_be_loaded_from_resource() {
        SomeYaml someYaml = Utils.loadYaml("/some.yml", SomeYaml.class);
        assertThat(someYaml, equalTo(new SomeYaml("aa", Arrays.asList(new B("cc", 1), new B("ccc", 2), new B("cccc", 3)))));
    }
}
