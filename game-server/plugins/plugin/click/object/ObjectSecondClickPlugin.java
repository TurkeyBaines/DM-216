package plugin.click.object;

import com.dm.content.Obelisks;
import com.dm.content.WellOfGoodwill;
import com.dm.content.dialogue.DialogueFactory;
import com.dm.content.store.impl.PersonalStore;
import com.dm.game.action.impl.FlaxPickingAction;
import com.dm.game.event.impl.ObjectClickEvent;
import com.dm.game.plugin.PluginContext;
import com.dm.game.world.World;
import com.dm.game.world.entity.mob.player.Player;
import com.dm.game.world.entity.mob.player.PlayerRight;
import com.dm.game.world.object.GameObject;
import com.dm.net.packet.out.SendMessage;

public class ObjectSecondClickPlugin extends PluginContext {

    @Override
    protected boolean secondClickObject(Player player, ObjectClickEvent event) {
        final GameObject object = event.getObject();
        final int id = object.getId();
        switch (id) {

            case 26760:
                player.message("There are " + World.get().getWildernessResourcePlayers() + " players in the wilderness resource area.");
                break;

            case 14826:
            case 14827:
            case 14828:
            case 14829:
            case 14830:
            case 14831:
                Obelisks.get().open(player, id);
                break;
            case 10060:
            case 30389:
                player.bank.open();
                break;
            /* Well */
            case 884:
                WellOfGoodwill.open(player);
                break;

        /* Flax picking. */
            case 14896:
                if (player.inventory.remaining() == 0) {
                    player.dialogueFactory.sendStatement("You do not have enough inventory spaces to do this!").execute();
                    break;
                }
                player.action.execute(new FlaxPickingAction(player, object), true);
                break;

         /* Grand exchange. */
            case 26044: {
                DialogueFactory f = player.dialogueFactory;
                f.sendOption("Browse all stores", () -> f.onAction(() -> {
                    PersonalStore.openPanel(player);
                }), "Open my shop", () -> f.onAction(() -> {
                    PersonalStore.myShop(player);
                }), "Edit my shop", () -> f.onAction(() -> {
                    PersonalStore.edit(player);
                }), "Collect coins", () -> f.onAction(() -> {
                    //TODO
                })).execute();
            }
            break;

            default:
                return false;

        }
        return true;
    }

}
