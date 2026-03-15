package org.dm.event.npc;

import com.dm.content.event.EventDispatcher;
import com.dm.content.event.InteractionEvent;
import com.dm.game.action.impl.NpcFaceAction;
import com.dm.game.event.Event;
import com.dm.game.event.impl.NpcClickEvent;
import com.dm.game.plugin.PluginManager;
import com.dm.game.world.entity.mob.npc.Npc;
import com.dm.game.world.entity.mob.player.Player;

/**
 * @author Jire
 */
public class NpcOptionEvent implements org.dm.event.npc.NpcClickEvent {

    private final int slot;
    private final int option;

    public NpcOptionEvent(int slot, int option) {
        this.slot = slot;
        this.option = option;
    }

    @Override
    public int getSlot() {
        return slot;
    }

    @Override
    public void handleNpc(Player player, Npc npc) {
        player.walkTo(npc, () -> {
            npc.action.execute(createAction(player, npc), true);

            InteractionEvent interactionEvent = createInteractionEvent(npc);
            if (interactionEvent == null || !EventDispatcher.execute(player, interactionEvent)) {
                publishToPluginManager(player, npc);
            }
        });
    }

    public com.dm.game.action.Action createAction(Player player, Npc npc) {
        return new NpcFaceAction(npc, player.getPosition(), option);
    }

    public InteractionEvent createInteractionEvent(Npc npc) {
        return null;
    }

    public boolean publishToPluginManager(Player player, Npc npc) {
        return PluginManager.getDataBus().publish(player, createEvent(npc));
    }

    public Event createEvent(Npc npc) {
        // Option + 1 typically converts 0-indexed menu internal options
        // back to the 1-indexed packet/interface expectations.
        return new NpcClickEvent(option + 1, npc);
    }

    public int getOption() {
        return option;
    }

}