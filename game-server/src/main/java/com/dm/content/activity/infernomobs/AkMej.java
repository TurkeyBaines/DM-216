package com.dm.content.activity.infernomobs;

import com.dm.game.world.entity.combat.attack.FightType;
import com.dm.game.world.entity.combat.hit.CombatHit;
import com.dm.game.world.entity.combat.strategy.npc.MultiStrategy;
import com.dm.game.world.entity.combat.strategy.npc.NpcMagicStrategy;
import com.dm.game.world.entity.mob.Mob;
import com.dm.game.world.entity.mob.npc.Npc;

import static com.dm.game.world.entity.combat.projectile.CombatProjectile.getDefinition;

public class AkMej extends MultiStrategy {

	public AkMej() {
		currentStrategy = new Mage();
	}

	@Override
	public int getAttackDelay(Npc attacker, Mob defender, FightType fightType) {
		return attacker.definition.getAttackDelay();
	}

	private static class Mage extends NpcMagicStrategy {

		private Mage() {
			super(getDefinition("jalak mej"));
		}

		@Override
		public CombatHit[] getHits(Npc attacker, Mob defender) {
			return new CombatHit[] { nextRangedHit(attacker, defender, 18) };
		}
	}

}
