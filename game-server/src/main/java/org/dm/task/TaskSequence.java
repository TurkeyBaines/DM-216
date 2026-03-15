package org.jire.tarnishps.task;

import com.dm.game.task.Task;
import com.dm.game.world.entity.mob.player.Player;
import org.dm.task.TaskManager;

import java.util.LinkedList;
import java.util.Queue;

/**
 * A utility to chain timed actions together without nesting WorldTask.schedule calls.
 * * @author Gemini
 */
public final class TaskSequence {

    private final Queue<SequenceStep> steps = new LinkedList<>();
    private int totalDelay = 0;

    private record SequenceStep(int delay, Runnable action) {}

    private TaskSequence() {}

    public static TaskSequence create() {
        return new TaskSequence();
    }

    /**
     * Adds an action to be performed after a specific delay from the PREVIOUS step.
     */
    public TaskSequence then(int delay, Runnable action) {
        steps.add(new SequenceStep(delay, action));
        return this;
    }

    /**
     * Adds an action to be performed immediately (0 tick delay) after the previous step.
     */
    public TaskSequence then(Runnable action) {
        return then(0, action);
    }

    /**
     * Submits the sequence to the TaskManager.
     */
    public void submit(TaskManager taskManager) {
        if (steps.isEmpty()) return;

        // We create a single Task that manages the internal timing of the sequence
        taskManager.schedule(new Task(1) { // Process every tick
            private int ticksPassed = 0;
            private SequenceStep currentStep = steps.poll();
            private int waitTime = currentStep != null ? currentStep.delay : 0;

            @Override
            public void execute() {
                if (currentStep == null) {
                    stop(this);
                    return;
                }

                if (ticksPassed >= waitTime) {
                    currentStep.action.run();

                    // Move to next step
                    currentStep = steps.poll();
                    if (currentStep != null) {
                        ticksPassed = 0;
                        waitTime = currentStep.delay;
                    } else {
                        stop(this);
                    }
                } else {
                    ticksPassed++;
                }
            }
        });
    }

    private void stop(Task task) {
        task.setRunning(false);
    }

}