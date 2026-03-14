package com.dm.content.event.impl;

import com.dm.content.event.InteractionEvent;
import com.dm.game.world.object.GameObject;

public class SecondObjectClick extends ObjectInteractionEvent {

	public SecondObjectClick(GameObject object) {
		super(InteractionEvent.InteractionType.SECOND_CLICK_OBJECT, object, 1);
	}

}