package com.sdk4.args;

import java.util.List;
import java.util.function.Function;

class SingleValuedOptionParser<T> implements OptionParser<T> {
    Function<String, T> valueParser;

    public SingleValuedOptionParser(Function<String, T> valueParser) {
        this.valueParser = valueParser;
    }

    @Override
    public T parse(List<String> arguments, Option option) {
        int index = arguments.indexOf("-" + option.value());
        String value = arguments.get(index + 1);
        return parseValue(value);
    }

    protected T parseValue(String value) {
        return valueParser.apply(value);
    }
}
