package com.sdk4.args;

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
}