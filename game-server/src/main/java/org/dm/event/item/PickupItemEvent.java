package org.dm.event.item;

import com.dm.content.event.EventDispatcher;
import com.dm.content.event.impl.PickupItemInteractionEvent;
import com.dm.game.world.entity.mob.player.Player;
import com.dm.game.world.entity.mob.player.PlayerRight;
import com.dm.game.world.items.Item;
import com.dm.game.world.position.Position;
import com.dm.net.packet.out.SendMessage;

/**
 * @author Jire
 */
public final class PickupItemEvent implements ItemEvent {

    private final int id;
    private final int x;
    private final int y;

    public PickupItemEvent(int id, int x, int y) {
        this.id = id;
        this.x = x;
        this.y = y;
    }

    @Override
    public void handle(Player player) {
        Item item = new Item(id);
        Position position = Position.create(x, y, player.getHeight());

        if (EventDispatcher.execute(player, new PickupItemInteractionEvent(item, position))) {
            if (PlayerRight.isOwner(player)) {
                player.send(
                        new SendMessage(
                                String.format(
                                        "[%s]: item=%d position=%s",
                                        PickupItemInteractionEvent.class.getSimpleName(),
                                        item.getId(),
                                        position.toString()
                                )
                        )
                );
            }
            return;
        }

        player.pickup(item, position);
    }

    public int getId() {
        return id;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

}