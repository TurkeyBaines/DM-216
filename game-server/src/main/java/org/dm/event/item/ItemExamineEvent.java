package org.dm.event.item;

import com.dm.game.world.entity.mob.player.Player;
import com.dm.net.packet.out.SendMessage;
import org.dm.defs.ItemDef;
import org.dm.defs.ItemDefLoader;
import org.dm.event.Event;

/**
 * @author Jire
 */
public final class ItemExamineEvent implements Event {

    private final int itemId;

    public ItemExamineEvent(int itemId) {
        this.itemId = itemId;
    }

    @Override
    public void handle(Player player) {
        ItemDef itemDef = ItemDefLoader.map.get(itemId);
        if (itemDef == null) {
            return;
        }

        String examine = itemDef.getExamine();
        if (!"null".equals(examine)) {
            player.send(new SendMessage(examine));
        }
    }

    public int getItemId() {
        return itemId;
    }

}