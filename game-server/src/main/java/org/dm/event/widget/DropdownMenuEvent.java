package org.dm.event.widget;

import com.dm.content.clanchannel.ClanRank;
import com.dm.content.clanchannel.ClanRepository;
import com.dm.content.clanchannel.ClanType;
import com.dm.content.clanchannel.content.ClanViewer;
import com.dm.content.simulator.DropSimulator;
import com.dm.content.simulator.Simulation;
import com.dm.game.world.entity.mob.player.Player;
import com.dm.game.world.entity.mob.player.PlayerRight;
import com.dm.net.packet.out.SendMessage;
import com.dm.util.MessageColor;

/**
 * @author Jire
 */
public final class DropdownMenuEvent implements WidgetEvent {

    private final int identification;
    private final int value;

    public DropdownMenuEvent(int identification, int value) {
        this.identification = identification;
        this.value = value;
    }

    @Override
    public void handle(Player player) {
        if (player.debug && PlayerRight.isDeveloper(player)) {
            player.send(new SendMessage(
                    "[DropdownMenuPacketListener] Identification: " + identification + " | Value: " + value,
                    MessageColor.DEVELOPER
            ));
        }

        switch (identification) {
            case 43019 -> {
                player.clanViewer.filter = ClanViewer.Filter.values()[value];
                player.clanViewer.open(player.clanChannel, ClanViewer.ClanTab.OVERVIEW);
            }

            case 42110 -> player.forClan(channel -> {
                if (channel.canManage(channel.getMember(player.getName()).orElse(null))) {
                    ClanRepository.getTopChanels(channel.getDetails().type)
                            .ifPresent(set -> set.remove(channel));

                    channel.getDetails().type = ClanType.values()[value];

                    ClanRepository.getTopChanels(ClanType.values()[value])
                            .ifPresent(set -> set.add(channel));

                    ClanRepository.ALLTIME.add(channel);
                    player.clanViewer.update(channel);
                }
            });

            case 42112 -> player.forClan(channel -> {
                if (channel.canManage(channel.getMember(player.getName()).orElse(null))) {
                    channel.getManagement().setEnterRank(ClanRank.values()[value]);
                    player.clanViewer.update(channel);
                }
            });

            case 42114 -> player.forClan(channel -> {
                if (channel.canManage(channel.getMember(player.getName()).orElse(null))) {
                    channel.getManagement().setTalkRank(ClanRank.values()[value]);
                    player.clanViewer.update(channel);
                }
            });

            case 42116 -> player.forClan(channel -> {
                if (channel.canManage(channel.getMember(player.getName()).orElse(null))) {
                    channel.getManagement().setManageRank(ClanRank.values()[value]);
                    player.clanViewer.update(channel);
                }
            });

            case 42134 -> {
                String color = switch (value) {
                    case 0 -> "<col=ffffff>";
                    case 1 -> "<col=F03737>";
                    case 2 -> "<col=2ADE36>";
                    case 3 -> "<col=2974FF>";
                    case 4 -> "<col=EBA226>";
                    case 5 -> "<col=A82D81>";
                    case 6 -> "<col=FF57CA>";
                    default -> null;
                };

                player.forClan(channel -> {
                    if (channel.canManage(channel.getMember(player.getName()).orElse(null))) {
                        channel.setColor(color);
                        player.clanViewer.update(channel);
                    }
                });
            }

            case 26811 -> {
                int[] simulations = {10, 100, 1000, 10000, 100000};
                int simulatorNpc = player.attributes.get("DROP_SIMULATOR_KEY");
                DropSimulator.simulate(player, Simulation.NPC_DROP, simulatorNpc, simulations[value]);
            }
        }
    }

    public int getIdentification() { return identification; }
    public int getValue() { return value; }

}