package com.dm.content.skill.impl.agility.obstacle.impl;

import com.dm.content.skill.impl.agility.obstacle.ObstacleInteraction;
import com.dm.game.Animation;
import com.dm.game.task.Task;
import com.dm.game.world.World;
import com.dm.game.world.entity.mob.player.Player;
import com.dm.game.world.position.Position;

public interface ArdougneWallClimbInteraction extends ObstacleInteraction {
    @Override
    default void start(Player player) {
        player.face(new Position(player.getX(), player.getY() + 1));
    }

    @Override
    default void onExecution(Player player, Position start, Position end) {
        World.schedule(new Task(1) {
            int ticks = 0;

            @Override
            public void execute() {
                switch (ticks++) {
                    case 1:
                        player.animate(new Animation(737));
                        break;
                    case 2:
                        player.animate(new Animation(737));
                        player.move(new Position(start.getX(), start.getY(), 1));
                        break;
                    case 3:
                        player.animate(new Animation(737));
                        player.move(new Position(start.getX(), start.getY(), 2));
                        break;
                    case 4:
                        player.animate(new Animation(2588));
                        player.move(end);
                        cancel();
                        break;
                }
            }
        });
    }

    @Override
    default void onCancellation(Player player) {
    }
}