package com.dm.game.world.entity.combat.attack.listener.item;

import com.dm.game.world.entity.combat.attack.listener.ItemCombatListenerSignature;
import com.dm.game.world.entity.combat.attack.listener.SimplifiedListener;
import com.dm.game.world.entity.mob.Mob;
import com.dm.game.world.entity.mob.npc.NpcAssistant;
import com.dm.game.world.entity.mob.player.Player;

/**
 * Handles the dragon hunter crossbow modifiers.
 *
 * @author Michael | Chex
 */
@ItemCombatListenerSignature(requireAll = false, items = {21012})
public class DragonHunterCrossbowListener extends SimplifiedListener<Player> {

    @Override
    public int modifyRangedLevel(Player attacker, Mob defender, int level) {
        if (NpcAssistant.isDragon(defender.id))
            return level * 11 / 10;
        return level;
    }

}
