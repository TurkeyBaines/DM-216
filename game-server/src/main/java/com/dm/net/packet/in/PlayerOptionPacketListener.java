package com.dm.net.packet.in;

import com.dm.content.ProfileViewer;
import com.dm.game.world.entity.mob.player.Player;
import com.dm.game.world.entity.mob.player.exchange.duel.StakeSession;
import com.dm.game.world.entity.mob.player.exchange.trade.TradeSession;
import com.dm.net.codec.ByteModification;
import com.dm.net.codec.ByteOrder;
import com.dm.net.packet.ClientPackets;
import com.dm.net.packet.GamePacket;
import com.dm.net.packet.PacketListener;
import com.dm.net.packet.PacketListenerMeta;
import org.dm.event.Event;
import org.dm.event.player.MagicOnPlayerEvent;

import static org.dm.event.player.PlayerEvent.walkTo;

/**
 * The {@link GamePacket}s responsible interacting with other players.
 *
 * @author Daniel | Obey
 * @author Jire
 */
@PacketListenerMeta({
        ClientPackets.TRADE_REQUEST,
        ClientPackets.TRADE_ANSWER,
        ClientPackets.CHALLENGE_PLAYER,
        ClientPackets.FOLLOW_PLAYER,
        ClientPackets.MAGIC_ON_PLAYER,
        ClientPackets.ATTACK_PLAYER,
        ClientPackets.GAMBLE_PLAYER
})
public final class PlayerOptionPacketListener implements PacketListener {

    @Override
    public void handlePacket(Player player, GamePacket packet) {
        Event event = switch (packet.getOpcode()) {
            case ClientPackets.GAMBLE_PLAYER -> walkTo(packet.readShort(), target ->
                    player.getGambling().sendRequest(player, target)
            );

            case 128 -> walkTo(packet.readShort(), target ->
                    player.exchangeSession.request(new StakeSession(player, target))
            );

            case 153 -> walkTo(packet.readShort(ByteOrder.LE), target ->
                    player.getCombat().attack(target)
            );

            case 73 -> walkTo(packet.readShort(ByteOrder.LE), target ->
                    player.follow(target)
            );

            case 139 -> walkTo(packet.readShort(ByteOrder.LE), target ->
                    player.exchangeSession.request(new TradeSession(player, target))
            );

            case 39 -> walkTo(packet.readShort(ByteOrder.LE), target ->
                    ProfileViewer.open(player, target)
            );

            case ClientPackets.MAGIC_ON_PLAYER -> new MagicOnPlayerEvent(
                    packet.readShort(ByteModification.ADD),
                    packet.readShort(ByteOrder.LE)
            );

            default -> null;
        };

        if (event != null) {
            player.getEvents().interact(player, event);
        }
    }

}