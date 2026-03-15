package org.dm.event.npc;

import com.dm.game.world.World;
import com.dm.game.world.entity.mob.data.PacketType;
import com.dm.game.world.entity.mob.npc.Npc;
import com.dm.game.world.entity.mob.player.Player;
import com.dm.game.world.position.Position;

/**
 * @author Jire
 */
public interface NpcClickEvent extends NpcEvent {

    int getSlot();

    @Override
    default boolean canHandle(Player player) {

        return NpcEvent.super.canHandle(player)
                && !player.locking.locked(PacketType.CLICK_NPC);
    }

    @Override
    default void handle(Player player) {
        // Kotlin's getOrNull() equivalent in Java 8+
        Npc npc = World.getNpcBySlot(getSlot()).orElse(null);

        if (npc == null || !npc.isValid()) {
            return;
        }

        Position position = npc.getPosition();
        var region = World.getRegions().getRegion(position);

        if (!region.containsNpc(position.getHeight(), npc)) {
            return;
        }

        handleNpc(player, npc);
    }

    default void handleNpc(Player player, Npc npc) {
        // Default implementation does nothing
    }

}