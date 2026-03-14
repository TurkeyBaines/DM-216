package plugin.click.item;

import com.dm.content.skill.impl.magic.teleport.Teleportation;
import com.dm.content.skill.impl.magic.teleport.TeleportationData;
import com.dm.content.teleport.TeleportTablet;
import com.dm.game.event.impl.ItemClickEvent;
import com.dm.game.plugin.PluginContext;
import com.dm.game.world.entity.mob.player.Player;
import com.dm.game.world.items.Item;
import com.dm.net.packet.out.SendMessage;

public class TeletabPlugin extends PluginContext {

    @Override
    protected boolean firstClickItem(Player player, ItemClickEvent event) {
        if (!TeleportTablet.forId(event.getItem().getId()).isPresent()) {
            return false;
        }

        final TeleportTablet tablet = TeleportTablet.forId(event.getItem().getId()).get();

        if (player.house.isInside()) {
            player.send(new SendMessage("Please leave the house before teleporting."));
            return true;
        }

        player.inventory.remove(new Item(event.getItem().getId(), 1));
        Teleportation.teleport(player, tablet.getPosition(), 20, TeleportationData.TABLET);
        return true;
    }
}
