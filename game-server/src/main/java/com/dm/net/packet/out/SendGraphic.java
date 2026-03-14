package com.dm.net.packet.out;

import com.dm.game.Graphic;
import com.dm.game.world.entity.mob.player.Player;
import com.dm.game.world.position.Position;
import com.dm.net.packet.OutgoingPacket;

public class SendGraphic extends OutgoingPacket {

	private final Graphic graphic;
	private final Position position;
	
	public SendGraphic(Graphic graphic, Position position) {
		super(4, 6);
		this.graphic = graphic;
		this.position = position;
	}

	@Override
	public boolean encode(Player player) {
		player.send(new SendCoordinate(position));
		builder.writeByte(0)
		.writeShort(graphic.getId())
		.writeByte(position.getHeight())
		.writeShort(graphic.getDelay());
		return true;
	}
}
