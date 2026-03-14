package plugin.click.object;

import com.dm.content.bot.BotUtility;
import com.dm.game.event.impl.ObjectClickEvent;
import com.dm.game.plugin.PluginContext;
import com.dm.game.world.entity.mob.player.Player;
import com.dm.game.world.items.Item;
import com.dm.net.packet.out.SendItemOnInterfaceSlot;
import com.dm.net.packet.out.SendString;
import com.dm.util.MutableNumber;
import com.dm.util.Utility;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class BotLootViewerPlugin extends PluginContext {

    @Override
    protected boolean firstClickObject(Player player, ObjectClickEvent event) {
        if (event.getObject().getId() != 24099)
            return false;

        long value = 0;

        List<Item> items = new LinkedList<>();

        for (Map.Entry<Integer, MutableNumber> entry : BotUtility.BOOT_LOOT.entrySet()) {
            int id = entry.getKey();
            int amount = entry.getValue().get();

            Item item = new Item(id, amount);
            items.add(item);
            value += item.getValue() * item.getAmount();
        }


        items.sort((first, second) -> second.getValue() - first.getValue());

        int index = 0;
        for (Item item : items) {
            player.send(new SendItemOnInterfaceSlot(37560, item, index++));
        }

        player.send(new SendString("Total value: " + Utility.formatPrice(value), 37553));
        player.send(new SendString("Total items: " + Utility.formatDigits(index), 37554));
        player.interfaceManager.open(37550);
        return true;
    }

}
