package com.dm.content.skill.impl.agility.obstacle.impl;

import com.dm.content.skill.impl.agility.obstacle.ObstacleInteraction;
import com.dm.game.Animation;
import com.dm.game.task.Task;
import com.dm.game.world.World;
import com.dm.game.world.entity.mob.player.Player;
import com.dm.game.world.position.Position;

public interface ArdougneJumpGapInteraction extends ObstacleInteraction {
    @Override
    default void start(Player player) {
    }

    @Override
    default void onExecution(Player player, Position start, Position end) {
        World.schedule(new Task(1) {
            int ticks = 0;

            @Override
            public void execute() {
                switch (ticks++) {
                    case 1:
                        player.face(end);
                        player.animate(new Animation(2586));
                        break;
                    case 2:
                        player.move(end);
                        player.animate(new Animation(2588));
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
