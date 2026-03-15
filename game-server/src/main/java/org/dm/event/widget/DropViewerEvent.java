package org.dm.event.widget;

import com.dm.content.DropDisplay;
import com.dm.content.DropDisplay.DropType;
import com.dm.game.world.entity.mob.npc.drop.NpcDropManager;
import com.dm.game.world.entity.mob.player.Player;

import java.util.List;

/**
 * @author Jire
 */
public final class DropViewerEvent implements WidgetEvent {

    private final String context;

    public DropViewerEvent(String context) {
        this.context = context;
    }

    @Override
    public void handle(Player player) {
        DropDisplay.search(player, context, DropType.NPC);

        if (!player.attributes.has("DROP_DISPLAY_KEY")) {
            return;
        }

        List<?> key = player.attributes.get("DROP_DISPLAY_KEY", List.class);
        if (key == null || key.isEmpty()) {
            return;
        }

        // Direct variable access for NPC_DROPS and interfaceManager
        DropDisplay.display(player, NpcDropManager.NPC_DROPS.get(key.get(0)));
        player.interfaceManager.open(54500);
    }

    public String getContext() {
        return context;
    }

}