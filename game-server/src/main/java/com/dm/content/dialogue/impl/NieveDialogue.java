package com.dm.content.dialogue.impl;

import com.dm.content.skill.impl.slayer.SlayerOfferings;
import com.dm.game.world.entity.mob.player.Player;
import com.dm.content.dialogue.Dialogue;
import com.dm.content.dialogue.DialogueFactory;
import com.dm.content.dialogue.Expression;
import com.dm.content.skill.impl.slayer.SlayerTab;

public class NieveDialogue extends Dialogue {


	@Override
	public void sendDialogues(DialogueFactory factory) {
		Player player = factory.getPlayer();

		factory.sendNpcChat(6797, Expression.HAPPY, "Hello adventurer, how may I help you?").sendOption("Offer slayer items", () -> sell(factory), "Open slayer manager", () -> factory.onAction(() -> player.slayer.open(SlayerTab.MAIN)), "Nevermind", () -> factory.onAction(player.interfaceManager::close));
		factory.execute();
	}

	private void sell(DialogueFactory factory) {
		factory.sendOption("What do I get for my offerings?", () -> {
			factory.sendNpcChat(6797, "You will get slayer experience and points!");
		}, "What items can be offered?", () -> {
			factory.sendStatement("Ensouled heads", "Ancient shards & Dark Totem pieces");
		}, "Offer all items", () -> {
			factory.onAction(() -> SlayerOfferings.offer(factory.getPlayer()));
		}, "Nevermind", factory::clear);
	}

}
