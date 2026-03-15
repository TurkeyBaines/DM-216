package plugin.click.object;

import com.dm.game.event.impl.ObjectClickEvent;
import com.dm.game.plugin.PluginContext;
import com.dm.game.world.entity.mob.player.Player;
import com.dm.game.world.object.GameObjectDefinition;

public class OpenBankObjectClickPlugin extends PluginContext {

    @Override
    protected boolean firstClickObject(Player player, ObjectClickEvent event) {
        final GameObjectDefinition def = event.getObject().getDefinition();

        if (def == null || def.getName() == null) {
            return false;
        }

        final String name = def.getName().toLowerCase();

        if (name.contains("bank booth") || name.contains("clan bank") || name.equals("open chest") || name.equals("bank chest")) {
            player.bank.open();
            return true;
        }

        return false;
    }

    @Override
    protected boolean secondClickObject(Player player, ObjectClickEvent event) {
        final GameObjectDefinition def = event.getObject().getDefinition();

        if (def == null || def.getName() == null) {
            return false;
        }

        final String name = def.getName().toLowerCase();

        return false;
    }

}
