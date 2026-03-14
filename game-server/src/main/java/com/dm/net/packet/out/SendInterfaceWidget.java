package com.dm.net.packet.out;

import com.dm.game.world.entity.mob.player.Player;
import com.dm.net.codec.ByteModification;
import com.dm.net.codec.ByteOrder;
import com.dm.net.packet.OutgoingPacket;
import com.dm.net.packet.PacketType;

public class SendInterfaceWidget extends OutgoingPacket {

	private final int interfaceID;
	private final int modelID;

	public SendInterfaceWidget(int interfaceID, int modelID) {
		super(8, PacketType.EMPTY);
		this.interfaceID = interfaceID;
		this.modelID = modelID;
	}

	@Override
	public boolean encode(Player player) {
		builder.writeShort(interfaceID, ByteModification.ADD, ByteOrder.LE);
		builder.writeShort(modelID);
		return true;
	}
}
