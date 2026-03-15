package org.dm.event.widget;

import com.dm.game.world.entity.mob.player.Player;
import com.dm.util.Utility;

/**
 * @author Jire
 */
public final class InputStringEvent implements WidgetEvent {

    private final long inputLong;

    public InputStringEvent(long inputLong) {
        this.inputLong = inputLong;
    }

    @Override
    public void handle(Player player) {
        String input = Utility.longToString(inputLong).replace('_', ' ');

        // Direct access to the Optional field and conditional execution
        player.enterInputListener.ifPresent(listener -> listener.accept(input));
    }

    public long getInputLong() {
        return inputLong;
    }

}