package org.dm.kalphitequeen;

import com.dm.game.Animation;
import com.dm.game.Projectile;
import com.dm.game.world.entity.combat.hit.CombatHit;
import com.dm.game.world.entity.combat.hit.Hit;
import com.dm.game.world.entity.combat.projectile.CombatProjectile;
import com.dm.game.world.entity.combat.strategy.npc.NpcRangedStrategy;
import com.dm.game.world.entity.mob.Mob;
import com.dm.game.world.entity.mob.npc.Npc;
import com.dm.game.world.entity.mob.player.Player;
import com.dm.game.world.entity.skill.Skill;
import com.dm.game.world.region.RegionManager;

import java.util.Arrays;

/**
 * @author Jire
 */
final class KalphiteQueenRangedStrategy extends NpcRangedStrategy {

    public static final KalphiteQueenRangedStrategy phase1 = new KalphiteQueenRangedStrategy(6240, 288);
    public static final KalphiteQueenRangedStrategy phase2 = new KalphiteQueenRangedStrategy(6234, 289);

    private final Animation attackAnimation;

    public KalphiteQueenRangedStrategy(int anim, int proj) {
        super(new CombatProjectile(
                "KalphiteQueen-RangedStrategy-" + anim + "-" + proj, 31, null,
                new Animation(anim), null, null,
                new Projectile(proj, 30, 60, 25, 0, 16, 128)
        ));
        this.attackAnimation = new Animation(anim);
    }

    @Override
    public Animation getAttackAnimation(Npc attacker, Mob defender) {
        return attackAnimation;
    }

    @Override
    public void start(Npc attacker, Mob defender, Hit[] hits) {
        super.start(attacker, defender, hits);
        sendAttack(attacker, defender, hits, true);
    }

    @Override
    public int sendProjectile(Mob from, Mob to, Runnable onProjectileLand) {
        return 0;
    }

    private void sendAttack(Npc attacker, Mob target, Hit[] hits, boolean first) {
        super.sendProjectile(attacker, target, () -> {
            if (!first) {
                target.damage(hits);
            }

            if (target instanceof Player player && Arrays.stream(hits).anyMatch(hit -> hit.isAccurate())) {
                player.skills.get(Skill.PRAYER).modifyLevel(level -> level - 1);
                player.skills.refresh(Skill.PRAYER);
                // player.message("Your prayer has been drained!");
            }

            RegionManager.forNearbyPlayer(target, 1, nearby -> {
                if (target != nearby && canAttack(attacker, nearby)) {
                    sendAttack(attacker, nearby, hits, false);
                }
            });
        });
    }

    @Override
    public boolean isAlwaysAccurate() {
        return true;
    }

    @Override
    public CombatHit[] getHits(Npc attacker, Mob defender) {
        CombatHit hit = nextRangedHit(attacker, defender, 31, getCombatProjectile());
        // Direct variable access
        hit.setAccurate(true);
        return new CombatHit[]{hit};
    }

}