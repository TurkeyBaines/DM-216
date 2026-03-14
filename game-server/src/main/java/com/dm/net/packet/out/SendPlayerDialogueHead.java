package com.dm.net.packet.out;

import com.dm.net.codec.ByteModification;
import com.dm.net.codec.ByteOrder;
import com.dm.game.world.entity.mob.player.Player;
import com.dm.net.packet.OutgoingPacket;

public class SendPlayerDialogueHead extends OutgoingPacket {

	private final int interfaceId;

	public SendPlayerDialogueHead(int interfaceId) {
		super(185, 2);
		this.interfaceId = interfaceId;
	}

	@Override
	public boolean encode(Player player) {
		builder.writeShort(interfaceId, ByteModification.ADD, ByteOrder.LE);
		return true;
	}

}
