package plugin.click.button;

import com.dm.content.ItemsKeptOnDeath;
import com.dm.game.plugin.PluginContext;
import com.dm.game.world.entity.mob.player.Player;

public class EquipmentButtonPlugin extends PluginContext {

    @Override
    protected boolean onClick(Player player, int button) {
        if (button == 27653) {
            player.equipment.openInterface();
            return true;
        }
        if (button == 27654) {
            ItemsKeptOnDeath.open(player);
            return true;
        }
        return false;
    }
}