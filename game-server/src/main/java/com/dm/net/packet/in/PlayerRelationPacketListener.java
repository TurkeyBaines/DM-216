package com.dm.net.packet.in;

import com.dm.game.world.entity.mob.player.Player;
import com.dm.net.packet.ClientPackets;
import com.dm.net.packet.GamePacket;
import com.dm.net.packet.PacketListener;
import com.dm.net.packet.PacketListenerMeta;
import org.dm.event.widget.PlayerRelationEvent;

/**
 * The {@link GamePacket}'s responsible for player communication.
 *
 * @author Daniel | Obey
 */
@PacketListenerMeta({ClientPackets.ADD_FRIEND, ClientPackets.PRIVATE_MESSAGE, ClientPackets.REMOVE_FRIEND, ClientPackets.REMOVE_IGNORE, ClientPackets.ADD_IGNORE})
public final class PlayerRelationPacketListener implements PacketListener {

    @Override
    public void handlePacket(Player player, GamePacket packet) {
        final int opcode = packet.getOpcode();
        final long username = packet.readLong();
        player.getEvents().widget(player,
                new PlayerRelationEvent(opcode, username,
                        opcode == ClientPackets.PRIVATE_MESSAGE
                                ? packet.readBytes(packet.getSize() - Long.BYTES)
                                : null));
    }

}
