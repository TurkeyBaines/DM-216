package com.dm.net.packet.in;

import com.dm.game.world.entity.mob.player.Player;
import com.dm.net.codec.ByteModification;
import com.dm.net.codec.ByteOrder;
import com.dm.net.packet.ClientPackets;
import com.dm.net.packet.GamePacket;
import com.dm.net.packet.PacketListener;
import com.dm.net.packet.PacketListenerMeta;
import org.dm.event.widget.MoveItemEvent;

@PacketListenerMeta(ClientPackets.MOVE_ITEM)
public class MoveItemPacketListener implements PacketListener {

    @Override
    public void handlePacket(Player player, GamePacket packet) {
        final int interfaceId = packet.readShort(ByteOrder.LE, ByteModification.ADD);
        final int inserting = packet.readByte(ByteModification.NEG);
        final int fromSlot = packet.readShort(ByteOrder.LE, ByteModification.ADD);
        final int toSlot = packet.readShort(ByteOrder.LE);

        player.idle = false;

        player.getEvents().widget(player, new MoveItemEvent(interfaceId, inserting, fromSlot, toSlot));
    }

}
