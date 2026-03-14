package com.dm.net.packet.out;

import com.dm.game.world.entity.mob.player.Player;
import com.dm.net.packet.OutgoingPacket;

public class SendWalkableInterface extends OutgoingPacket {

	private final int id;

	public SendWalkableInterface(int id) {
		super(208, 2);
		this.id = id;
	}

	@Override
	public boolean encode(Player player) {
		builder.writeShort(id);
		return true;
	}

}
