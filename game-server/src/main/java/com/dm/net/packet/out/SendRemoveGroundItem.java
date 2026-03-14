package com.dm.net.packet.out;

import com.dm.net.codec.ByteModification;
import com.dm.game.world.entity.mob.player.Player;
import com.dm.game.world.items.ground.GroundItem;
import com.dm.net.packet.OutgoingPacket;

public class SendRemoveGroundItem extends OutgoingPacket {

    private final GroundItem groundItem;

    public SendRemoveGroundItem(GroundItem groundItem) {
        super(156, 3);
        this.groundItem = groundItem;
    }

    @Override
    public boolean encode(Player player) {
        player.send(new SendCoordinate(groundItem.getPosition()));
        builder.writeByte(0, ByteModification.ADD)
        .writeShort(groundItem.item.getId());
        return true;
    }

}
