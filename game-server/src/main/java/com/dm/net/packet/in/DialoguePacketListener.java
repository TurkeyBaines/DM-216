package com.dm.net.packet.in;

import com.dm.game.world.entity.mob.player.Player;
import com.dm.net.packet.GamePacket;
import com.dm.net.packet.PacketListener;
import com.dm.net.packet.PacketListenerMeta;
import org.dm.event.widget.DialogueEvent;

/**
 * The {@link GamePacket} responsible for dialogues.
 * 
 * @author Daniel | Obey
 */
@PacketListenerMeta(40)
public class DialoguePacketListener implements PacketListener {

	@Override
	public void handlePacket(Player player, GamePacket packet) {
		player.getEvents().widget(player, DialogueEvent.INSTANCE);
	}

}
