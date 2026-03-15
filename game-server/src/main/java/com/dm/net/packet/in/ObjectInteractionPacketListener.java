package com.dm.net.packet.in;

import com.dm.content.event.impl.FirstObjectClick;
import com.dm.content.event.impl.SecondObjectClick;
import com.dm.content.event.impl.ThirdObjectClick;
import com.dm.game.world.entity.mob.data.PacketType;
import com.dm.game.world.entity.mob.player.Player;
import com.dm.net.codec.ByteModification;
import com.dm.net.codec.ByteOrder;
import com.dm.net.packet.ClientPackets;
import com.dm.net.packet.GamePacket;
import com.dm.net.packet.PacketListener;
import com.dm.net.packet.PacketListenerMeta;
import org.dm.event.object.ObjectOptionEvent;

/**
 * The {@code GamePacket} responsible for clicking various options of an in-game
 * object.
 *
 * @author Daniel | Obey
 * @author Jire
 */
@PacketListenerMeta({
        ClientPackets.FIRST_CLICK_OBJECT,
        ClientPackets.SECOND_CLICK_OBJECT,
        ClientPackets.THIRD_CLICK_OBJECT
})
public final class ObjectInteractionPacketListener implements PacketListener {

    @Override
    public void handlePacket(Player player, GamePacket packet) {
        if (player.locking.locked(PacketType.CLICK_OBJECT)) {
            return;
        }

        ObjectOptionEvent event = switch (packet.getOpcode()) {
            case ClientPackets.FIRST_CLICK_OBJECT -> {
                int x = packet.readShort(ByteOrder.LE, ByteModification.ADD);
                int id = packet.readShort(false);
                int y = packet.readShort(false, ByteModification.ADD);
                yield new ObjectOptionEvent(1, id, x, y, FirstObjectClick::new);
            }

            case ClientPackets.SECOND_CLICK_OBJECT -> {
                int id = packet.readShort(ByteOrder.LE, ByteModification.ADD);
                int y = packet.readShort(ByteOrder.LE);
                int x = packet.readShort(false, ByteModification.ADD);
                yield new ObjectOptionEvent(2, id, x, y, SecondObjectClick::new);
            }

            case ClientPackets.THIRD_CLICK_OBJECT -> {
                int x = packet.readShort(ByteOrder.LE);
                int y = packet.readShort(false);
                int id = packet.readShort(false, ByteOrder.LE, ByteModification.ADD);
                yield new ObjectOptionEvent(3, id, x, y, ThirdObjectClick::new);
            }

            default -> null;
        };

        if (event != null) {
            player.getEvents().interact(player, event);
        }
    }

}