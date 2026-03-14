package com.dm.net.packet.out;

import com.dm.net.codec.ByteOrder;
import com.dm.net.codec.ByteModification;
import com.dm.game.world.entity.mob.player.Player;
import com.dm.net.packet.OutgoingPacket;

public class SendNpcHead extends OutgoingPacket {

	private final int npcId;
	private final int interfaceId;

	public SendNpcHead(int npcId, int interfaceId) {
		super(75, 4);
		this.npcId = npcId;
		this.interfaceId = interfaceId;
	}

	@Override
	public boolean encode(Player player) {
		builder.writeShort(npcId, ByteModification.ADD, ByteOrder.LE)
		.writeShort(interfaceId, ByteModification.ADD, ByteOrder.LE);
		return true;
	}

}
