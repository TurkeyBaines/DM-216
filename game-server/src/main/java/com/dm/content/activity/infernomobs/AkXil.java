package com.dm.content.activity.infernomobs;

import com.dm.game.world.entity.combat.attack.FightType;
import com.dm.game.world.entity.combat.hit.CombatHit;
import com.dm.game.world.entity.combat.strategy.npc.MultiStrategy;
import com.dm.game.world.entity.combat.strategy.npc.NpcRangedStrategy;
import com.dm.game.world.entity.mob.Mob;
import com.dm.game.world.entity.mob.npc.Npc;

import static com.dm.game.world.entity.combat.projectile.CombatProjectile.getDefinition;

public class AkXil extends MultiStrategy {

	public AkXil() {
		currentStrategy = new Ranged();
	}

	@Override
	public int getAttackDelay(Npc attacker, Mob defender, FightType fightType) {
		return attacker.definition.getAttackDelay();
	}

	private static class Ranged extends NpcRangedStrategy {
		private Ranged() {
			super(getDefinition("jalak xil"));
		}

		@Override
		public CombatHit[] getHits(Npc attacker, Mob defender) {
			return new CombatHit[] { nextRangedHit(attacker, defender, 18) };
		}
	}

}
