package com.dm.content.event.impl;

import com.dm.game.world.items.Item;

public class ThirdItemClickInteractionEvent extends ItemInteractionEvent {

	public ThirdItemClickInteractionEvent(Item item, int slot) {
		super(InteractionType.THIRD_ITEM_CLICK, item, slot, 2);
	}
}
