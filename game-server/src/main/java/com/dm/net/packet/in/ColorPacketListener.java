package com.dm.net.packet.in;

import com.dm.net.packet.GamePacket;
import com.dm.net.packet.PacketListenerMeta;
import com.dm.net.packet.PacketListener;
import com.dm.net.packet.out.SendMessage;
import com.dm.net.codec.ByteOrder;
import com.dm.game.world.entity.mob.UpdateFlag;
import com.dm.game.world.entity.mob.player.Player;
import com.dm.game.world.entity.mob.player.PlayerRight;
import com.dm.content.tittle.PlayerTitle;
import com.dm.util.MessageColor;

@PacketListenerMeta(187)
public class ColorPacketListener implements PacketListener {

	@Override
	public void handlePacket(Player player, GamePacket packet) {
		int identification = packet.readShort(ByteOrder.LE);
		int value = packet.readInt();
		
		if (player.right.equals(PlayerRight.OWNER)) {
			player.send(new SendMessage("[ColorPacket] - Identification: " + identification + " Value: " + value, MessageColor.DEVELOPER));
		}

		switch (identification) {
		
		case 0:
			player.playerTitle = PlayerTitle.create(player.playerTitle.getTitle(), value);
			player.updateFlags.add(UpdateFlag.APPEARANCE);
			break;
			
		case 1:
			//yell
			break;
		}
	}
}
