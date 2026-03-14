package com.dm.content.event;

import com.dm.game.world.entity.mob.player.Player;

public interface InteractionEventListener {

    boolean onEvent(Player player, InteractionEvent interactionEvent);
}