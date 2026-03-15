package org.dm.event.npc;

import com.dm.content.skill.impl.hunter.net.impl.Butterfly;
import com.dm.content.skill.impl.hunter.net.impl.Impling;
import com.dm.game.world.entity.combat.magic.CombatSpell;
import com.dm.game.world.entity.mob.npc.Npc;
import com.dm.game.world.entity.mob.player.Player;
import com.dm.net.packet.out.SendMessage;

/**
 * @author Jire
 */
public final class MagicOnNpcEvent implements NpcClickEvent {

    private final int slot;
    private final int spell;

    public MagicOnNpcEvent(int slot, int spell) {
        this.slot = slot;
        this.spell = spell;
    }

    @Override
    public int getSlot() {
        return slot;
    }

    @Override
    public void handleNpc(Player player, Npc npc) {
        CombatSpell definition = CombatSpell.get(spell);
        if (definition == null) {
            return;
        }

        if (player.spellbook != definition.getSpellbook()) {
            return;
        }

        // Logic check for attackable NPCs or Hunter targets (Implings/Butterflies)
        if (!npc.definition.isAttackable()
                && !Impling.forId(npc.getId()).isPresent()
                && !Butterfly.forId(npc.getId()).isPresent()) {
            player.send(new SendMessage("This npc can not be attacked!"));
            return;
        }

        player.setSingleCast(definition);

        if (!player.getCombat().attack(npc)) {
            player.setSingleCast(null);
            player.resetFace();
        }
    }

    public int getSpell() {
        return spell;
    }

}