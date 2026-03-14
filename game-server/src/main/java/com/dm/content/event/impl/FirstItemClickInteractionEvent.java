package com.dm.content.event.impl;

import com.dm.game.world.items.Item;

public class FirstItemClickInteractionEvent extends ItemInteractionEvent {

	public FirstItemClickInteractionEvent(Item item, int slot) {
		super(InteractionType.FIRST_ITEM_CLICK, item, slot, 0);
	}
}
