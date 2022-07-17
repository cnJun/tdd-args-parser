package com.sdk4.args;

import java.util.List;

import static com.sdk4.args.SingleValuedOptionParser.values;

class BooleanOptionParser implements OptionParser<Boolean> {
    @Override
    public Boolean parse(List<String> arguments, Option option) {
        return values(arguments, option, 0)
                .map(it -> true)
                .orElse(false);
    }
}
