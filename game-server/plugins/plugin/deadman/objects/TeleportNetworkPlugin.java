package plugin.deadman.objects;

import com.dm.content.dialogue.DialogueFactory;
import com.dm.game.event.impl.ObjectClickEvent;
import com.dm.game.plugin.PluginContext;
import com.dm.game.world.entity.mob.player.Player;
import com.dm.game.world.object.CustomGameObject;
import com.dm.game.world.object.ObjectDirection;
import com.dm.game.world.object.ObjectType;
import com.dm.game.world.position.Area;
import com.dm.game.world.position.Position;

public class TeleportNetworkPlugin extends PluginContext {

    @Override
    public boolean firstClickObject(Player p, ObjectClickEvent e) {
        if (e.getObject().getId() == 33181) {
            DialogueFactory factory = p.dialogueFactory;

            factory.sendOption(
                            "Citadel", () -> { p.move(Area.CITADEL_SPAWN.getRandomLocation()); },
                            "Lumbridge", () -> { p.move(new Position(0, 0, 0)); factory.clear(); },
                            "Varrock", () -> { p.move(new Position(0, 0, 0)); factory.clear(); },
                            "Ardougne", () -> { p.move(new Position(0, 0, 0)); factory.clear(); },
                                    "more...", () -> {})
                    .sendOption(
                            "Canifis", () -> { p.move(new Position(0, 0, 0)); factory.clear(); },
                            "Camelot", () -> { p.move(new Position(0, 0, 0)); factory.clear(); },
                            "Rellekka", () -> { p.move(new Position(0, 0, 0)); factory.clear(); })
                    .execute();
        }
        return false;
    }


}
