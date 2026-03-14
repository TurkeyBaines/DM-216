package com.dm.net.packet.in;

import com.dm.content.achievement.AchievementHandler;
import com.dm.content.activity.Activity;
import com.dm.content.itemaction.impl.CelestialRing;
import com.dm.game.event.impl.ItemClickEvent;
import com.dm.game.plugin.PluginManager;
import com.dm.game.world.InterfaceConstants;
import com.dm.game.world.entity.mob.data.PacketType;
import com.dm.game.world.entity.mob.player.Player;
import com.dm.game.world.entity.mob.player.PlayerRight;
import com.dm.game.world.items.Item;
import com.dm.game.world.items.containers.equipment.EquipmentType;
import com.dm.net.codec.ByteModification;
import com.dm.net.packet.GamePacket;
import com.dm.net.packet.PacketListener;
import com.dm.net.packet.PacketListenerMeta;
import com.dm.net.packet.out.SendMessage;
import com.dm.util.MessageColor;

/**
 * The {@code GamePacket} responsible for wielding items.
 *
 * @author Daniel | Obey
 */
@PacketListenerMeta(41)
public class WieldItemPacketListener implements PacketListener {

    /** Array of all the max cape identification. */
    private static final int[] MAX_CAPE_AND_HOOD = {
            13280, 13337, 13333, 13335, 13329, 20760, 13331, 21285, 13281, 13330,
            13332, 13334, 13336, 13338, 20764, 21282
    };

    @Override
    public void handlePacket(Player player, GamePacket packet) {
        if (player.locking.locked(PacketType.WIELD_ITEM))
            return;

        final int wearId = packet.readShort();
        final int wearSlot = packet.readShort(ByteModification.ADD);
        final int interfaceId = packet.readShort(ByteModification.ADD);

        switch (interfaceId) {

            case InterfaceConstants.INVENTORY_INTERFACE:
                final Item item = player.inventory.get(wearSlot);

                if (item == null || item.getId() != wearId) {
                    return;
                }

                if(PluginManager.getDataBus().publish(player, new ItemClickEvent(4, item, wearSlot))) {
                    return;
                }

                if (!item.isEquipable()) {
                    return;
                }

                if (Activity.evaluate(player, it -> !it.canEquipItem(player, item, item.getEquipmentType()))) {
                    return;
                }

                if (!player.interfaceManager.isClear() && !player.interfaceManager.isInterfaceOpen(15106)) {
                    player.interfaceManager.close(false);
                }

                if (player.right.equals(PlayerRight.OWNER)) {
                    player.send(new SendMessage("[WearItem] - [id= " + wearId + "] [slot= " + wearSlot + "] [itemcontainer " + interfaceId + "]", MessageColor.DEVELOPER));
                }

                if (item.getEquipmentType() == EquipmentType.NOT_WIELDABLE) {
                    player.send(new SendMessage("This item cannot be worn."));
                    return;
                }

                for (int maxCape : MAX_CAPE_AND_HOOD) {
                    if (item.getId() == maxCape && !player.skills.isMaxed()) {
                        player.message("You can not wield this item until you have 99 in all your skills!");
                        return;
                    }
                }

                if (item.getId() == CelestialRing.CHARGED_RING) {
                    CelestialRing.check(player);
                    return;
                }

                if (item.getId() == 13069 || item.getId() == 13070) {
                    if (!AchievementHandler.completedAll(player)) {
                        player.send(new SendMessage("You need to have completed all the achievements to wear this."));
                        return;
                    }
                }

                player.equipment.equip(wearSlot);
        }
    }
}
