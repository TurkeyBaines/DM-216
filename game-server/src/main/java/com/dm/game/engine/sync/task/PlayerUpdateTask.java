package com.dm.game.engine.sync.task;

import com.dm.game.world.entity.mob.player.Player;
import com.dm.net.packet.out.SendPlayerUpdate;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public final class PlayerUpdateTask extends SynchronizationTask {

    private static final Logger logger = LogManager.getLogger();

    private final Player player;

    public PlayerUpdateTask(Player player) {
        this.player = player;
    }

    @Override
    public void run() {
        try {
            if (player == null) {
                return;
            }

            player.send(new SendPlayerUpdate());
        } catch (Exception ex) {
            logger.fatal(String.format("Error in %s %s", PlayerUpdateTask.class.getSimpleName(), player), ex);
        }
    }

}
