package com.sequsoft.testui;

import org.springframework.context.ApplicationEvent;

import java.util.function.Function;

public class ValueChangedEvent extends ApplicationEvent {

    private final String valueChangeId;
    private final Function valueRetrieverFn;

    public ValueChangedEvent(Object source, String valueChangeId, Function valueRetrieverFn) {
        super(source);
        this.valueChangeId = valueChangeId;
        this.valueRetrieverFn = valueRetrieverFn;
    }

    public String getValueChangeId() {
        return valueChangeId;
    }

    public <T> T retrieveValue() {
        return (T)valueRetrieverFn.apply(source);
    }
}
