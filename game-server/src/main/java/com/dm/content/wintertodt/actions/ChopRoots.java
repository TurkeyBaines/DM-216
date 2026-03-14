package com.dm.content.wintertodt.actions;

import com.dm.Config;
import com.dm.content.skill.impl.woodcutting.AxeData;
import com.dm.content.wintertodt.Wintertodt;
import com.dm.game.action.Action;
import com.dm.game.action.policy.WalkablePolicy;
import com.dm.game.world.entity.mob.player.Player;
import com.dm.game.world.entity.skill.Skill;
import com.dm.game.world.items.Item;

public class ChopRoots extends Action<Player> {

    int tick;
    public ChopRoots(Player player) {
        super(player, 1);
    }

    @Override
    public WalkablePolicy getWalkablePolicy() {
        return WalkablePolicy.NON_WALKABLE;
    }

    @Override
    public String getName() {
        return "Chop roots";
    }

    @Override
    public void execute() {
        AxeData axeData = AxeData.getDefinition(getMob()).orElse(null);

        if (axeData == null) {
            getMob().message("You need an axe to chop this tree.");
            getMob().animate(65535);
            getMob().action.getCurrentAction().cancel();
            return;
        }

        if(getMob().inventory.getFreeSlots() <= 0) {
            getMob().message("You have no space for that.");
            getMob().animate(65535);
            getMob().action.getCurrentAction().cancel();
            return;
        }

        getMob().animate(axeData.animation);

        if(tick % 3 == 0) {
            getMob().inventory.add(new Item(Wintertodt.BRUMA_ROOT));
            getMob().inventory.refresh();
            getMob().message("You get a bruma root.");
            double xp = Skill.getLevelForExperience(getMob().skills.get(Skill.WOODCUTTING).getExperience()) * 0.3;
            if(xp > 0) getMob().skills.addExperience(Skill.WOODCUTTING, xp * Config.WOODCUTTING_MODIFICATION);
        }
        tick++;
    }
}
