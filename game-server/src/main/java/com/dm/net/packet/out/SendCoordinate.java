package com.dm.net.packet.out;

import com.dm.net.codec.ByteModification;
import com.dm.game.world.entity.mob.player.Player;
import com.dm.game.world.position.Position;
import com.dm.net.packet.OutgoingPacket;

public class SendCoordinate extends OutgoingPacket {

	private final Position position;

	public SendCoordinate(Position position) {
		super(85, 2);
		this.position = position;
	}

	@Override
	public boolean encode(Player player) {
		final int y = position.getLocalY(player.lastPosition);
		final int x = position.getLocalX(player.lastPosition);
		builder.writeByte(y, ByteModification.NEG);
		builder.writeByte(x, ByteModification.NEG);
		return true;
	}

}
