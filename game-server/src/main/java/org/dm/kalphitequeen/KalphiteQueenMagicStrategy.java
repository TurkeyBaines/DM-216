package org.dm.kalphitequeen;

import com.dm.game.Animation;
import com.dm.game.Graphic;
import com.dm.game.Projectile;
import com.dm.game.world.entity.combat.hit.CombatHit;
import com.dm.game.world.entity.combat.hit.Hit;
import com.dm.game.world.entity.combat.projectile.CombatProjectile;
import com.dm.game.world.entity.combat.strategy.npc.NpcMagicStrategy;
import com.dm.game.world.entity.mob.Mob;
import com.dm.game.world.entity.mob.npc.Npc;
import com.dm.game.world.region.RegionManager;

/**
 * @author Jire
 */
final class KalphiteQueenMagicStrategy extends NpcMagicStrategy {

    public static final KalphiteQueenMagicStrategy phase1 = new KalphiteQueenMagicStrategy(1173, 278);
    public static final KalphiteQueenMagicStrategy phase2 = new KalphiteQueenMagicStrategy(6234, 279);

    public KalphiteQueenMagicStrategy(int anim, int graphic) {
        super(new CombatProjectile(
                "KalphiteQueen-MagicStrategy-" + anim + "-" + graphic, 31, null,
                new Animation(anim), new Graphic(graphic), new Graphic(281),
                new Projectile(280, 70, 90, 60, 43, 16, 128)
        ));
    }

    @Override
    public Animation getAttackAnimation(Npc attacker, Mob defender) {
        return Animation.RESET;
    }

    @Override
    public void start(Npc attacker, Mob defender, Hit[] hits) {
        super.start(attacker, defender, hits);
        sendAttack(attacker, defender, hits, true);
    }

    @Override
    public int sendProjectile(Npc attacker, Hit[] hits, Mob from, Mob to, Runnable onProjectileLand) {
        return 0;
    }

    private void sendAttack(Npc attacker, Mob target, Hit[] hits, boolean first) {
        super.sendProjectile(attacker, hits, attacker, target, () -> {
            if (!first) {
                target.damage(hits);
            }
            RegionManager.forNearbyPlayer(target, 1, nearbyPlayer -> {
                if (target != nearbyPlayer && canAttack(attacker, nearbyPlayer)) {
                    sendAttack(attacker, nearbyPlayer, hits, false);
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
        CombatHit hit = nextMagicHit(attacker, defender, combatProjectile);
        // Direct variable access
        hit.setAccurate(true);
        return new CombatHit[]{hit};
    }

}