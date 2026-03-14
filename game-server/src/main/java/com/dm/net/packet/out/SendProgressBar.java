package com.dm.net.packet.out;

import com.dm.net.packet.OutgoingPacket;
import com.dm.net.packet.PacketType;
import com.dm.game.world.entity.mob.player.Player;

/**
 * Handles sending the progress bar data to the client.
 *
 * @author Daniel
 */
public class SendProgressBar extends OutgoingPacket {
    private final int id;
    private final int amount;
    private final String message;

    public SendProgressBar(int id, int amount) {
        this(id, amount, "");
    }

    public SendProgressBar(int id, int amount, String message) {
        super(129, PacketType.VAR_BYTE);
        this.id = id;
        this.amount = amount;
        this.message = message;
    }

    @Override
    public boolean encode(Player player) {
        builder.writeInt(id)
        .writeShort(amount)
        .writeString(String.valueOf(message));
        return true;
    }
}
