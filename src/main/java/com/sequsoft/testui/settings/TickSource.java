package com.sequsoft.testui.settings;

import com.sequsoft.testui.ChangeValueEvent;
import com.sequsoft.testui.ValueChangedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.function.Function;

public class TickSource implements ApplicationListener<ChangeValueEvent> {
    private static final Logger LOGGER = LoggerFactory.getLogger(TickSource.class);

    private final ConfigurableApplicationContext ctx;

    private boolean ticked;

    public TickSource(ConfigurableApplicationContext ctx) {
        this.ctx = ctx;
        ctx.addApplicationListener(this);
    }

    public boolean isTicked() {
        LOGGER.info("isTicked called! ticked = {}", ticked);
        return ticked;
    }

    public void setTicked(boolean ticked) {
        this.ticked = ticked;
        LOGGER.info("Publishing tick changed event");

        Function<Object, Boolean> fn = o -> ((TickSource)o).isTicked();
        ctx.publishEvent(new ValueChangedEvent(this, "ticked", fn));
    }

    public void toggleTicked() {
        ticked = !ticked;
        LOGGER.info("Publishing tick toggled event");

        Function<Object, Boolean> fn = o -> ((TickSource)o).isTicked();

        ctx.publishEvent(new ValueChangedEvent(this, "ticked", fn));
    }

    @Override
    public void onApplicationEvent(ChangeValueEvent changeValueEvent) {
        if (changeValueEvent.getValueChangeId().equals("ticked")) {
            LOGGER.info(("Received 'ticked' changeValueEvent. Tick toggled!"));
            toggleTicked();
        }
    }
}
