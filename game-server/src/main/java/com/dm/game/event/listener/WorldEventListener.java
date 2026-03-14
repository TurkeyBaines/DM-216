package com.dm.game.event.listener;

import com.dm.game.event.Event;
import com.dm.game.event.impl.log.LogEvent;

public final class WorldEventListener implements EventListener {

    @Override
    public void accept(Event event) {
        if (event instanceof LogEvent) {
            LogEvent logEvent = (LogEvent) event;
            logEvent.log();
        }
    }

}
