package plugin.click.button;

import com.dm.game.plugin.PluginContext;
import com.dm.game.world.entity.mob.player.Player;

public class PuzzlePlugin extends PluginContext{

    @Override
    protected boolean onClick(Player player, int button) {
        return player.puzzle.click(button);
    }
}
