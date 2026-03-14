package plugin.click.object.wintertodt;

import com.dm.content.skill.impl.woodcutting.AxeData;
import com.dm.game.event.impl.ObjectClickEvent;
import com.dm.game.plugin.PluginContext;
import com.dm.game.world.entity.mob.player.Player;
import com.dm.game.world.items.Item;
import com.dm.game.world.object.GameObject;
public class TakeAxePlugin extends PluginContext {

    @Override
    protected boolean firstClickObject(Player player, ObjectClickEvent event) {
        final GameObject gameObject = event.getObject();
        final int objectId = gameObject.getId();

        if(objectId != 29318) return false;

        if(AxeData.getDefinition(player).orElse(null) != null) {
            player.message("You already have a axe.");
            return true;
        }

        if(!player.inventory.hasCapacityFor(new Item(1351))) {
            player.message("You need space in your inventory to take a axe.");
            return true;
        }

        player.message("You take a axe from the crate.");
        player.inventory.add(1351, 1);
        player.inventory.refresh();

        return true;
    }

}