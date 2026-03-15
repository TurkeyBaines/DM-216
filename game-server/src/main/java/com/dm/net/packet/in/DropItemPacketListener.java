package com.dm.net.packet.in;

import com.dm.content.itemaction.ItemActionRepository;
import com.dm.game.event.impl.DropItemEvent;
import com.dm.game.plugin.PluginManager;
import com.dm.game.world.entity.mob.data.PacketType;
import com.dm.game.world.entity.mob.player.Player;
import com.dm.game.world.entity.mob.player.PlayerRight;
import com.dm.game.world.items.Item;
import com.dm.game.world.items.ItemDefinition;
import com.dm.game.world.items.containers.pricechecker.PriceType;
import com.dm.game.world.items.ground.GroundItem;
import com.dm.game.world.position.Area;
import com.dm.net.codec.ByteModification;
import com.dm.net.packet.ClientPackets;
import com.dm.net.packet.GamePacket;
import com.dm.net.packet.PacketListener;
import com.dm.net.packet.PacketListenerMeta;
import com.dm.net.packet.out.SendMessage;

/**
 * The {@code GamePacket} responsible for dropping items.
 *
 * @author Daniel | Obey
 */
@PacketListenerMeta(ClientPackets.DROP_ITEM)
public class DropItemPacketListener implements PacketListener {

    @Override
    public void handlePacket(Player player, GamePacket packet) {

        final int itemId = packet.readShort(false, ByteModification.ADD);
        packet.readByte(false);
        packet.readByte(false);
        final int slot = packet.readShort(false, ByteModification.ADD);
        final Item item = player.inventory.get(slot);

        if (ItemDefinition.get(itemId) == null)
            return;

        player.getCombat().reset();

        if (!player.interfaceManager.isClear())
            player.interfaceManager.close(false);

        if (player.idle)
            player.idle = false;

        if (item == null)
            return;

        if (item.getId() != itemId)
            return;

        if (PluginManager.getDataBus().publish(player, new DropItemEvent(item, slot, player.getPosition().copy())))
            return;

        if (ItemActionRepository.drop(player, item)) {
            if (PlayerRight.isOwner(player)) {
                player.send(new SendMessage(String.format("[%s]: item=%d amount=%d slot=%d", ItemActionRepository.class.getSimpleName(), item.getId(), item.getAmount(), slot)));
            }
            return;
        }

        boolean inWilderness = Area.inWilderness(player);
        if (inWilderness && item.getValue(PriceType.VALUE) >= 500_000) {
            player.dialogueFactory.sendStatement("This is a valuable item, are you sure you want to", "drop it? In a PvP area, this item will be seen", "by everyone when dropped.");
            player.dialogueFactory.sendOption("Yes, drop it.", () -> {
                player.inventory.remove(item, slot, true);
                GroundItem.createGlobal(player, item);
            }, "Nevermind", () -> player.dialogueFactory.clear());
            player.dialogueFactory.execute();
            return;
        } else if (inWilderness) {
            player.inventory.remove(item, slot, true);
            GroundItem.createGlobal(player, item);
            return;
        }

        player.inventory.remove(item, slot, true);
        GroundItem.create(player, item);
    }
}
