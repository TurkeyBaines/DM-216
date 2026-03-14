package com.dm.game.world.entity.combat.strategy.npc.boss.kril;

import com.dm.game.world.entity.combat.hit.CombatHit;
import com.dm.game.world.entity.combat.strategy.npc.NpcMeleeStrategy;
import com.dm.game.world.entity.mob.Mob;
import com.dm.game.world.entity.mob.npc.Npc;

public class Tstanon extends NpcMeleeStrategy {

    @Override
    public CombatHit[] getHits(Npc attacker, Mob defender) {
        return new CombatHit[] { nextMeleeHit(attacker, defender, 15) };
    }

}
