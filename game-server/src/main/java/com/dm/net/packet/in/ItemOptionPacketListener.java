package com.dm.net.packet.in;

import com.dm.content.event.EventDispatcher;
import com.dm.content.event.impl.FirstItemClickInteractionEvent;
import com.dm.content.event.impl.SecondItemClickInteractionEvent;
import com.dm.content.event.impl.ThirdItemClickInteractionEvent;
import com.dm.content.itemaction.ItemActionRepository;
import com.dm.game.event.impl.ItemClickEvent;
import com.dm.game.plugin.PluginManager;
import com.dm.game.world.InterfaceConstants;
import com.dm.game.world.entity.mob.data.PacketType;
import com.dm.game.world.entity.mob.player.Player;
import com.dm.game.world.items.Item;
import com.dm.net.codec.ByteModification;
import com.dm.net.codec.ByteOrder;
import com.dm.net.packet.GamePacket;
import com.dm.net.packet.PacketListener;
import com.dm.net.packet.PacketListenerMeta;
import static com.google.common.base.Preconditions.checkState;
import static com.dm.net.packet.ClientPackets.*;

/**
 * The {@code GamePacket} responsible for clicking the actions of an {@code
 * Item}.
 *
 * @author Daniel | Obey
 */
@PacketListenerMeta({ FIRST_ITEM_OPTION, SECOND_ITEM_OPTION, THIRD_ITEM_OPTION })
public class ItemOptionPacketListener implements PacketListener {

    @Override
    public void handlePacket(Player player, GamePacket packet) {
        checkState(player != null, "Player does not exist.");

        if (player.locking.locked(PacketType.CLICK_ITEM)) {
            return;
        }

        if (player.isDead()) {
            return;
        }

        switch (packet.getOpcode()) {

            case FIRST_ITEM_OPTION:
                handleFirstOption(player, packet);
                break;

            case SECOND_ITEM_OPTION:
                handleSecondOption(player, packet);
                break;

            case THIRD_ITEM_OPTION:
                handleThirdOption(player, packet);
                break;

        }
    }

    private static void handleFirstOption(Player player, GamePacket packet) {
        final int interfaceId = packet.readShort(ByteOrder.LE, ByteModification.ADD);
        final int slot = packet.readShort(false, ByteModification.ADD);
        final int id = packet.readShort(ByteOrder.LE);

        switch (interfaceId) {
            case 57716:
                player.forClan(channel -> channel.getShowcase().select(player, id, slot));
                break;
            case InterfaceConstants.INVENTORY_INTERFACE:
                final Item item = player.inventory.get(slot);

                if (item == null || item.getId() != id) {
                    return;
                }

                if (EventDispatcher.execute(player, new FirstItemClickInteractionEvent(item, slot))) {
                    return;
                }

                if (ItemActionRepository.inventory(player, item, 1)) {
                    return;
                }

                if (player.mysteryBox.click(item)) {
                    return;
                }

                PluginManager.getDataBus().publish(player, new ItemClickEvent(1, item, slot));
                break;
        }
    }

    private static void handleSecondOption(Player player, GamePacket packet) {
        final int itemId = packet.readShort(ByteModification.ADD);
        final int slot = packet.readShort(ByteOrder.LE, ByteModification.ADD);
        final int interfaceId = packet.readShort(ByteOrder.LE, ByteModification.ADD);

        switch (interfaceId) {
            case InterfaceConstants.INVENTORY_INTERFACE:
                final Item item = player.inventory.get(slot);

                if (item == null || item.getId() != itemId) {
                    return;
                }

                if (EventDispatcher.execute(player, new SecondItemClickInteractionEvent(item, slot))) {
                    return;
                }

                if (ItemActionRepository.inventory(player, item, 2)) {
                    return;
                }

                PluginManager.getDataBus().publish(player, new ItemClickEvent(2, item, slot));
                break;
        }
    }

    private static void handleThirdOption(Player player, GamePacket packet) {
        final int interfaceId = packet.readShort(ByteOrder.LE, ByteModification.ADD);
        final int slot = packet.readShort(ByteOrder.LE);
        final int itemId = packet.readShort(ByteModification.ADD);

        switch (interfaceId) {
            case InterfaceConstants.INVENTORY_INTERFACE:
                Item item = player.inventory.get(slot);

                if (item == null || item.getId() != itemId) {
                    return;
                }

                if (EventDispatcher.execute(player, new ThirdItemClickInteractionEvent(item, slot))) {
                    return;
                }

                if (ItemActionRepository.inventory(player, item, 3)) {
                    return;
                }

                PluginManager.getDataBus().publish(player, new ItemClickEvent(3, item, slot));
                break;
        }
    }

}
