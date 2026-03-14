package com.dm.game.event.listener;

import com.dm.game.event.Event;

/**
 * The base event listener that will listen for any type of event.
 *
 * @author nshusa
 */
public interface EventListener {

    default void accept(Event event) {

    }

}
