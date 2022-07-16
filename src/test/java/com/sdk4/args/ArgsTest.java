package com.sdk4.args;

import org.junit.jupiter.api.Test;

public class ArgsTest {
    // -l -p 8080 -d /usr/logs
    // [-l], [-p, 8080], [-d, /usr/logs]
    // {-l:[], -p:[8080], -d:[/usr/logs]}
    @Test
    void should() {
        Arguments args = Args.parse("l:b,p:d,d:s", "-l", "-p", "8080", "-d", "/usr/logs");
        args.getBool("l");
        args.getInt("p");

        Options options = Args.parse(Option.class, "-l", "-p", "8080", "-d", "/usr/logs");
        options.logging();
        options.port();
    }

    static class Options() {
        boolean logging;
        int port;
        String directory;

        public Options(@Option("l") boolean logging, @Option("p") int port, @Option("d") String directory) {
            this.logging = logging;
            this.port = port;
            this.directory = directory;
        }
    }

}
