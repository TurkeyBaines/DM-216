package plugin.click.item;

import com.dm.content.consume.PotionData;
import com.dm.game.event.impl.ItemClickEvent;
import com.dm.game.plugin.PluginContext;
import com.dm.game.world.entity.mob.player.Player;
import com.dm.net.packet.out.SendMessage;

public class EmptyPotionPlugin extends PluginContext {

    @Override
    protected boolean thirdClickItem(Player player, ItemClickEvent event) {
        if (PotionData.forId(event.getItem().getId()).isPresent()) {
            player.inventory.replace(event.getItem().getId(), 229, true);
            player.send(new SendMessage("You have poured out the remaining dose(s) of " + event.getItem().getName() + ".", true));
            return true;
        }
        return false;
    }

}
