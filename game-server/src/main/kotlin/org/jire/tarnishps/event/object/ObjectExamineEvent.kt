package org.jire.tarnishps.event.`object`

import com.dm.game.world.entity.mob.player.Player
import com.dm.net.packet.out.SendMessage
import org.jire.tarnishps.objectexamines.ObjectExamines

/**
 * @author Jire
 */
class ObjectExamineEvent(val objectId: Int) : ObjectEvent {

    override fun handle(player: Player) {
        val examine = ObjectExamines.map.get(objectId) ?: return
        if (examine != "null") {
            player.send(SendMessage(examine))
        }
    }

}