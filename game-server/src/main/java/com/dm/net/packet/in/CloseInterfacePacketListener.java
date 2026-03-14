package com.dm.net.packet.in;

import com.dm.net.packet.GamePacket;
import com.dm.net.packet.PacketListenerMeta;
import com.dm.net.packet.PacketListener;
import com.dm.net.packet.ClientPackets;
import com.dm.game.world.entity.mob.player.Player;

/**
 * The {@link GamePacket} responsible for closing interfaces.
 * 
 * @author Daniel
 */
@PacketListenerMeta(130)
public class CloseInterfacePacketListener implements PacketListener {

	@Override
	public void handlePacket(Player player, GamePacket packet) {

		switch (packet.getOpcode()) {

		case ClientPackets.CLOSE_WINDOW:
			player.interfaceManager.close(false);
			break;
		}
	}
}