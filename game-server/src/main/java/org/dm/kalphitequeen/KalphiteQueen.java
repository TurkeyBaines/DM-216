package org.dm.kalphitequeen;

import com.dm.game.Animation;
import com.dm.game.Graphic;
import com.dm.game.world.World;
import com.dm.game.world.entity.combat.CombatType;
import com.dm.game.world.entity.combat.CombatUtil;
import com.dm.game.world.entity.combat.attack.FightType;
import com.dm.game.world.entity.combat.hit.Hit;
import com.dm.game.world.entity.combat.strategy.CombatStrategy;
import com.dm.game.world.entity.combat.strategy.npc.MultiStrategy;
import com.dm.game.world.entity.combat.strategy.npc.NpcMeleeStrategy;
import com.dm.game.world.entity.mob.Mob;
import com.dm.game.world.entity.mob.npc.Npc;
import com.dm.util.RandomUtils;
import org.dm.WorldTask;

import java.util.Arrays;
import java.util.List;

/**
 * @author Jire
 */
public class KalphiteQueen extends MultiStrategy {

    public static final int PHASE1_ID = 963;
    public static final int PHASE2_ID = 965;

    public static final Graphic PHASE_SWITCH_GFX = new Graphic(1055);
    public static final Animation PHASE_SWITCH_ANIM = new Animation(6270);

    public static final NpcMeleeStrategy melee = NpcMeleeStrategy.get();

    public static final CombatStrategy<Npc>[] phase1Strats =
            CombatUtil.createStrategyArray(KalphiteQueenMagicStrategy.phase1, KalphiteQueenRangedStrategy.phase1, melee);
    public static final CombatStrategy<Npc>[] phase2Strats =
            CombatUtil.createStrategyArray(KalphiteQueenMagicStrategy.phase2, KalphiteQueenRangedStrategy.phase2, melee);

    private boolean transforming = false;
    private boolean switchingStyles = false;

    public KalphiteQueen() {
        currentStrategy = melee;
    }

    @Override
    public boolean canAttack(Npc attacker, Mob defender) {
        return super.canAttack(attacker, defender) && !transforming;
    }

    @Override
    public boolean canOtherAttack(Mob attacker, Npc defender) {
        return super.canOtherAttack(attacker, defender) && !transforming;
    }

    @Override
    public void performChecks(Npc attacker, Mob defender) {
        if (RandomUtils.inclusive(2) == 1) {
            CombatStrategy<Npc>[] pool = (attacker.id == PHASE1_ID) ? phase1Strats : phase2Strats;

            List<CombatStrategy<Npc>> strategies = Arrays.stream(pool)
                    .filter(it -> it.canAttack(attacker, defender) && it.withinDistance(attacker, defender))
                    .toList();

            CombatStrategy<Npc> strategy = RandomUtils.random(strategies);
            if (strategy != null) {
                currentStrategy = strategy;
                switchingStyles = true;
            }
        }
        super.performChecks(attacker, defender);
    }

    @Override
    public void attack(Npc attacker, Mob defender, Hit hit) {
        switchingStyles = false;
        super.attack(attacker, defender, hit);
    }

    @Override
    public void block(Mob attacker, Npc defender, Hit hit, CombatType combatType) {
        int id = defender.id;
        if ((id == PHASE1_ID && combatType.match(CombatType.MAGIC, CombatType.RANGED))
                || (id == PHASE2_ID && combatType.match(CombatType.MELEE))) {
            int newDamage = (int) (hit.getDamage() * 0.4);
            hit.setDamage(newDamage);
            if (newDamage < 1) {
                hit.setAccurate(false);
            }
        }
        super.block(attacker, defender, hit, combatType);
    }

    @Override
    public void preDeath(Mob attacker, Npc defender, Hit hit) {
        if (defender.getId() == PHASE1_ID) {
            defender.setDead(false);
            defender.heal(255);
            attacker.getCombat().reset();
            defender.getCombat().reset(true);
            defender.canAttack = false;
            defender.animate(6242);
            defender.locking.lock();
            transforming = true;

//            WorldTask.schedule(5, () -> {
//                currentStrategy = CombatUtil.randomStrategy(phase2Strats);
//                defender.transform(PHASE2_ID);
//                defender.canAttack = false;
//                defender.graphic(PHASE_SWITCH_GFX);
//                defender.animate(PHASE_SWITCH_ANIM);
//
//                WorldTask.schedule(8, () -> {
//                    transforming = false;
//                    defender.getCombat().reset(true);
//                    defender.canAttack = true;
//                    defender.locking.unlock();
//                    defender.getCombat().attack(attacker);
//                });
//            });

            org.jire.tarnishps.task.TaskSequence.create()
                    .then(5, () -> {
                        currentStrategy = CombatUtil.randomStrategy(phase2Strats);
                        defender.transform(PHASE2_ID);
                        defender.canAttack = false;
                        defender.graphic(PHASE_SWITCH_GFX);
                        defender.animate(PHASE_SWITCH_ANIM);
                    })
                    .then(8, () -> {
                        transforming = false;
                        defender.getCombat().reset();
                        defender.canAttack = true;
                        defender.locking.unlock();
                        defender.getCombat().attack(attacker);
                    })
                    .submit(World.getTaskManager());
            return;
        }

        if (defender.getId() == PHASE2_ID) {
            WorldTask.schedule(8, () -> defender.transform(PHASE1_ID));
        }

        super.preDeath(attacker, defender, hit);
    }

    @Override
    public void onDeath(Mob attacker, Npc defender, Hit hit) {
        super.onDeath(attacker, defender, hit);
    }

    @Override
    public int getAttackDelay(Npc attacker, Mob defender, FightType fightType) {
        return switchingStyles ? 7 : 4;
    }

}