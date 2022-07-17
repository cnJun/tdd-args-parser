package com.sdk4.args;

public class IllegalValueException extends RuntimeException {
    private String option;
    private String value;

    public IllegalValueException(String option, String value) {
        this.option = option;
        this.value = value;
    }
}
