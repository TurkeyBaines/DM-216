package com.dm.content.bot.objective;

import com.dm.content.bot.PlayerBot;

public interface BotObjectiveListener {

    void init(PlayerBot bot);

    void finish(PlayerBot bot);

}
