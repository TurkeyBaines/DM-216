package com.dm.content.wintertodt;

import com.dm.game.action.Action;
import com.dm.game.action.policy.WalkablePolicy;
import com.dm.game.world.entity.mob.player.Player;

public class WintertodtAction extends Action<Player> {

    public WintertodtAction(Player player) {
        super(player, 1);
    }

    @Override
    public WalkablePolicy getWalkablePolicy() {
        return WalkablePolicy.NON_WALKABLE;
    }

    @Override
    public String getName() {
        return "Wintertodt interuptable action";
    }

    @Override
    public void execute() {

    }
}
