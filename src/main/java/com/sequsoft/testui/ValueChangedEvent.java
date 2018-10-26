package com.sequsoft.testui;

import com.sequsoft.testui.settings.TickSource;
import org.springframework.context.ApplicationEvent;

import java.util.function.Function;

public class ValueChangedEvent extends ApplicationEvent {

    private final String valueChangeId;
    private final Function<Object, Boolean> fn;

    public ValueChangedEvent(Object source, String valueChangeId, Function<Object, Boolean> fn ) {
        super(source);
        this.valueChangeId = valueChangeId;
        this.fn = fn;
    }

    public String getValueChangeId() {
        return valueChangeId;
    }

    public Function<Object, Boolean> getFn() {
        return fn;
    }
}
