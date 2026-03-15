package com.dm.net.packet.in;

import com.dm.game.world.entity.mob.data.PacketType;
import com.dm.game.world.entity.mob.player.Player;
import com.dm.net.codec.ByteOrder;
import com.dm.net.packet.ClientPackets;
import com.dm.net.packet.GamePacket;
import com.dm.net.packet.PacketListener;
import com.dm.net.packet.PacketListenerMeta;
import org.dm.event.item.PickupItemEvent;

/**
 * The {@link GamePacket} responsible for picking up an item on the ground.
 *
 * @author Daniel | Obey
 */
@PacketListenerMeta(ClientPackets.PICKUP_GROUND_ITEM)
public class PickupItemPacketListener implements PacketListener {

    @Override
    public void handlePacket(final Player player, GamePacket packet) {
        if (player.locking.locked(PacketType.PICKUP_ITEM)) {
            return;
        }

        final int y = packet.readShort(ByteOrder.LE);
        final int id = packet.readShort(false);
        final int x = packet.readShort(ByteOrder.LE);

        player.getEvents().interact(player, new PickupItemEvent(id, x, y));
    }

}
