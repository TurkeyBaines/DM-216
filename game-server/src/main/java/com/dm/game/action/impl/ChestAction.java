package com.dm.game.action.impl;

import com.dm.content.ActivityLog;
import com.dm.content.achievement.AchievementHandler;
import com.dm.content.achievement.AchievementKey;
import com.dm.game.Animation;
import com.dm.game.action.Action;
import com.dm.game.action.policy.WalkablePolicy;
import com.dm.game.world.entity.mob.player.Player;
import com.dm.game.world.items.Item;
import com.dm.net.packet.out.SendMessage;

/**
 * Handles opening a chest.
 *
 * @author Daniel
 */
public final class ChestAction extends Action<Player> {
    private final int key;
    private final Item[] items;

    public ChestAction(Player player, int key, Item... items) {
        super(player, 1);
        this.key = key;
        this.items = items;
    }

    @Override
    public boolean canSchedule() {
        if (!getMob().inventory.hasCapacityFor(items)) {
            getMob().message("You do not have enough free inventory spaces to do this!");
            return false;
        }
        return true;
    }

    @Override
    public void onSchedule() {
        getMob().locking.lock(2);
        getMob().inventory.remove(key, 1);
        getMob().animate(new Animation(881));
        getMob().send(new SendMessage("You attempt to unlock the chest..."));
    }

    @Override
    public void execute() {
        cancel();
    }

    @Override
    public void onCancel(boolean logout) {
        getMob().inventory.addOrDrop(items);
        getMob().send(new SendMessage("...you find a few items inside of the chest.", true));

        if (key == 989) {
            getMob().activityLogger.add(ActivityLog.CRYSTAL_CHEST);
            AchievementHandler.activate(getMob(), AchievementKey.CRYSTAL_CHEST, 1);
        } else if (key == 20608) {
            getMob().activityLogger.add(ActivityLog.BLOOD_MONEY_CHEST);
        }
    }

    @Override
    public String getName() {
        return "chest action";
    }

    @Override
    public boolean prioritized() {
        return false;
    }

    @Override
    public WalkablePolicy getWalkablePolicy() {
        return WalkablePolicy.NON_WALKABLE;
    }
}