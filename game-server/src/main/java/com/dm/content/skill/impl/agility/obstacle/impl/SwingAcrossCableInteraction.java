package com.dm.content.skill.impl.agility.obstacle.impl;

import com.dm.content.skill.impl.agility.obstacle.ObstacleInteraction;
import com.dm.game.Animation;
import com.dm.game.task.Task;
import com.dm.game.world.World;
import com.dm.game.world.entity.mob.player.Player;
import com.dm.game.world.position.Position;

public interface SwingAcrossCableInteraction extends ObstacleInteraction {
    @Override
    default void start(Player player) {
    }

    @Override
    default void onExecution(Player player, Position start, Position end) {
        World.schedule(new Task(1) {

            @Override
            public void execute() {
                player.animate(new Animation(getAnimation()));
            }
        });
    }

    @Override
    default void onCancellation(Player player) {
    }
}