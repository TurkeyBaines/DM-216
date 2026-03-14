package plugin.click.item;

import com.dm.content.activity.Activity;
import com.dm.content.consume.PotionData;
import com.dm.game.Animation;
import com.dm.game.UpdatePriority;
import com.dm.game.event.impl.ItemClickEvent;
import com.dm.game.plugin.PluginContext;
import com.dm.game.world.entity.mob.player.Player;
import com.dm.game.world.items.Item;

import java.util.Optional;

public class DrinkPotionPlugin extends PluginContext {

    @Override
    protected boolean firstClickItem(Player player, ItemClickEvent event) {
        Optional<PotionData> potion = PotionData.forId(event.getItem().getId());

        if (!potion.isPresent() || player.isDead() || !player.potionDelay.elapsed(1600)) {
            return false;
        }

        if (Activity.evaluate(player, it -> !it.canDrinkPotions(player))) {
            return true;
        }

        if (!potion.get().canDrink(player)) {
            return true;
        }

        if (!player.interfaceManager.isClear()) {
            player.interfaceManager.close(false);
        }

        player.animate(new Animation(829, UpdatePriority.LOW));
        player.potionDelay.reset();

        Item replace = PotionData.getReplacementItem(event.getItem());

        if (replace.getId() == 229) {
            player.inventory.remove(event.getItem());
        } else {
            player.inventory.replace(event.getItem().getId(), replace.getId(), event.getSlot(), true);
        }
        potion.get().onEffect(player);
        return true;
    }


}
