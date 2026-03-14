package com.dm.game.world.entity.combat.strategy.npc.boss.kril;

import com.dm.game.world.entity.combat.hit.CombatHit;
import com.dm.game.world.entity.combat.projectile.CombatProjectile;
import com.dm.game.world.entity.combat.strategy.npc.NpcMagicStrategy;
import com.dm.game.world.entity.mob.Mob;
import com.dm.game.world.entity.mob.npc.Npc;

public class Balfrug extends NpcMagicStrategy {

    public Balfrug() {
        super(CombatProjectile.getDefinition("EMPTY"));
    }

    @Override
    public CombatHit[] getHits(Npc attacker, Mob defender) {
        return new CombatHit[] { nextMagicHit(attacker, defender, 16) };
    }

}
