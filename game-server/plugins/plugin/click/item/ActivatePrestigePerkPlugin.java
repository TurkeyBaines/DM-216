package plugin.click.item;

import com.dm.content.prestige.PrestigePerk;
import com.dm.game.event.impl.ItemClickEvent;
import com.dm.game.plugin.PluginContext;
import com.dm.game.world.entity.mob.player.Player;
import com.dm.net.packet.out.SendMessage;
import com.dm.util.MessageColor;

import java.util.HashSet;

public class ActivatePrestigePerkPlugin extends PluginContext {

    @Override
    protected boolean firstClickItem(Player player, ItemClickEvent event) {
        final PrestigePerk perk = PrestigePerk.forItem(event.getItem().getId());
        if (perk == null) {
            return false;
        }

        if (player.prestige.activePerks == null) {
            player.prestige.activePerks = new HashSet<>();
        }

        if (player.prestige.activePerks.contains(perk)) {
            player.send(new SendMessage("The Perk: " + perk.name + " perk is already active on your account!", MessageColor.DARK_BLUE));
            return true;
        }

        player.inventory.remove(event.getItem());
        player.prestige.activePerks.add(perk);
        player.send(new SendMessage("You have successfully activated the " + perk.name + " perk.", MessageColor.DARK_BLUE));
        return true;
    }
}
