package com.dm.net.packet.out;

import com.dm.net.packet.OutgoingPacket;
import com.dm.net.packet.PacketType;
import com.dm.game.world.entity.mob.player.Player;

public class SendAnnouncement extends OutgoingPacket {
    private final String title;
    private final String message;
    private final int color;

    public SendAnnouncement(Object title, Object message, int color) {
        super(202, PacketType.VAR_BYTE);
        this.title = String.valueOf(title);
        this.message = String.valueOf(message);
        this.color = color;
    }

    @Override
    public boolean encode(Player player) {
        builder.writeString(title)
        .writeString(message)
        .writeInt(color);
        return true;
    }
}
