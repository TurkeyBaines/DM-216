package org.dm.event.widget;

import com.dm.content.clanchannel.channel.ClanChannel;
import com.dm.game.plugin.PluginManager;
import com.dm.game.world.entity.mob.player.Player;
import com.dm.game.world.entity.mob.player.command.CommandParser;
import com.dm.util.Utility;

/**
 * @author Jire
 */
public final class CommandEvent implements WidgetEvent {

    private final String input;

    public CommandEvent(String input) {
        this.input = input;
    }

    @Override
    public void handle(Player player) {
        CommandParser parser = CommandParser.split(input, " ");

        if (parser.getCommand().startsWith("/")) {
            if (player.punishment.isMuted()) {
                player.message("You can not send clan messages while muted!");
                return;
            }
            player.forClan((ClanChannel channel) -> {
                CommandParser copy = CommandParser.split(input, "/");
                if (copy.hasNext()) {
                    String line = copy.nextLine();
                    channel.chat(player.getName(), Utility.capitalizeSentence(line));
                }
            });
            return;
        }

        // Using fully qualified name to avoid collision with this class name
        com.dm.game.event.impl.CommandEvent pluginEvent = new com.dm.game.event.impl.CommandEvent(parser);
        PluginManager.getDataBus().publish(player, pluginEvent);
    }

    public String getInput() {
        return input;
    }

}