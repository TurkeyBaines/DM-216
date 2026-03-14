package plugin.click.button;

import com.dm.content.teleport.TeleportHandler;
import com.dm.game.plugin.PluginContext;
import com.dm.game.world.entity.mob.player.Player;

public class PreviousTeleportButtonPlugin extends PluginContext {
    @Override
    protected boolean onClick(Player player, int button) {
        if (button != 155) {
            return false;
        }
        if (player.lastTeleport == null) {
            player.message("You have not teleported anywhere yet.");
            return false;
        }
        player.attributes.set("TELEPORT", player.lastTeleport);
        TeleportHandler.teleport(player);
        return true;
    }
}
