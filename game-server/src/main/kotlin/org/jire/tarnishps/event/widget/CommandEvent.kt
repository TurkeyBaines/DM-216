package org.jire.tarnishps.event.widget

import com.dm.content.clanchannel.channel.ClanChannel
import com.dm.game.event.impl.CommandEvent
import com.dm.game.plugin.PluginManager
import com.dm.game.world.entity.mob.player.Player
import com.dm.game.world.entity.mob.player.command.CommandParser
import com.dm.util.Utility

/**
 * @author Jire
 */
class CommandEvent(val input: String) : WidgetEvent {

    override fun handle(player: Player) {
        val parser = CommandParser.split(input, " ")

        if (parser.command.startsWith("/")) {
            if (player.punishment.isMuted) {
                player.message("You can not send clan messages while muted!")
                return
            }
            player.forClan { channel: ClanChannel ->
                val copy = CommandParser.split(input, "/")
                if (copy.hasNext()) {
                    val line = copy.nextLine()
                    channel.chat(player.name, Utility.capitalizeSentence(line))
                }
            }
            return
        }

        PluginManager.getDataBus().publish(player, CommandEvent(parser))
    }

}