package plugin.command;

import com.dm.Config;
import com.dm.content.Yell;
import com.dm.content.skill.impl.magic.teleport.Teleportation;
import com.dm.content.skill.impl.magic.teleport.TeleportationData;
import com.dm.game.plugin.extension.CommandExtension;
import com.dm.game.world.entity.mob.player.Player;
import com.dm.game.world.entity.mob.player.PlayerRight;
import com.dm.game.world.entity.mob.player.command.Command;
import com.dm.game.world.entity.mob.player.command.CommandParser;
import com.dm.net.packet.out.SendMessage;


public class DonatorCommandPlugin extends CommandExtension {

    @Override
    protected void register() {
        commands.add(new Command("superdonatorzone", "sdonorzone", "sdzone", "sdz") {
            @Override
            public void execute(Player player, CommandParser parser) {
                if (PlayerRight.isElite(player) || PlayerRight.isKing(player)) {
                Teleportation.teleport(player, Config.SUPER_DONATOR_ZONE, 20, TeleportationData.DONATOR, () -> {
                    player.send(new SendMessage("Welcome to the super donator zone, " + player.getName() + "!"));
                    });
           } else {
                    player.message("You must be an elite or king donator to do this.");
                }


                commands.add(new Command("yell") {
                    @Override
                    public void execute(Player player, CommandParser parser) {
                        if (parser.hasNext()) {
                            final String message = parser.nextLine();
                            Yell.yell(player, message);
                        }
                    }
                });
            }
        });
        commands.add(new Command("donatorzone", "donorzone", "dzone", "dz") {
            @Override
            public void execute(Player player, CommandParser parser) {
                Teleportation.teleport(player, Config.REGULAR_DONATOR_ZONE, 20, TeleportationData.DONATOR, () -> {
                    player.send(new SendMessage("Welcome to the regular donator zone, " + player.getName() + "!"));
                });

                commands.add(new Command("yell") {
                    @Override
                    public void execute(Player player, CommandParser parser) {
                        if (parser.hasNext()) {
                            final String message = parser.nextLine();
                            Yell.yell(player, message);
                        }
                    }
                });
            }
        });
        commands.add(new Command("yell") {
            @Override
            public void execute(Player player, CommandParser parser) {
                if (parser.hasNext()) {
                    final String message = parser.nextLine();
                    Yell.yell(player, message);
                }
            }
        });
    }

    @Override
    public boolean canAccess(Player player) {
        return PlayerRight.isDonator(player);
    }

}
