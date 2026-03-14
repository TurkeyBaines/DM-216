package com.dm.game.event.impl;

import com.dm.game.event.Event;
import com.dm.game.world.entity.mob.player.command.CommandParser;

public class CommandEvent implements Event {

    private final CommandParser parser;

    public CommandEvent(CommandParser parser) {
        this.parser = parser;
    }

    public CommandParser getParser() {
        return parser;
    }

}
