package com.dm.content.itemaction.impl;

import com.dm.content.clanchannel.ClanUtility;
import com.dm.content.clanchannel.channel.ClanChannel;
import com.dm.content.clanchannel.content.ClanLevel;
import com.dm.content.itemaction.ItemAction;
import com.dm.game.world.entity.mob.player.Player;
import com.dm.game.world.items.Item;
import com.dm.net.packet.out.SendMessage;
import com.dm.util.Utility;

import java.util.ArrayList;
import java.util.List;

public class ClanShowcaseBox extends ItemAction {

    @Override
    public String name() {
        return "Clan Showcase Box";
    }

    @Override
    public boolean inventory(Player player, Item item, int opcode) {
        if (opcode != 1) {
            return false;
        }
        ClanChannel channel = player.clanChannel;
        if (channel == null) {
            player.send(new SendMessage("You need to be in a clan to do this!"));
            return true;
        }
        if (channel.getShowcase().showcaseItems.size() >= 28) {
            player.send(new SendMessage("You have reached the maximum capacity of showcase items you can hold. Please delete an item to proceed."));
            return true;
        }
        ClanLevel level = channel.getDetails().level;
        List<Item> items = new ArrayList<>();

        for (int reward : ClanUtility.getRewardItems(level)) {
            Item rewardItem = new Item(reward, 1);
            for (Item showcase : channel.getShowcaseItems()) {
                if (rewardItem.getId() != showcase.getId())
                    items.add(rewardItem);
            }
        }

        if (items.isEmpty()) {
            return true;
        }

        Item showcaseReward = Utility.randomElement(items);
        player.inventory.remove(item);
        channel.getShowcase().showcaseItems.add(showcaseReward.getId());
        channel.message("We just received " + showcaseReward.getName() + " from the showcase box!");
        return true;
    }
}
