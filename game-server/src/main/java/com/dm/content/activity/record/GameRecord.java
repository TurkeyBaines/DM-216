package com.dm.content.activity.record;

import com.dm.content.activity.ActivityType;
import com.dm.game.world.entity.mob.player.Player;
import com.dm.util.Utility;

import java.util.Objects;

public class GameRecord {
    public final String name;
    public final String date;
    public final int rank;
    public final long time;
    final ActivityType activityType;

    private GameRecord(String name, String date, int rank, long time, ActivityType activityType) {
        this.name = name;
        this.date = date;
        this.rank = rank;
        this.time = time;
        this.activityType = activityType;
    }

    GameRecord(Player player, long time, ActivityType activity) {
        this(player.getName(), Utility.getSimpleDate(), player.right.getCrown(), time, activity);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, time);
    }

    @Override
    public String toString() {
        return "name=" + name + " time=" + time + " type=" + activityType;
    }

}
