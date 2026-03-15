package plugin.click.npc;

import com.dm.content.skill.impl.slayer.SlayerOfferings;
import com.dm.game.event.impl.NpcClickEvent;
import com.dm.game.plugin.PluginContext;
import com.dm.game.world.entity.mob.player.Player;

public class NpcThirdClickPlugin extends PluginContext {

    @Override
    protected boolean thirdClickNpc(Player player, NpcClickEvent event) {
        switch (event.getNpc().id) {
        }
        return false;
    }

}
