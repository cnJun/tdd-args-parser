package com.sdk4.args;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ArgsTest {

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

    @Test
    void should_throw_illegal_option_exception_if_annotation_not_present() {
        IllegalOptionException e = assertThrows(IllegalOptionException.class, () -> Args.parse(OptionsWithoutAnnotation.class, "-l", "-p", "8080", "-d", "/usr/logs"));
        assertEquals("arg1", e.getParameter());
    }

    public static class OptionsWithoutAnnotation {
        boolean logging;
        int port;
        String directory;

        public OptionsWithoutAnnotation(@Option("l") boolean logging, int port, @Option("d") String directory) {
            this.logging = logging;
            this.port = port;
            this.directory = directory;
        }
    }

    @Test
    @Disabled
    void should_example_2() {
        ListOptions options = Args.parse(ListOptions.class, "-g", "this", "is", "a", "list", "-d", "1", "2", "-3", "5");

        assertArrayEquals(new String[]{"this", "is", "a", "list"}, options.group);
        assertArrayEquals(new int[]{1, 2, -3, 5}, options.decimals);
    }

    public static class ListOptions {
        public String[] group;
        public int[] decimals;

        public ListOptions(@Option("g") String[] group, @Option("d") int[] decimals) {
            this.group = group;
            this.decimals = decimals;
        }
    }
}