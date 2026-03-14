package com.dm.net.packet.out;

import com.dm.net.packet.OutgoingPacket;
import com.dm.net.packet.PacketType;
import com.dm.net.codec.ByteModification;
import com.dm.game.world.entity.mob.player.Player;
import com.dm.game.world.entity.mob.player.PlayerOption;

/**
 * Shows a player options such as right clicking a player.

 * @author Daniel
 */
public final class SendPlayerOption extends OutgoingPacket {

	private PlayerOption option;
	private boolean top;
	private boolean disable;

	public SendPlayerOption(PlayerOption option, boolean top) {
		this(option, top, false);
	}

	public SendPlayerOption(PlayerOption option, boolean top, boolean disable) {
		super(104, PacketType.VAR_BYTE);
		this.option = option;
		this.top = top;
		this.disable = disable;
	}

	@Override
	public boolean encode(Player player) {
		if (player.contextMenus.contains(option) && !disable) {
			return false;
		}

		builder.writeByte(option.getIndex(), ByteModification.NEG)
		.writeByte(top ? 1 : 0, ByteModification.ADD)
		.writeString(disable ? "null" : option.getName());

		if (disable) {
			player.contextMenus.remove(option);
		} else {
			player.contextMenus.add(option);
		}
		return true;
	}

}
