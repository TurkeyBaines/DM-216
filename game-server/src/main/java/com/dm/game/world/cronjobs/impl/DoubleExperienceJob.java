package com.dm.game.world.cronjobs.impl;

import com.dm.Config;
import com.dm.Starter;
import com.dm.game.world.World;
import com.dm.game.world.cronjobs.Job;
import com.dm.net.discord.DiscordPlugin;
import org.joda.time.DateTime;

import java.time.DayOfWeek;

public class DoubleExperienceJob extends Job {

    public DoubleExperienceJob() {
        super("Double Experience");
    }

    @Override
    public void execute() {
        final DateTime time = Starter.currentDateTime();
        final DayOfWeek day = DayOfWeek.of(time.getDayOfWeek());
        final boolean enable = (day == DayOfWeek.SATURDAY || day == DayOfWeek.SUNDAY);

        if (enable && !Config.DOUBLE_EXPERIENCE) {
            World.sendMessage("<col=ff0000>Double EXP has been enabled!");
            DiscordPlugin.sendSimpleMessage("Double EXP has been enabled!");
        } else if (!enable && Config.DOUBLE_EXPERIENCE) {
            World.sendMessage("<col=ff0000>Double EXP has been disabled!");
            DiscordPlugin.sendSimpleMessage("Double EXP has been disabled!");
        }

        Config.DOUBLE_EXPERIENCE = enable;
    }
}
