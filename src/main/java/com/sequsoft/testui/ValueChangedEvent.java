package com.sequsoft.testui;

import org.springframework.context.ApplicationEvent;

import java.util.function.Function;

public class ValueChangedEvent extends ApplicationEvent {

    private final String valueChangeId;
    private final Function ValueRetrieverFn;

    public ValueChangedEvent(Object source, String valueChangeId, Function valueRetrieverFn) {
        super(source);
        this.valueChangeId = valueChangeId;
        this.ValueRetrieverFn = valueRetrieverFn;
    }

    public String getValueChangeId() {
        return valueChangeId;
    }

    public Function getValueRetrieverFn() {
        return ValueRetrieverFn;
    }
}
