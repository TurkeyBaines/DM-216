package org.dm.event.npc;

import com.dm.game.world.entity.mob.npc.Npc;
import com.dm.game.world.entity.mob.player.Player;

/**
 * @author Jire
 */
public final class AttackNpcEvent implements NpcClickEvent {

    private final int slot;

    public AttackNpcEvent(int slot) {
        this.slot = slot;
    }

    @Override
    public int getSlot() {
        return slot;
    }

    @Override
    public void handleNpc(Player player, Npc npc) {
        player.getCombat().attack(npc);
    }

}