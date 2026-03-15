package org.dm.event.player;

import com.dm.game.world.entity.combat.magic.CombatSpell;
import com.dm.game.world.entity.mob.player.Player;

/**
 * @author Jire
 */
public final class MagicOnPlayerEvent implements PlayerEvent {

    private final int index;
    private final int spell;

    public MagicOnPlayerEvent(int index, int spell) {
        this.index = index;
        this.spell = spell;
    }

    public int getIndex() {
        return index;
    }

    @Override
    public int index() {
        return 0;
    }

    @Override
    public void handlePlayer(Player player, Player other) {
        CombatSpell combatSpell = CombatSpell.get(spell);
        if (combatSpell == null) {
            return;
        }

        if (player.spellbook != combatSpell.getSpellbook()) {
            return;
        }

        player.setSingleCast(combatSpell);

        if (!player.getCombat().attack(other)) {
            player.setSingleCast(null);
            player.resetFace();
        }
    }

    public int getSpell() {
        return spell;
    }

}