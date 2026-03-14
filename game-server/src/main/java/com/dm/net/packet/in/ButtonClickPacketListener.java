package com.dm.net.packet.in;

import com.dm.content.event.EventDispatcher;
import com.dm.content.event.impl.ClickButtonInteractionEvent;
import com.dm.game.event.impl.ButtonClickEvent;
import com.dm.game.plugin.PluginManager;
import com.dm.game.world.entity.mob.data.PacketType;
import com.dm.game.world.entity.mob.player.Player;
import com.dm.game.world.entity.mob.player.PlayerRight;
import com.dm.net.packet.ClientPackets;
import com.dm.net.packet.GamePacket;
import com.dm.net.packet.PacketListener;
import com.dm.net.packet.PacketListenerMeta;
import com.dm.net.packet.out.SendMessage;

/**
 * The {@code GamePacket} responsible for clicking buttons on the client.
 *
 * @author Daniel | Obey
 */
@PacketListenerMeta(ClientPackets.BUTTON_CLICK)
public class ButtonClickPacketListener implements PacketListener {

    @Override
    public void handlePacket(final Player player, GamePacket packet) {
        final int button = packet.readShort();

        if (player.isDead()) {
            return;
        }

        if (player.locking.locked(PacketType.CLICK_BUTTON, button)) {
            return;
        }


        // player.message("Currently not available");

        if (PlayerRight.isDeveloper(player) || PlayerRight.isOwner(player)) {
            player.send(new SendMessage(String.format("[%s]: button=%d", ButtonClickPacketListener.class.getSimpleName(), button)));
            System.out.println(String.format("[%s]: button=%d", ButtonClickPacketListener.class.getSimpleName(), button));
        }//save it plz theres no save button with intellij


        if (EventDispatcher.execute(player, new ClickButtonInteractionEvent(button))) {
            return;
        }

        PluginManager.getDataBus().publish(player, new ButtonClickEvent(button));
    }
}
