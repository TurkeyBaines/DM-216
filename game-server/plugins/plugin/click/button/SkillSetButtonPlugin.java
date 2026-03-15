package plugin.click.button;

import com.dm.content.SkillSet;
import com.dm.game.plugin.PluginContext;
import com.dm.game.world.entity.mob.UpdateFlag;
import com.dm.game.world.entity.mob.player.Player;
import com.dm.game.world.entity.mob.player.PlayerRight;
import com.dm.game.world.entity.skill.Skill;
import com.dm.game.world.position.Area;
import com.dm.net.packet.out.SendInputAmount;
import com.dm.net.packet.out.SendMessage;

public class SkillSetButtonPlugin extends PluginContext {

    @Override
    protected boolean onClick(Player player, int button) {
        //TODO - Add Prestige for Overworld
        return true;
    }
}
