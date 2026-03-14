package com.dm.game.event.impl;

import com.dm.game.event.Event;
import com.dm.game.world.position.Position;

public class MovementEvent implements Event {

    private final Position destination;

    public MovementEvent(Position destination) {
        this.destination = destination;
    }

    public Position getDestination() {
        return destination;
    }

}
