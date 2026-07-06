package io.harness.jhttp;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class LambdaUtilsTest {

    @Test
    public void curryBindsFirstArgument() {
        BiFunction<String, String, String> joinWithDash = (left, right) -> left + "-" + right;

        Function<String, String> prefixedWithHarness = LambdaUtils.curry(joinWithDash, "harness");

        assertEquals("harness-ci", prefixedWithHarness.apply("ci"));
    }

    @Test
    public void curryWorksWithNumbers() {
        BiFunction<Integer, Integer, Integer> multiply = (left, right) -> left * right;

        Function<Integer, Integer> doubleIt = LambdaUtils.curry(multiply, 2);

        assertEquals(Integer.valueOf(10), doubleIt.apply(5));
    }

    @Test
    public void takeWhileKeepsValuesUntilPredicateFails() {
        List<Integer> result = LambdaUtils
                .takeWhile(Stream.of(1, 2, 3, 4, 1), number -> number < 4)
                .collect(Collectors.toList());

        assertEquals(Arrays.asList(1, 2, 3), result);
    }

    @Test
    public void takeWhileReturnsEmptyStreamWhenFirstValueFails() {
        List<Integer> result = LambdaUtils
                .takeWhile(Stream.of(5, 1, 2, 3), number -> number < 4)
                .collect(Collectors.toList());

        assertTrue(result.isEmpty());
    }

    @Test
    public void takeWhileHandlesEmptyStream() {
        List<Integer> result = LambdaUtils
                .takeWhile(Stream.<Integer>empty(), number -> number < 4)
                .collect(Collectors.toList());

        assertTrue(result.isEmpty());
    }
}