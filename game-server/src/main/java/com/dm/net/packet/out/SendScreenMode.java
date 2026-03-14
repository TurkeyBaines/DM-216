package com.dm.net.packet.out;

import com.dm.game.world.entity.mob.player.Player;
import com.dm.net.codec.ByteModification;
import com.dm.net.codec.ByteOrder;
import com.dm.net.packet.OutgoingPacket;

/** Sends the screen mode state for the player. */
public class SendScreenMode extends OutgoingPacket {
		private final int width;
		private final int length;

	public SendScreenMode(int width, int length) {
		super(108, 6);
		this.width = width;
		this.length = length;
	}

	@Override
	public boolean encode(Player player) {
		builder.writeShort(width, ByteModification.ADD, ByteOrder.LE).writeInt(length);
		return true;
	}
}

