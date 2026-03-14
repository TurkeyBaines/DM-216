package com.dm.game.task.impl;

import com.dm.Config;
import com.dm.content.bot.PlayerBot;
import com.dm.content.bot.objective.BotObjective;
import com.dm.game.task.TickableTask;

/**
 * This loads all the bots into the game world after starting the server.
 *
 * @author Daniel
 */
public class BotStartupEvent extends TickableTask {

    public BotStartupEvent() {
        super(false, 100);
    }

    @Override
    protected void tick() {
        if (tick >= Config.MAX_BOTS) {
            cancel();
            return;
        }

        PlayerBot bot = new PlayerBot();
        bot.register();
        BotObjective.WALK_TO_BANK.init(bot);
    }
}
