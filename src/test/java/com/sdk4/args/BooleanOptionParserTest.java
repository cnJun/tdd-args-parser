package com.sdk4.args;

import org.junit.jupiter.api.Test;

import java.lang.annotation.Annotation;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

public class BooleanOptionParserTest {

    @Test
    public void should_not_accept_extra_argument_for_boolean_option() {
        TooManyArgumentsException e = assertThrows(TooManyArgumentsException.class,
                () -> new BooleanOptionParser().parse(Arrays.asList("-l", "t"), option("l")));

        assertEquals("l", e.getOption());
    }

    @Test
    public void should_not_accept_extra_arguments_for_boolean_option() {
        TooManyArgumentsException e = assertThrows(TooManyArgumentsException.class,
                () -> new BooleanOptionParser().parse(Arrays.asList("-l", "t", "f"), option("l")));

        assertEquals("l", e.getOption());
    }

    @Test
    void should_set_default_value_to_false_if_option_not_present() {
        assertFalse(new BooleanOptionParser().parse(Arrays.asList(), option("l")));
    }

    @Test
    void should_set_value_to_true_if_option_present() {
        assertTrue(new BooleanOptionParser().parse(Arrays.asList("-l"), option("l")));
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
