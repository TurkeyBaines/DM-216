package com.dm.net.packet.out;

import com.dm.net.packet.OutgoingPacket;
import com.dm.net.packet.PacketType;
import com.dm.game.world.entity.mob.player.Player;
import com.dm.game.world.entity.mob.player.PlayerRight;

public class SendPrivateMessage extends OutgoingPacket {

	private final long name;
	private final PlayerRight rights;
	private final byte[] compressed;

	public SendPrivateMessage(long name, PlayerRight rights, byte[] compressed) {
		super(196, PacketType.VAR_BYTE);
		this.name = name;
		this.rights = rights;
		this.compressed = compressed;
	}

	@Override
	public boolean encode(Player player) {
		builder.writeLong(name)
		.writeInt(player.relations.getPrivateMessageId())
		.writeByte(rights.getCrown())
		.writeBytes(compressed, compressed.length);
		return true;
	}

}
