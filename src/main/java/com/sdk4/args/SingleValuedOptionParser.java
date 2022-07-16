package com.sdk4.args;

import java.util.List;
import java.util.function.Function;

class SingleValuedOptionParser<T> implements OptionParser<T> {
    Function<String, T> valueParser;
    T defaultValue;

    public SingleValuedOptionParser(T defaultValue, Function<String, T> valueParser) {
        this.defaultValue = defaultValue;
        this.valueParser = valueParser;
    }

    @Override
    public T parse(List<String> arguments, Option option) {
        int index = arguments.indexOf("-" + option.value());
        if (index == -1) {
            return defaultValue;
        }

        if (isReachEndOfList(arguments, index)
                || isFollowedByOtherFlag(arguments, index)) {
            throw new InsufficientArgumentsException(option.value());
        }
        if (secondArgumentIsNotAFlag(arguments, index)) {
            throw new TooManyArgumentsException(option.value());
        }
        String value = arguments.get(index + 1);
        return valueParser.apply(value);
    }

    private boolean secondArgumentIsNotAFlag(List<String> arguments, int index) {
        return index + 2 < arguments.size() && !arguments.get(index + 2).startsWith("-");
    }

    private boolean isFollowedByOtherFlag(List<String> arguments, int index) {
        return arguments.get(index + 1).startsWith("-");
    }

    private boolean isReachEndOfList(List<String> arguments, int index) {
        return index + 1 == arguments.size();
    }

}
