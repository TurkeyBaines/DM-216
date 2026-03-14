package plugin.click.object.wintertodt;

import com.dm.content.wintertodt.Wintertodt;
import com.dm.game.event.impl.ItemOnItemEvent;
import com.dm.game.plugin.PluginContext;
import com.dm.game.world.entity.mob.player.Player;
import com.dm.game.world.items.Item;
public class MixHerbPlugin extends PluginContext {

    @Override
    protected boolean itemOnItem(Player player, ItemOnItemEvent event) {
        final Item useWith = event.getWith();
        final Item itemUsed = event.getUsed();

        if (useWith.getId() == Wintertodt.BRUMA_HERB && itemUsed.getId() == Wintertodt.REJUV_POT_UNF || useWith.getId() == Wintertodt.REJUV_POT_UNF && itemUsed.getId() == Wintertodt.BRUMA_HERB) {
            Wintertodt.mixHerb(player);
            return true;
        }

        return false;
    }

}