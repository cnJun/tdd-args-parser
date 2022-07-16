package com.sdk4.args;

import java.util.List;

interface OptionParser {
    Object parse(List<String> arguments, Option option);
}
