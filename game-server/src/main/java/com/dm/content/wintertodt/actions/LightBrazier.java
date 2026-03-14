package com.dm.content.wintertodt.actions;

import com.dm.Config;
import com.dm.content.wintertodt.Brazier;
import com.dm.content.wintertodt.Wintertodt;
import com.dm.game.action.Action;
import com.dm.game.action.policy.WalkablePolicy;
import com.dm.game.world.entity.mob.player.Player;
import com.dm.game.world.entity.skill.Skill;

public class LightBrazier extends Action<Player> {

    private Brazier brazier;

    public LightBrazier(Player player, Brazier brazier) {
        super(player, 3);
        this.brazier = brazier;
    }

    @Override
    public WalkablePolicy getWalkablePolicy() {
        return WalkablePolicy.NON_WALKABLE;
    }

    @Override
    public String getName() {
        return "Light brazier";
    }

    @Override
    public void execute() {
        if(brazier.getBrazierState() != 2) {
            //brazier.getObject().transform(Wintertodt.BURNING_BRAZIER_ID);
            brazier.getObject().unregister();
            brazier.setObject(Wintertodt.BURNING_BRAZIER_ID);
            brazier.getObject().register();
        }

        getMob().skills.addExperience(Skill.FIREMAKING, (Skill.getLevelForExperience(getMob().skills.get(Skill.FIREMAKING).getExperience()) * 6) * Config.FIREMAKING_MODIFICATION);
        Wintertodt.addPoints(getMob(), 25);
        getMob().animate(65535);
        getMob().action.getCurrentAction().cancel();
    }
}