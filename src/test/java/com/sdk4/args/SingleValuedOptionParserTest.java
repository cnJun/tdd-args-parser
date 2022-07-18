package com.sdk4.args;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.*;

public class SingleValuedOptionParserTest {

    @Nested
    class UnaryOptionParser {
        @Test
        void should_not_accept_extra_argument_for_single_valued_option() {
            TooManyArgumentsException e = assertThrows(TooManyArgumentsException.class, () -> {
                OptionParsers.unary(0, Integer::parseInt).parse(Arrays.asList("-p", "8080", "8081"), option("p"));
            });

            assertEquals("p", e.getOption());
        }

        // sad path
        @ParameterizedTest
        @ValueSource(strings = {"-p -l", "-p"})
        void should_not_accept_insufficient_arguments_for_single_valued_option(String arguments) {
            InsufficientArgumentsException e = assertThrows(InsufficientArgumentsException.class, () -> {
                OptionParsers.unary(0, Integer::parseInt).parse(Arrays.asList(arguments.split(" ")), option("p"));
            });

            assertEquals("p", e.getOption());
        }

        // default value
        @Test
        void should_set_default_value_for_option() {
            Function<String, Object> whatever = (it) -> null;
            Object defaultValue = new Object();
            assertSame(defaultValue, OptionParsers.unary(defaultValue, whatever).parse(Arrays.asList(), option("p")));
        }

        @Test
        void should_not_accept_extra_argument_for_string_single_valued_option() {
            TooManyArgumentsException e = assertThrows(TooManyArgumentsException.class, () -> {
                OptionParsers.unary("", String::valueOf).parse(Arrays.asList("-d", "/usr/logs", "/usr/vars"), option("d"));
            });

            assertEquals("d", e.getOption());
        }

        // happy path
        @Test
        void should_parse_value_if_flag_present() {
            Object parsed = new Object();
            Function<String, Object> parse = (it) -> parsed;
            Object whatever = new Object();

            assertSame(parsed, OptionParsers.unary(whatever, parse).parse(Arrays.asList("-p", "8080"), option("p")));
        }
    }

    @Nested
    class BoolOptionTest {
        @Test
        public void should_not_accept_extra_argument_for_boolean_option() {
            TooManyArgumentsException e = assertThrows(TooManyArgumentsException.class,
                    () -> OptionParsers.bool().parse(Arrays.asList("-l", "t"), option("l")));

            assertEquals("l", e.getOption());
        }

        // sad path
        @Test
        public void should_not_accept_extra_arguments_for_boolean_option() {
            TooManyArgumentsException e = assertThrows(TooManyArgumentsException.class,
                    () -> OptionParsers.bool().parse(Arrays.asList("-l", "t", "f"), option("l")));

            assertEquals("l", e.getOption());
        }

        // sad path: default value
        @Test
        void should_set_default_value_to_false_if_option_not_present() {
            assertFalse(OptionParsers.bool().parse(Arrays.asList(), option("l")));
        }

        // happy path
        @Test
        void should_set_value_to_true_if_option_present() {
            assertTrue(OptionParsers.bool().parse(Arrays.asList("-l"), option("l")));
        }
    }

    static Option option(String value) {
        return new Option() {

            @Override
            public Class<? extends Annotation> annotationType() {
                return Option.class;
            }

            @Override
            public String value() {
                return value;
            }
        };
    }

}
