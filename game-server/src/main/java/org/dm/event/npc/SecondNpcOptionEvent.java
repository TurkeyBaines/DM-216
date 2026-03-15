package org.dm.event.npc;

import com.dm.content.event.InteractionEvent;
import com.dm.content.event.impl.SecondNpcClick;
import com.dm.game.world.entity.mob.npc.Npc;

/**
 * @author Jire
 */
public final class SecondNpcOptionEvent extends NpcOptionEvent {

    public SecondNpcOptionEvent(int slot) {
        super(slot, 1);
    }

    @Override
    public InteractionEvent createInteractionEvent(Npc npc) {
        return new SecondNpcClick(npc);
    }

}