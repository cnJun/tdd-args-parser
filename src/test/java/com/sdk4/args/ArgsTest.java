package com.sdk4.args;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ArgsTest {
    @Test
    void should_set_boolean_option_to_return_true_if_flag_present() {
        BooleanOption option = Args.parse(BooleanOption.class, "-l");

        assertTrue(option.logging);
    }

    @Test
    void should_set_boolean_option_to_return_false_if_flag_not_present() {
        BooleanOption option = Args.parse(BooleanOption.class);

        assertFalse(option.logging);
    }

    public static class BooleanOption {
        boolean logging;

        public BooleanOption(@Option("l") boolean logging) {
            this.logging = logging;
        }
    }

    @Test
    void should_parse_int_as_option_value() {
        IntOption option = Args.parse(IntOption.class, "-p", "8080");
        assertEquals(8080, option.port);
    }

    public static class IntOption {
        public int port;

        public IntOption(@Option("p") int port) {
            this.port = port;
        }
    }

    @Test
    void should_parse_string_as_option_value() {
        StringOption option = Args.parse(StringOption.class, "-d", "/usr/logs");
        assertEquals("/usr/logs", option.directory);
    }

    public static class StringOption {
        public String directory;

        public StringOption(@Option("d") String directory) {
            this.directory = directory;
        }
    }

    @Test
    void should_parse_multi_options() {
        MultiOptions options = Args.parse(MultiOptions.class, "-l", "-p", "8080", "-d", "/usr/logs");
        assertTrue(options.logging);
        assertEquals(8080, options.port);
        assertEquals("/usr/logs", options.directory);
    }

    public static class MultiOptions {
        public boolean logging;
        public int port;
        public String directory;

        public MultiOptions(@Option("l") boolean logging, @Option("p") int port, @Option("d") String directory) {
            this.logging = logging;
            this.port = port;
            this.directory = directory;
        }
    }
}