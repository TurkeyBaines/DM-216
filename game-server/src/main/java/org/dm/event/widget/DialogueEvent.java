package org.dm.event.widget;

import com.dm.game.world.entity.mob.player.Player;

/**
 * @author Jire
 */
public final class DialogueEvent implements WidgetEvent {

    public static final DialogueEvent INSTANCE = new DialogueEvent();

    private DialogueEvent() {
        // Private constructor for singleton
    }

    @Override
    public void handle(Player player) {
        player.dialogueFactory.execute();
    }

}