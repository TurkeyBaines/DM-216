package com.dm.content.skill.impl.magic.spell.impl;

import com.dm.Config;
import com.dm.content.activity.randomevent.RandomEventHandler;
import com.dm.content.skill.impl.magic.Magic;
import com.dm.content.skill.impl.magic.Spellbook;
import com.dm.content.skill.impl.magic.spell.Spell;
import com.dm.game.Animation;
import com.dm.game.Graphic;
import com.dm.game.world.entity.mob.player.Player;
import com.dm.game.world.entity.skill.Skill;
import com.dm.game.world.items.Item;
import com.dm.net.packet.out.SendForceTab;
import com.dm.net.packet.out.SendMessage;

import java.util.Arrays;

/**
 * The high alchemy spell.
 * 
 * @author Daniel
 */
public class LowAlchemy implements Spell {

	@Override
	public String getName() {
		return "Low alchemy";
	}

	@Override
	public int getLevel() {
		return 21;
	}

	@Override
	public Item[] getRunes() {
		return new Item[] { new Item(554, 3), new Item(561, 1) };
	}

	@Override
	public void execute(Player player, Item item) {
		if (player.spellbook != Spellbook.MODERN)
			return;

        if (!player.spellCasting.castingDelay.elapsed(500)) {
            return;
		}

		if (Arrays.stream(Magic.UNALCHABLES).anyMatch($it -> item.getId() == $it.getId())) {
			player.send(new SendMessage("You can not alch this item!"));
			return;
		}

		int value = item.getLowAlch();

		if (value > 150_000) {
			player.send(new SendMessage("The value of this item is too high and can not be low-alched."));
			return;
		}

		player.animate(new Animation(712));
		player.graphic(new Graphic(112, true));
		player.inventory.remove(item.getId(), 1);
		player.inventory.removeAll(getRunes());
		player.inventory.refresh();
		player.send(new SendForceTab(6));
		player.inventory.add(Config.CURRENCY, value == 0 ? 1 : value);
		player.skills.addExperience(Skill.MAGIC, 31 * (Config.MAGIC_MODIFICATION + 5));
        player.spellCasting.castingDelay.reset();
        player.action.clearNonWalkableActions();
		RandomEventHandler.trigger(player);
	}
}
