package com.dm.game.world.entity.combat.attack.listener.other.prayer.ranged;

import com.dm.game.world.entity.combat.attack.listener.SimplifiedListener;
import com.dm.game.world.entity.mob.Mob;

public class HawkEyeListener extends SimplifiedListener<Mob> {

    @Override
    public int modifyRangedLevel(Mob attacker, Mob defender, int damage) {
        return damage * 11 / 10;
    }

}
