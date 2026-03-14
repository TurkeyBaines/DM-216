package com.dm.game.event.impl;

import com.dm.game.event.Event;
import com.dm.game.world.items.Item;
import com.dm.game.world.items.ground.GroundItem;
import com.dm.game.world.position.Position;

public class PickupItemEvent implements Event {

    private final GroundItem groundItem;

    public PickupItemEvent(GroundItem groundItem) {
        this.groundItem = groundItem;
    }

    public GroundItem getGroundItem() {
        return groundItem;
    }

    public Item getItem() {
        return groundItem.item;
    }

    public Position getPosition() {
        return groundItem.getPosition();
    }

}
