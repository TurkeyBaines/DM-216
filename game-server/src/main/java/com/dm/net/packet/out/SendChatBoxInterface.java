package com.dm.net.packet.out;

import com.dm.net.codec.ByteOrder;
import com.dm.game.world.entity.mob.player.Player;
import com.dm.net.packet.OutgoingPacket;

public class SendChatBoxInterface extends OutgoingPacket {

	private final int interfaceId;

	public SendChatBoxInterface(int interfaceId) {
		super(164, 2);
		this.interfaceId = interfaceId;
	}

	@Override
	public boolean encode(Player player) {
		player.interfaceManager.setDialogue(1);
		builder.writeShort(interfaceId, ByteOrder.LE);
		return true;
	}
}
