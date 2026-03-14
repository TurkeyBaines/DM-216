package com.dm.game.world.entity.combat.attack.listener.item;

import com.dm.game.world.entity.combat.attack.listener.ItemCombatListenerSignature;
import com.dm.game.world.entity.combat.attack.listener.SimplifiedListener;
import com.dm.game.world.entity.combat.hit.Hit;
import com.dm.game.world.entity.mob.Mob;
import com.dm.game.world.entity.mob.UpdateFlag;

/**
 * Author : Settings 08/23/2023
 *  Discord : tettings
 */
@ItemCombatListenerSignature(requireAll = true, items = {81})
public class StarterAgsListener extends SimplifiedListener<Mob> {

    @Override
    public void hit(Mob attacker, Mob defender, Hit hit) {
        final var player = attacker.getPlayer();

        if (player.agsCharges > 0) {
            player.agsCharges--;
        }
        if (player.agsCharges <= 0 && player.equipment.contains(81)) {
            player.message("Your starter godsword is out of charges and has degraded into dust.");
            player.equipment.remove(81);
            player.equipment.refresh();
            player.updateFlags.add(UpdateFlag.APPEARANCE);
        }
    }
}
