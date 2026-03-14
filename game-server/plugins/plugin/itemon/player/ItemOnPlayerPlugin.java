package plugin.itemon.player;

import com.dm.game.event.impl.ItemOnPlayerEvent;
import com.dm.game.plugin.PluginContext;
import com.dm.game.world.entity.mob.player.Player;

public class ItemOnPlayerPlugin extends PluginContext {

    @Override
    protected boolean itemOnPlayer(Player player, ItemOnPlayerEvent event) {
        return false;
    }

}
