package plugin.click.object.wintertodt;

import com.dm.game.event.impl.ObjectClickEvent;
import com.dm.game.plugin.PluginContext;
import com.dm.game.world.entity.mob.player.Player;
import com.dm.game.world.object.GameObject;
import com.dm.content.wintertodt.Wintertodt;
public class LightBrazierPlugin extends PluginContext {

    @Override
    protected boolean firstClickObject(Player player, ObjectClickEvent event) {
        final GameObject gameObject = event.getObject();
        final int objectId = gameObject.getId();

        if(objectId != Wintertodt.EMPTY_BRAZIER_ID) return false;

        Wintertodt.lightBrazier(player, gameObject);

        return true;
    }

}