package org.jire.tarnishps.event.widget

import com.dm.game.world.entity.mob.player.Player
import com.dm.util.Utility
import kotlin.jvm.optionals.getOrNull

/**
 * @author Jire
 */
class InputStringEvent(val inputLong: Long) : WidgetEvent {

    override fun handle(player: Player) {
        val input = Utility.longToString(inputLong).replace('_', ' ')
        player.enterInputListener.getOrNull()?.accept(input)
    }

}