package com.dm.content.activity.impl.kraken;

import com.dm.content.activity.ActivityListener;
import com.dm.game.world.Interactable;
import com.dm.game.world.entity.combat.attack.FightType;
import com.dm.game.world.entity.combat.hit.Hit;
import com.dm.game.world.entity.mob.Mob;
import com.dm.game.world.position.Position;
import com.dm.net.packet.out.SendMessage;
import com.dm.util.Utility;

/**
 * Created by Daniel on 2017-09-17.
 */
public class KrakenActivityListener extends ActivityListener<KrakenActivity> {

    KrakenActivityListener(KrakenActivity minigame) {
        super(minigame);
    }

    @Override
    public boolean withinDistance(Mob attacker, Mob defender) {
        if (!attacker.isPlayer())
            return true;
        FightType fightType = attacker.getCombat().getFightType();
        int distance = attacker.getStrategy().getAttackDistance(attacker, fightType);
        Interactable kraken = Interactable.create(new Position(2278, 10035, attacker.getHeight()), 4, 4);
        return Utility.getDistance(attacker, kraken) <= distance
                && attacker.getStrategy().withinDistance(attacker, activity.kraken);
    }

    @Override
    public boolean canAttack(Mob attacker, Mob defender) {
        if (attacker.isPlayer() && defender.isNpc() && defender.getNpc().id == 496 && activity.count != 4) {
            attacker.getPlayer()
                    .send(new SendMessage("You must activate all four whirlpools before awakening the Kraken."));
            return false;
        }
        return activity.kraken == null || !activity.kraken.isDead();
    }

    @Override
    public void hit(Mob attacker, Mob defender, Hit hit) {
        if (!attacker.isPlayer() && !defender.isNpc()) {
            return;
        }

        if (attacker.isPlayer() && defender.getNpc().id == 493) {
            activity.transform(defender.getNpc(), 5535);
        } else if (attacker.isPlayer() && defender.getNpc().id == 496) {
            activity.transform(defender.getNpc(), 494);
        }
    }

    @Override
    public void onDeath(Mob attacker, Mob defender, Hit hit) {
    }
}