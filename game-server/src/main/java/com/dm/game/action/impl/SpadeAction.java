package com.dm.game.action.impl;

import com.dm.content.activity.impl.barrows.BarrowsUtility;
import com.dm.game.Animation;
import com.dm.game.action.Action;
import com.dm.game.action.policy.WalkablePolicy;
import com.dm.game.world.entity.mob.player.Player;
import com.dm.net.packet.out.SendMessage;

/**
 * Handles digging with a spade.
 *
 * @author Daniel.
 */
public final class SpadeAction extends Action<Player> {

    public SpadeAction(Player player) {
        super(player, 2, false);
    }

    @Override
    public void onSchedule() {
        getMob().movement.reset();
        getMob().animate(new Animation(830));
        getMob().send(new SendMessage("You start digging..."));
    }

    @Override
    public void execute() {
        boolean found = false;

        if (BarrowsUtility.teleportPlayer(getMob()))
            found = true;

        if (!found)
            getMob().send(new SendMessage("You found nothing of interest."));
        cancel();
    }

    @Override
    public WalkablePolicy getWalkablePolicy() {
        return WalkablePolicy.NON_WALKABLE;    }


    @Override
    public boolean prioritized() {
        return false;
    }

    @Override
    public String getName() {
        return "Spade Action";
    }
}