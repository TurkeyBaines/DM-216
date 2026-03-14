package com.dm.net.packet.in;

import com.dm.game.world.entity.mob.player.Player;
import com.dm.net.packet.GamePacket;
import com.dm.net.packet.PacketListenerMeta;
import com.dm.net.packet.PacketListener;

@PacketListenerMeta({241})
public class MouseClickPacketListener implements PacketListener {

    @Override
    public void handlePacket(Player player, GamePacket packet) {

    }

}
