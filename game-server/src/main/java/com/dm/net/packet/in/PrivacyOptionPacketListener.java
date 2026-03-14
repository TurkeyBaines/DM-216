package com.dm.net.packet.in;

import com.dm.game.world.entity.mob.player.Player;
import com.dm.net.packet.ClientPackets;
import com.dm.net.packet.GamePacket;
import com.dm.net.packet.PacketListener;
import com.dm.net.packet.PacketListenerMeta;
import org.jire.tarnishps.event.widget.PrivacyOptionEvent;

@PacketListenerMeta({ClientPackets.PRIVACY_OPTIONS})
public final class PrivacyOptionPacketListener implements PacketListener {

    @Override
    public void handlePacket(Player player, GamePacket packet) {
        final int publicMode = packet.readByte();
        final int privateMode = packet.readByte();
        final int tradeMode = packet.readByte();
        final int clanMode = packet.readByte();

        player.getEvents().widget(player,
                new PrivacyOptionEvent(
                        publicMode,
                        privateMode,
                        tradeMode,
                        clanMode));
    }

}
