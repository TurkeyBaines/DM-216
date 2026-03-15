package org.dm.event.npc;

import com.dm.content.event.InteractionEvent;
import com.dm.content.event.impl.FirstNpcClick;
import com.dm.game.world.entity.mob.npc.Npc;
import com.dm.game.world.entity.mob.player.Player;

/**
 * @author Jire
 */
public final class FirstNpcOptionEvent extends NpcOptionEvent {

    public FirstNpcOptionEvent(int slot) {
        super(slot, 0);
    }

    @Override
    public void handleNpc(Player player, Npc npc) {
        // Special case for NPC 394 (typically Bankers/Siles) with distance check
        if (npc.getId() == 394 && player.getPosition().isWithinDistance(npc.getPosition(), 2)) {
            publishToPluginManager(player, npc);
        } else {
            super.handleNpc(player, npc);
        }
    }

    @Override
    public InteractionEvent createInteractionEvent(Npc npc) {
        return new FirstNpcClick(npc);
    }

}