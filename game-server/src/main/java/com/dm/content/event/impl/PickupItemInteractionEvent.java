package com.dm.content.event.impl;

import com.dm.content.event.InteractionEvent;
import com.dm.game.world.items.Item;
import com.dm.game.world.position.Position;

public class PickupItemInteractionEvent extends InteractionEvent {

	private final Item item;
	private final Position position;

	public PickupItemInteractionEvent(Item item, Position position) {
		super(InteractionType.PICKUP_ITEM);
		this.item = item;
		this.position = position;
	}

	public Item getItem() {
		return item;
	}

	public Position getPosition() {
		return position;
	}
}