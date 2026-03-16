package com.dm.game.task.impl;

import com.dm.Config;
import com.dm.game.task.Task;
import com.dm.game.world.World;
import com.dm.util.Utility;

/**
 * Sends game messages to all the online players.
 *
 * @author Daniel
 */
public class MessageEvent extends Task {

    /** The message randomevent ticks. */
    private int tick;

    /** Constructs a new <code>MessageEvent</code>. */
    public MessageEvent() {
        super(180);
        this.tick = 0;
    }

    @Override
    public void execute() {
        tick++;

        if (tick % 2 == 0) {
            String message = Utility.randomElement(Config.MESSAGES);
            World.sendMessage("<img=15> <col=2C7526>Broadcast: </col>" + message);
        }
    }
}
