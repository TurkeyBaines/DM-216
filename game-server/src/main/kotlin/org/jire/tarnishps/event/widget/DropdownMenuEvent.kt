package org.jire.tarnishps.event.widget

import com.dm.content.clanchannel.ClanRank
import com.dm.content.clanchannel.ClanRepository
import com.dm.content.clanchannel.ClanType
import com.dm.content.clanchannel.channel.ClanChannel
import com.dm.content.clanchannel.content.ClanViewer
import com.dm.content.simulator.DropSimulator
import com.dm.content.simulator.Simulation
import com.dm.game.world.entity.mob.player.Player
import com.dm.game.world.entity.mob.player.PlayerRight
import com.dm.net.packet.out.SendMessage
import com.dm.util.MessageColor

/**
 * @author Jire
 */
class DropdownMenuEvent(
    val identification: Int,
    val value: Int
) : WidgetEvent {

    override fun handle(player: Player) {
        if (player.debug && PlayerRight.isDeveloper(player)) {
            player.send(
                SendMessage(
                    "[DropdownMenuPacketListener] Identification: $identification | Value: $value",
                    MessageColor.DEVELOPER
                )
            )
        }

        when (identification) {
            43019 -> {
                player.clanViewer.filter = ClanViewer.Filter.values()[value]
                player.clanViewer.open(player.clanChannel, ClanViewer.ClanTab.OVERVIEW)
            }

            42110 -> player.forClan { channel: ClanChannel ->
                if (channel.canManage(channel.getMember(player.name).orElse(null))) {
                    ClanRepository.getTopChanels(channel.details.type)
                        .ifPresent { set: MutableSet<ClanChannel?> ->
                            set.remove(
                                channel
                            )
                        }
                    channel.details.type = ClanType.values()[value]
                    ClanRepository.getTopChanels(ClanType.values()[value])
                        .ifPresent { set: MutableSet<ClanChannel?> ->
                            set.add(
                                channel
                            )
                        }
                    ClanRepository.ALLTIME.add(channel)
                    player.clanViewer.update(channel)
                }
            }

            42112 -> player.forClan { channel: ClanChannel ->
                if (channel.canManage(channel.getMember(player.name).orElse(null))) {
                    channel.management.setEnterRank(ClanRank.values()[value])
                    player.clanViewer.update(channel)
                }
            }

            42114 -> player.forClan { channel: ClanChannel ->
                if (channel.canManage(channel.getMember(player.name).orElse(null))) {
                    channel.management.setTalkRank(ClanRank.values()[value])
                    player.clanViewer.update(channel)
                }
            }

            42116 -> player.forClan { channel: ClanChannel ->
                if (channel.canManage(channel.getMember(player.name).orElse(null))) {
                    channel.management.setManageRank(ClanRank.values()[value])
                    player.clanViewer.update(channel)
                }
            }

            42134 -> {
                var color: String? = null
                when (value) {
                    0 -> color = "<col=ffffff>"
                    1 -> color = "<col=F03737>"
                    2 -> color = "<col=2ADE36>"
                    3 -> color = "<col=2974FF>"
                    4 -> color = "<col=EBA226>"
                    5 -> color = "<col=A82D81>"
                    6 -> color = "<col=FF57CA>"
                }
                val col = color
                player.forClan { channel: ClanChannel ->
                    if (channel.canManage(channel.getMember(player.name).orElse(null))) {
                        channel.color = col
                        player.clanViewer.update(channel)
                    }
                }
            }

            26811 -> {
                val simulations = intArrayOf(10, 100, 1000, 10000, 100000)
                val simulatorNpc = player.attributes.get<String, Int>("DROP_SIMULATOR_KEY")
                DropSimulator.simulate(player, Simulation.NPC_DROP, simulatorNpc, simulations[value])
            }
        }
    }

}