package com.dm.net.packet.out;

import com.dm.net.codec.ByteOrder;
import com.dm.net.codec.ByteModification;
import com.dm.game.world.entity.mob.player.Player;
import com.dm.net.packet.OutgoingPacket;

public class SendPlayerDetails extends OutgoingPacket {

	public SendPlayerDetails() {
		super(249, 3);
	}

	@Override
	public boolean encode(Player player) {
		builder.writeByte(1, ByteModification.ADD)
		.writeShort(player.getIndex(), ByteModification.ADD, ByteOrder.LE);
		return true;
	}

}
