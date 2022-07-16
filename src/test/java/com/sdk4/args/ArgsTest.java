package com.sdk4.args;

import org.junit.jupiter.api.Test;

public class ArgsTest {

    @Test
    void should() {
        Arguments args = Args.parse("l:b,p:d,d:s", "-l", "-p", "8080", "-d", "/usr/logs");
        args.getBool("l");
        args.getInt("p");

        Options options = Args.parse(Option.class, "-l", "-p", "8080", "-d", "/usr/logs");
        options.logging();
        options.port();
    }

}
