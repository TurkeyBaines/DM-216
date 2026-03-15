package org.dm.event.player;

import com.dm.game.plugin.PluginManager;
import com.dm.game.world.World;
import com.dm.game.world.entity.mob.player.Player;
import com.dm.game.world.items.Item;
import com.dm.net.packet.out.SendMessage;
import org.dm.event.Event;

/**
 * @author Jire
 */
public final class ItemOnPlayerEvent implements Event {

    private final int interfaceId;
    private final int item;
    private final int itemSlot;
    private final int slot;

    public ItemOnPlayerEvent(int interfaceId, int item, int itemSlot, int slot) {
        this.interfaceId = interfaceId;
        this.item = item;
        this.itemSlot = itemSlot;
        this.slot = slot;
    }

    @Override
    public void handle(Player player) {
        Item used = player.inventory.get(itemSlot);
        if (used == null) {
            return;
        }
        if (!used.matchesId(item)) {
            return;
        }

        Player other = World.getPlayerBySlot(slot).orElse(null);
        if (other == null) {
            return;
        }

        player.walkTo(other, () -> {
            player.face(other.getPosition());

            // Resolve naming conflict by using the fully qualified name for the data bus event
            com.dm.game.event.impl.ItemOnPlayerEvent interactionEvent =
                    new com.dm.game.event.impl.ItemOnPlayerEvent(other, used, itemSlot);

            if (!PluginManager.getDataBus().publish(player, interactionEvent)) {
                player.send(new SendMessage("Nothing interesting happens."));
            }
        });
    }

    public int getInterfaceId() {
        return interfaceId;
    }

    public int getItem() {
        return item;
    }

    public int getItemSlot() {
        return itemSlot;
    }

    public int getSlot() {
        return slot;
    }

}