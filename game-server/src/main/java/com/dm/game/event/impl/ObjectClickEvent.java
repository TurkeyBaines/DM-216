package com.dm.game.event.impl;

import com.dm.game.event.Event;
import com.dm.game.world.object.GameObject;

public class ObjectClickEvent implements Event {

    private final int type;

    private final GameObject object;

    public ObjectClickEvent(int type, GameObject object) {
        this.type = type;
        this.object = object;
    }

    public int getType() {
        return type;
    }

    public GameObject getObject() {
        return object;
    }

}
