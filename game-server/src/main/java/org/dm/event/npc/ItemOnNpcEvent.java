package org.dm.event.npc;

import com.dm.game.plugin.PluginManager;
import com.dm.game.world.World;
import com.dm.game.world.entity.mob.npc.Npc;
import com.dm.game.world.entity.mob.player.Player;
import com.dm.game.world.items.Item;
import com.dm.game.world.position.Position;
import com.dm.net.packet.out.SendMessage;

/**
 * @author Jire
 */
public final class ItemOnNpcEvent implements NpcEvent {

    private final int itemId;
    private final int index;
    private final int slot;

    public ItemOnNpcEvent(int itemId, int index, int slot) {
        this.itemId = itemId;
        this.index = index;
        this.slot = slot;
    }

    @Override
    public void handle(Player player) {
        Item used = player.inventory.get(slot);
        if (used == null) return;
        if (!used.matchesId(itemId)) return;

        Npc npc = World.getNpcBySlot(index).orElse(null);
        if (npc == null || !npc.isValid()) return;

        Position position = npc.getPosition();
        var region = World.getRegions().getRegion(position);
        if (!region.containsNpc(position.getHeight(), npc)) return;

        player.walkTo(npc, () -> {
            player.face(position);

            // Note: Using fully qualified name for the Interaction Event to avoid collision with this class name
            com.dm.game.event.impl.ItemOnNpcEvent interactionEvent =
                    new com.dm.game.event.impl.ItemOnNpcEvent(npc, used, slot);

            if (!PluginManager.getDataBus().publish(player, interactionEvent)) {
                player.send(new SendMessage("Nothing interesting happens."));
            }
        });
    }

    public int getItemId() { return itemId; }
    public int getIndex() { return index; }
    public int getSlot() { return slot; }

}