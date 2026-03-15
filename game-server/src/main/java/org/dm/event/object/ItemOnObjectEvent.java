package org.dm.event.object;

import com.dm.content.event.EventDispatcher;
import com.dm.content.event.impl.ItemOnObjectInteractionEvent;
import com.dm.game.plugin.PluginManager;
import com.dm.game.world.InterfaceConstants;
import com.dm.game.world.World;
import com.dm.game.world.entity.mob.player.Player;
import com.dm.game.world.items.Item;
import com.dm.game.world.position.Position;
import com.dm.net.packet.out.SendMessage;

/**
 * @author Jire
 */
public final class ItemOnObjectEvent implements ObjectEvent {

    private final int interfaceType;
    private final int itemId;
    private final int slot;
    private final int objectId;
    private final int x;
    private final int y;

    public ItemOnObjectEvent(int interfaceType, int itemId, int slot, int objectId, int x, int y) {
        this.interfaceType = interfaceType;
        this.itemId = itemId;
        this.slot = slot;
        this.objectId = objectId;
        this.x = x;
        this.y = y;
    }

    @Override
    public void handle(Player player) {
        Item used = player.inventory.get(slot);
        if (used == null || !used.matchesId(itemId)) {
            return;
        }

        Position position = new Position(x, y, player.getHeight());
        int id = objectId;

        // Birdhouse logic check
        for (var birdHouseData : player.birdHouseData) {
            if (birdHouseData.birdhouseData.equals(position)) {
                id = birdHouseData.oldObjectId;
                break;
            }
        }

        var region = World.getRegions().getRegion(position);
        var obj = region.getGameObject(id, position);
        if (obj == null) {
            return;
        }

        player.walkTo(obj, () -> {
            if (interfaceType == InterfaceConstants.INVENTORY_INTERFACE) {
                player.face(obj);

                if (EventDispatcher.execute(player, new ItemOnObjectInteractionEvent(used, obj))) {
                    return;
                }

                // Using fully qualified name for the data bus event to avoid collision with this class
                com.dm.game.event.impl.ItemOnObjectEvent interactionEvent =
                        new com.dm.game.event.impl.ItemOnObjectEvent(used, slot, obj);

                if (!PluginManager.getDataBus().publish(player, interactionEvent)) {
                    player.send(new SendMessage("Nothing interesting happens."));
                }
            }
        });
    }

    // Getters
    public int getInterfaceType() { return interfaceType; }
    public int getItemId() { return itemId; }
    public int getSlot() { return slot; }
    public int getObjectId() { return objectId; }
    public int getX() { return x; }
    public int getY() { return y; }

}