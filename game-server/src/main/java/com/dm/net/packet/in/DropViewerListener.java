package com.dm.net.packet.in;

import com.dm.game.world.entity.mob.player.Player;
import com.dm.net.packet.ClientPackets;
import com.dm.net.packet.GamePacket;
import com.dm.net.packet.PacketListener;
import com.dm.net.packet.PacketListenerMeta;
import org.dm.event.widget.DropViewerEvent;

@PacketListenerMeta({ClientPackets.NPC_DROP_VIEWER})
public class DropViewerListener implements PacketListener {

    @Override
    public void handlePacket(Player player, GamePacket packet) {
        final String context = packet.getRS2String();
        if (context == null || context.isEmpty() || context.equalsIgnoreCase("null")) {
            return;
        }

        player.getEvents().widget(player, new DropViewerEvent(context));
    }
}
