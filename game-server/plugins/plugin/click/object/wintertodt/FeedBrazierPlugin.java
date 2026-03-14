package plugin.click.object.wintertodt;

import com.dm.content.wintertodt.Wintertodt;
import com.dm.game.event.impl.ObjectClickEvent;
import com.dm.game.plugin.PluginContext;
import com.dm.game.world.entity.mob.player.Player;
import com.dm.game.world.object.GameObject;

public class FeedBrazierPlugin extends PluginContext {

    @Override
    protected boolean firstClickObject(Player player, ObjectClickEvent event) {
        final GameObject gameObject = event.getObject();
        final int objectId = gameObject.getId();

        if(objectId != Wintertodt.BURNING_BRAZIER_ID) return false;

        Wintertodt.feedBrazier(player, gameObject);

        return true;
    }

}
