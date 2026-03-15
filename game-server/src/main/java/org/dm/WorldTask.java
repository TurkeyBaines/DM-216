package org.dm;

import com.dm.game.task.Task;
import com.dm.game.world.World;

/**
 * @author Jire
 */
public final class WorldTask {

    private WorldTask() {
        // Private constructor to prevent instantiation
    }

    /**
     * Schedules a task to run after a default delay of 1 tick.
     *
     * @param execute The logic to execute.
     */
    public static void schedule(Runnable execute) {
        schedule(1, execute);
    }

    /**
     * Schedules a task to run after a specified delay.
     *
     * @param delay   The delay in ticks.
     * @param execute The logic to execute.
     */
    public static void schedule(int delay, Runnable execute) {
        if (delay > 0) {
            World.schedule(new Task(delay) {
                @Override
                public void execute() {
                    cancel();
                    execute.run();
                }
            });
        } else {
            execute.run();
        }
    }

}