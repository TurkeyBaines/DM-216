package plugin.click.object.wintertodt;

import com.dm.content.wintertodt.Wintertodt;
import com.dm.game.event.impl.ObjectClickEvent;
import com.dm.game.plugin.PluginContext;
import com.dm.game.world.entity.mob.player.Player;
import com.dm.game.world.object.GameObject;

public class FixBrazierPlugin extends PluginContext {

    @Override
    protected boolean firstClickObject(Player player, ObjectClickEvent event) {
        final GameObject gameObject = event.getObject();
        final int objectId = gameObject.getId();

        if(objectId != Wintertodt.BROKEN_BRAZIER_ID) return false;

        Wintertodt.fixBrazier(player, gameObject);

        return true;
    }

}