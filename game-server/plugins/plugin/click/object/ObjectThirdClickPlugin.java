package plugin.click.object;

import com.dm.game.event.impl.ObjectClickEvent;
import com.dm.game.plugin.PluginContext;
import com.dm.game.world.entity.mob.player.Player;

public class ObjectThirdClickPlugin extends PluginContext {

    @Override
    protected boolean thirdClickObject(Player player, ObjectClickEvent event) {
        final int id = event.getObject().getId();

        switch (id) {

        }

        return false;
    }

}
