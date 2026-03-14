package com.dm.content.skill.impl.hunter.birdhouse.action;

import com.dm.content.skill.impl.hunter.birdhouse.BirdhouseData;
import com.dm.game.action.Action;
import com.dm.game.action.policy.WalkablePolicy;
import com.dm.game.world.entity.mob.player.Player;
import com.dm.game.world.entity.skill.SkillData;
import com.dm.game.world.items.Item;

public class CreateBirdHouse extends Action<Player> {

    private int tick;
    private BirdhouseData birdHouseData;
    public CreateBirdHouse(Player player, BirdhouseData birdHouseData) {
        super(player, 1);
        this.birdHouseData = birdHouseData;
    }

    @Override
    public WalkablePolicy getWalkablePolicy() {
        return WalkablePolicy.WALKABLE;
    }

    @Override
    public String getName() {
        return "Create birdhouse";
    }

    @Override
    public void execute() {
        if(tick == 1) {
            getMob().inventory.remove(new Item(8792));
            getMob().inventory.remove(new Item(birdHouseData.logId));
            getMob().inventory.add(new Item(birdHouseData.birdHouseId));

            getMob().skills.addExperience(SkillData.CRAFTING.ordinal(), birdHouseData.craftingData[1]);

            getMob().inventory.refresh();

            getMob().action.getCurrentAction().cancel();
        }
        tick++;
    }

}
