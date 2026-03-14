package com.dm.net.packet.in;

import com.dm.game.world.entity.mob.player.Player;
import com.dm.net.packet.GamePacket;
import com.dm.net.packet.PacketListener;
import com.dm.net.packet.PacketListenerMeta;

/**
 * The {@link GamePacket} responsible logging out a player after a certain
 * amount of time.
 * 
 * @author Daniel
 */
@PacketListenerMeta(202)
public class IdleLogoutPacketListener implements PacketListener {

    @Override
    public void handlePacket(Player player, GamePacket packet) {
        player.idle = true;
    }

}