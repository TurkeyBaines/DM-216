package com.dm.game.world.entity.combat.attack.listener.npc.godwar;

import com.dm.game.Animation;
import com.dm.game.UpdatePriority;
import com.dm.game.world.entity.combat.attack.listener.NpcCombatListenerSignature;
import com.dm.game.world.entity.combat.attack.listener.SimplifiedListener;
import com.dm.game.world.entity.combat.hit.Hit;
import com.dm.game.world.entity.combat.strategy.npc.NpcRangedStrategy;
import com.dm.game.world.entity.mob.Mob;
import com.dm.game.world.entity.mob.npc.Npc;

import static com.dm.game.world.entity.combat.CombatUtil.createStrategyArray;
import static com.dm.game.world.entity.combat.projectile.CombatProjectile.getDefinition;

/**
 * @author Daniel
 */
@NpcCombatListenerSignature(npcs = {3160, 2211})
public class SpritualRanger extends SimplifiedListener<Npc> {

    private static RangedAttack RANGED = new RangedAttack();

    @Override
    public void start(Npc attacker, Mob defender, Hit[] hits) {
        attacker.setStrategy(RANGED);
    }

    private static class RangedAttack extends NpcRangedStrategy {
        private RangedAttack() {
            super(getDefinition("Spirtual Ranger"));
        }

        @Override
        public Animation getAttackAnimation(Npc attacker, Mob defender) {
            return new Animation(426, UpdatePriority.VERY_HIGH);
        }
    }
}
