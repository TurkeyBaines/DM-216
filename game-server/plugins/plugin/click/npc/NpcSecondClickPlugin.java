package plugin.click.npc;

import com.dm.content.dialogue.DialogueFactory;
import com.dm.content.skill.impl.slayer.SlayerTab;
import com.dm.content.store.Store;
import com.dm.content.store.impl.RecipeForDisasterStore;
import com.dm.game.event.impl.NpcClickEvent;
import com.dm.game.plugin.PluginContext;
import com.dm.game.world.entity.mob.player.Player;
import com.dm.game.world.entity.mob.player.PlayerRight;

public class NpcSecondClickPlugin extends PluginContext {

    @Override
    protected boolean secondClickNpc(Player player, NpcClickEvent event) {
        final int id = event.getNpc().id;
        switch (id) {
            case 6526:
                new RecipeForDisasterStore().open(player);
                break;
            case 3089:
            case 1634:
            case 1633:
            case 1613:
                player.bank.open();
            break;
            case 2148:
            case 2149:
            case 2150:
            case 2151:
                player.tradingPost.openOverviewInterface();
                break;
            case 7481:
                Store.STORES.get("Dead Men Vote Store").open(player);
                break;

            case 1603:
                Store.STORES.get("Kolodion's Arena Store").open(player);
                break;

//            case 506:
//            case 507:
//            case 513: // falador female
//            case 512: // falador male
//            case 1032:
//                Store.STORES.get("The General Store").open(player);
//                break;

		/* Nieve */
            case 6797:
                player.slayer.open(SlayerTab.MAIN);
                break;

        /* Zeke */
            case 527:
                Store.STORES.get("Zeke's Superior Scimitars").open(player);
                break;
        }
        return false;
    }

}
