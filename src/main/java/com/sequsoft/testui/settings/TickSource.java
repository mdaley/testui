package com.sequsoft.testui.settings;

import com.sequsoft.testui.ChangeValueEvent;
import com.sequsoft.testui.ValueChangedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.ConfigurableApplicationContext;

public class TickSource implements ApplicationListener<ChangeValueEvent> {
    private static final Logger LOGGER = LoggerFactory.getLogger(TickSource.class);

    private final ConfigurableApplicationContext ctx;

    private boolean ticked;

    public TickSource(ConfigurableApplicationContext ctx) {
        this.ctx = ctx;
        ctx.addApplicationListener(this);
    }

    public boolean isTicked() {
        return ticked;
    }

    public void setTicked(boolean ticked) {
        this.ticked = ticked;
        LOGGER.info("Publishing tick changed event");
        ctx.publishEvent(new ValueChangedEvent(this));
    }

    public void toggleTicked() {
        ticked = !ticked;
        LOGGER.info("Publishing tick toggled event");
        ctx.publishEvent(new ValueChangedEvent(this));
    }

    @Override
    public void onApplicationEvent(ChangeValueEvent changeValueEvent) {
        LOGGER.info(("Received changeValueEvent. Tick toggled!"));
        toggleTicked();
    }
}
