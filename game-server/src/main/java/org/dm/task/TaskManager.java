package org.dm.task;

import com.dm.game.task.Task;
import it.unimi.dsi.fastutil.objects.Object2BooleanMap;
import it.unimi.dsi.fastutil.objects.Object2BooleanOpenHashMap;
import org.jctools.queues.MessagePassingQueue;
import org.jctools.queues.MpscArrayQueue;
import org.jctools.queues.SpscArrayQueue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.dm.task.MessagePassingQueues.drain;

/**
 * Used to schedule {@link Task}s.
 *
 * @author Jire
 */
public final class TaskManager {

    /**
     * The capacity of the tasks queue.
     */
    private static final int CAPACITY = (2000 /* players */ + 20000 /* NPCs */) * 256;

    /**
     * Used for logging via SLF4J API.
     */
    private static final Logger logger = LoggerFactory.getLogger(TaskManager.class);

    /**
     * Tasks which are pending to be scheduled to be
     * added to {@link #pending} tasks during processing.
     */
    private final MessagePassingQueue<Task> scheduled = new MpscArrayQueue<>(CAPACITY);

    /**
     * The pending tasks, which are first added to
     * the {@link #active} tasks during processing.
     */
    private final MessagePassingQueue<Task> pending = new SpscArrayQueue<>(CAPACITY);

    /**
     * The active tasks, which will be processed in the current tick.
     */
    private final MessagePassingQueue<Task> active = new SpscArrayQueue<>(CAPACITY);

    /**
     * Set of attachment keys to `logout` state used
     * for cancelling tasks by their {@link Task#getAttachment()}.
     */
    private final Object2BooleanMap<Object> cancelAttachments = new Object2BooleanOpenHashMap<>(CAPACITY);

    /**
     * @param task The {@link Task} to schedule.
     * @return whether the task was successfully added to the {@link #scheduled} queue.
     */
    public boolean schedule(Task task) {
        if (task == null) return false;

        try {
            if (!task.canSchedule()) {
                return false;
            }

            if (task.isInstantDelay()) {
                task.setRunning(true);
                task.beforeSchedule();

                /* task was cancelled inside [Task.beforeSchedule] */
                if (task.isRunning()) {
                    task.onSchedule();

                    if (task.isRunning()) {
                        task.process();
                    }
                    if (task.isRunning() && !pending.offer(task)) {
                        return false;
                    }
                }
                return true;
            }
        } catch (Exception e) {
            logger.error("Failed to schedule task", e);
            return false;
        }

        boolean added = scheduled.offer(task);
        if (!added) {
            logger.warn("Unable to add task to `scheduled` ({} size)", scheduled.size());
        }

        return added;
    }

    public boolean process() {
        boolean drainedScheduled = drain(scheduled, CAPACITY, task -> {
            try {
                if (!task.canSchedule()) return true;

                task.setRunning(true);
                task.beforeSchedule();

                /* task was cancelled inside [Task.beforeSchedule] */
                if (task.isRunning()) {
                    if (!pending.offer(task)) return false;

                    task.onSchedule();

                    if (task.isInstant()) {
                        task.baseExecute();
                    }
                }
            } catch (Exception e) {
                logger.error("Failed to process scheduled task", e);
            }
            return true;
        });
        if (!drainedScheduled) return false;

        boolean drainedPending = drain(pending, CAPACITY, active::offer);
        if (!drainedPending) return false;

        boolean drainedActive = drain(active, CAPACITY, task -> {
            /* Check to make sure task isn't cancelled */
            if (checkCancelled(task)) return true;

            try {
                task.process();

                return !task.isRunning() || pending.offer(task);
            } catch (Exception e) {
                logger.error("Failed to process active task", e);

                try {
                    task.cancel();
                } catch (Exception ex) {
                    logger.error("Failed to cancel active task", ex);
                }
            }
            return true;
        });
        if (!drainedActive) return false;

        /* Clear attachments so next tick they won't be still there */
        synchronized (cancelAttachments) {
            if (!cancelAttachments.isEmpty()) { /* avoids clear object allocation if empty */
                cancelAttachments.clear();
            }
        }

        return true;
    }

    private boolean checkCancelled(Task task) {
        Object attachment = task.getAttachment();
        if (attachment == null) return false;

        boolean logout;
        synchronized (cancelAttachments) {
            if (!cancelAttachments.containsKey(attachment)) return false;
            logout = cancelAttachments.getBoolean(attachment);
        }

        task.cancel(logout);
        return true;
    }

    public boolean cancel(Object attachment) {
        return cancel(attachment, false);
    }

    public boolean cancel(Object attachment, boolean logout) {
        if (attachment == null) return false;

        synchronized (cancelAttachments) {
            return cancelAttachments.put(attachment, logout);
        }
    }

}