package org.jire.tarnishps.event.npc

import com.dm.game.world.entity.mob.npc.Npc
import com.dm.game.world.entity.mob.player.Player

/**
 * @author Jire
 */
class AttackNpcEvent(override val slot: Int) : NpcClickEvent {

    override fun handleNpc(player: Player, npc: Npc) {
        player.combat.attack(npc)
    }

}