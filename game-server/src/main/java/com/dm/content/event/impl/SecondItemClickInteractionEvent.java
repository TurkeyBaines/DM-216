package com.dm.content.event.impl;

import com.dm.content.event.InteractionEvent;
import com.dm.game.world.items.Item;

public class SecondItemClickInteractionEvent extends ItemInteractionEvent {

	public SecondItemClickInteractionEvent(Item item, int slot) {
		super(InteractionEvent.InteractionType.SECOND_ITEM_CLICK, item, slot, 1);
	}
}
