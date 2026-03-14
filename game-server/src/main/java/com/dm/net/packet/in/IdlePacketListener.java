package com.dm.net.packet.in;

import com.dm.game.world.entity.mob.player.Player;
import com.dm.net.packet.GamePacket;
import com.dm.net.packet.PacketListenerMeta;
import com.dm.net.packet.PacketListener;

@PacketListenerMeta({0})
public class IdlePacketListener implements PacketListener {

    @Override
    public void handlePacket(Player player, GamePacket packet) {

    }

}
