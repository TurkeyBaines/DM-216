package org.jire.tarnishps.event.npc

import com.dm.content.event.impl.SecondNpcClick
import com.dm.game.world.entity.mob.npc.Npc

/**
 * @author Jire
 */
class SecondNpcOptionEvent(slot: Int) : NpcOptionEvent(slot, 1) {

    override fun createInteractionEvent(npc: Npc) = SecondNpcClick(npc)

}