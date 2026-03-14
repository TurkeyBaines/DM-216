package com.dm.content.dailyeffect.impl;

import com.dm.content.dailyeffect.DailyEffect;
import com.dm.game.world.entity.mob.player.Player;

public class DailySlayerTaskSkip extends DailyEffect {

    @Override
    public int maxUses(Player player) {
        return 2;
    }
}
