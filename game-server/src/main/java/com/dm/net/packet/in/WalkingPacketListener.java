
package com.dm.net.packet.in;

import com.dm.game.world.entity.mob.player.Player;
import com.dm.net.codec.ByteModification;
import com.dm.net.codec.ByteOrder;
import com.dm.net.packet.ClientPackets;
import com.dm.net.packet.GamePacket;
import com.dm.net.packet.PacketListener;
import com.dm.net.packet.PacketListenerMeta;
import org.jire.tarnishps.event.player.WalkEvent;

/**
 * A packet which handles walking requests.
 *
 * @author Graham Edgecombe
 */
@PacketListenerMeta({ClientPackets.WALK_ON_COMMAND, ClientPackets.REGULAR_WALK, ClientPackets.MAP_WALK})
public class WalkingPacketListener implements PacketListener {

    @Override
    public void handlePacket(Player player, GamePacket packet) {
        final int targetX = packet.readShort(ByteOrder.LE);
        final int targetY = packet.readShort(ByteOrder.LE, ByteModification.ADD);
        final boolean runQueue = packet.readByte(ByteModification.NEG) == 1;

        player.getEvents().interact(player, new WalkEvent(targetX, targetY, runQueue));
    }

}