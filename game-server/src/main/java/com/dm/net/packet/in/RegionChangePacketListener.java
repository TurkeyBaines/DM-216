package com.dm.net.packet.in;

import com.dm.game.world.entity.mob.player.Player;
import com.dm.game.world.entity.mob.player.PlayerRight;
import com.dm.net.packet.ClientPackets;
import com.dm.net.packet.GamePacket;
import com.dm.net.packet.PacketListener;
import com.dm.net.packet.PacketListenerMeta;
import com.dm.net.packet.out.SendMessage;
import com.dm.util.MessageColor;

/**
 * The {@link GamePacket}'s responsible for changing a players region. Used when
 * a player enters a new map region or when the map region has been successfully
 * loaded.
 *
 * @author Daniel
 */
@PacketListenerMeta({ClientPackets.LOADED_REGION, ClientPackets.ENTER_REGION})
public class RegionChangePacketListener implements PacketListener {

    @Override
    public void handlePacket(Player player, GamePacket packet) {
        switch (packet.getOpcode()) {
            case ClientPackets.ENTER_REGION:
                int a = packet.readInt();
                if (player.debug && PlayerRight.isOwner(player)) {
                    player.send(new SendMessage("[REGION] Entered new region: " + a, MessageColor.DEVELOPER));
                }
                if (a != 0x3f008edd) {
                    player.getEvents().setLogOut(true);
                }
                break;

            case ClientPackets.LOADED_REGION:
                player.getEvents().setLoadRegion(true);
                break;
        }
    }
}