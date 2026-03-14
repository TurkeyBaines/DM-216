package plugin.click.button;

import com.dm.content.collectionlog.CollectionLog;
import com.dm.game.plugin.PluginContext;
import com.dm.game.world.entity.mob.player.Player;

public class CollectionLogButtonPlugin extends PluginContext {
    @Override
    protected boolean onClick(Player player, int button) {
        if(CollectionLog.clickButton(player, button))
            return true;

        switch (button) {

        }
        return false;
    }
}
