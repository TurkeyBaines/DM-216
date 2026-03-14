package com.dm.game.world.entity.combat.strategy.npc.boss.scorpia;

import com.dm.game.world.entity.combat.strategy.npc.NpcMeleeStrategy;
import com.dm.game.world.entity.mob.Mob;
import com.dm.game.world.entity.mob.npc.Npc;

public class ScorpiaGuardian extends NpcMeleeStrategy {

    @Override
    public boolean canAttack(Npc attacker, Mob defender) {
        return false;
    }

}
