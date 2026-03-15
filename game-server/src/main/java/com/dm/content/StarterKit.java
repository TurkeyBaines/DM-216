package com.dm.content;

import com.dm.game.world.entity.mob.UpdateFlag;
import com.dm.game.world.entity.mob.player.Player;
import com.dm.game.world.entity.mob.player.PlayerRight;
import com.dm.game.world.items.Item;
import com.dm.net.packet.out.SendConfig;
import com.dm.net.packet.out.SendItemOnInterface;
import com.dm.net.packet.out.SendRunEnergy;
import com.dm.net.packet.out.SendString;

import java.util.Arrays;

/**
 * Handles the starter kits.
 *
 * @author Daniel
 */
public class StarterKit {
    /** Handles opening the starter kit interface. */
    public static void open(Player player) {
        player.locking.lock();
        refresh(player, KitData.NORMAL);
        player.interfaceManager.open(45000);
    }

    /** Handles refreshing the starter kit interface. */
    public static void refresh(Player player, StarterKit.KitData kit) {
        player.attributes.set("STARTER_KEY", kit);
        for (int index = 0, string = 45004; index < 4; index++, string += 1) {
            String desc = index >= kit.getDescription().length ? "" : kit.getDescription()[index];
            player.send(new SendString(desc, string));
        }

        player.equipment.clear();
        player.movement.setRunningToggled(true);
        player.send(new SendRunEnergy());
        if (kit.equipment == null) {
            player.equipment.clear();
            player.updateFlags.add(UpdateFlag.APPEARANCE);
        } else {
            Arrays.stream(kit.getEquipment()).forEach(player.equipment::manualWear);
        }

        player.equipment.refresh();
        player.send(new SendConfig(1085, kit.ordinal()));
        player.send(new SendItemOnInterface(45021, kit.getItems()));
    }

    /** Holds the starter kit data. */
    public enum KitData {
        NORMAL(PlayerRight.PLAYER, new String[]{
                "Play Dead Men as a casual player."
        }, null,
                new Item(995, Integer.MAX_VALUE)
        );

        /** The player right of the starter kit. */
        private final PlayerRight right;

        /** The starter kit description. */
        private final String[] description;

        /** The starter kit equipment items. */
        private final Item[] equipment;

        /** The starter kit items. */
        private final Item[] items;

        /** Constructs a new <code>KitData</code>. */
        KitData(PlayerRight right, String[] description, Item[] equipment, Item... items) {
            this.right = right;
            this.description = description;
            this.equipment = equipment;
            this.items = items;
        }

        public PlayerRight getRight() {
            return right;
        }

        public String[] getDescription() {
            return description;
        }

        public Item[] getEquipment() {
            return equipment;
        }

        public Item[] getItems() {
            return items;
        }

    }

}
