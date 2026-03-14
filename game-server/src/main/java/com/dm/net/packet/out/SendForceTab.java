package com.dm.net.packet.out;

import com.dm.net.codec.ByteModification;
import com.dm.game.world.entity.mob.player.Player;
import com.dm.net.packet.OutgoingPacket;

public class SendForceTab extends OutgoingPacket {

	private final int id;

	public SendForceTab(int id) {
		super(106, 1);
		this.id = id;
	}

	@Override
	public boolean encode(Player player) {
		builder.writeByte(id, ByteModification.NEG);
		return true;
	}

}
