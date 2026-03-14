package com.dm.net.packet.out;

import com.dm.game.world.entity.mob.player.Player;
import com.dm.net.packet.OutgoingPacket;

public class SendLogout extends OutgoingPacket {

	public SendLogout() {
		super(109, 0);
	}

	@Override
	public boolean encode(Player player) {
		return true;
	}

}
