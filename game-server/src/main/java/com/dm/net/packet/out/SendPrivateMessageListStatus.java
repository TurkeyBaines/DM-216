package com.dm.net.packet.out;

import com.dm.game.world.entity.mob.player.Player;
import com.dm.game.world.entity.mob.player.relations.PrivateMessageListStatus;
import com.dm.net.packet.OutgoingPacket;

public class SendPrivateMessageListStatus extends OutgoingPacket {

	private final PrivateMessageListStatus status;

	public SendPrivateMessageListStatus(PrivateMessageListStatus status) {
		super(221, 1);
		this.status = status;
	}

	@Override
	public boolean encode(Player player) {
		builder.writeByte(status.ordinal());
		return true;
	}

}