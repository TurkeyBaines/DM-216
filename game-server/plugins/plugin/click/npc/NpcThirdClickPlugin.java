package plugin.click.npc;

import com.dm.content.dialogue.impl.RoyalKingDialogue;
import com.dm.content.skill.impl.slayer.SlayerOfferings;
import com.dm.game.event.impl.NpcClickEvent;
import com.dm.game.plugin.PluginContext;
import com.dm.game.world.entity.mob.player.Player;

public class NpcThirdClickPlugin extends PluginContext {

    @Override
    protected boolean thirdClickNpc(Player player, NpcClickEvent event) {
        switch (event.getNpc().id) {
            case 5523:
                player.dialogueFactory.sendDialogue(new RoyalKingDialogue(1));
                break;
            case 6797:
                SlayerOfferings.offer(player);
                break;
            case 311:
                player.playerAssistant.claimIronmanArmour();
                break;
        }
        return false;
    }

}
