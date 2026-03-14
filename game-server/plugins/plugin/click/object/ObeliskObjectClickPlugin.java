package plugin.click.object;

import com.dm.content.Obelisks;
import com.dm.game.event.impl.ObjectClickEvent;
import com.dm.game.plugin.PluginContext;
import com.dm.game.world.entity.mob.player.Player;

public class ObeliskObjectClickPlugin extends PluginContext {

    @Override
    protected boolean firstClickObject(Player player, ObjectClickEvent event) {
        return Obelisks.get().activate(player, event.getObject().getId());
    }

}
