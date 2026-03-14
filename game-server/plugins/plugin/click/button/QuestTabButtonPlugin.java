package plugin.click.button;

import com.dm.content.DropDisplay;
import com.dm.content.ProfileViewer;
import com.dm.content.achievement.AchievementWriter;
import com.dm.content.activity.ActivityType;
import com.dm.content.simulator.DropSimulator;
import com.dm.content.tittle.TitleManager;
import com.dm.content.writer.InterfaceWriter;
import com.dm.content.writer.impl.InformationWriter;
import com.dm.game.plugin.PluginContext;
import com.dm.game.world.entity.mob.player.Player;
import com.dm.net.packet.out.SendURL;

public class QuestTabButtonPlugin extends PluginContext {

    @Override
    protected boolean onClick(Player player, int button) {
        switch (button) {
            case -29408:
                AchievementWriter.write(new AchievementWriter(player));
                return true;

            case -29404:
                player.send(new SendURL("www.tarnishps.com"));
                return true;

            case 29411:
                InterfaceWriter.write(new InformationWriter(player));
                return true;

            case -30085:
                ProfileViewer.open(player, player);
                return true;

            case -30084:
                player.activityLogger.open();
                return true;

            case -30083:
                TitleManager.open(player);
                return true;

            case -30082:
                DropDisplay.open(player);
                return true;

            case -30081:
                DropSimulator.open(player);
                return true;

            case -30080:
                player.gameRecord.display(ActivityType.getFirst());
                return true;
        }
        return false;
    }
}
