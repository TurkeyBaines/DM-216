package com.dm.net.packet.out;

import com.dm.net.codec.ByteModification;
import com.dm.game.world.entity.mob.player.Player;
import com.dm.game.world.object.GameObject;
import com.dm.net.packet.OutgoingPacket;

public class SendRemoveObject extends OutgoingPacket {

	private final GameObject object;

	public SendRemoveObject(GameObject object) {
		super(101, 2);
		this.object = object;
	}

	@Override
	public boolean encode(Player player) {
		player.send(new SendCoordinate(object.getPosition()));
		builder.writeByte(object.getObjectType().getId() << 2 | (object.getDirection().getId() & 3), ByteModification.NEG)
		.writeByte(0);
		return true;
	}
}