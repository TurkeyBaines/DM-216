package org.dm.task;

import org.jctools.queues.MessagePassingQueue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Jire
 */
public final class MessagePassingQueues {

    private static final Logger logger = LoggerFactory.getLogger(MessagePassingQueue.class);

    private MessagePassingQueues() {
        // Private constructor for singleton-like utility class
    }

    /**
     * Used to drain from a {@link MessagePassingQueue}.
     */
    @FunctionalInterface
    public interface Drainer<T> {

        /**
         * @return {@code true} if the queue should continue being drained, {@code false} otherwise.
         */
        boolean continueDraining(T e);

    }

    /**
     * @return {@code false} if it was broken due to {@link Drainer#continueDraining(Object)}
     */
    public static <T> boolean drain(MessagePassingQueue<T> queue, Drainer<T> consumer) {
        return drain(queue, queue.capacity(), consumer);
    }

    /**
     * @return {@code false} if it was broken due to {@link Drainer#continueDraining(Object)}
     */
    public static <T> boolean drain(
            MessagePassingQueue<T> queue,
            int limit,
            Drainer<T> consumer
    ) {
        int i = 0;
        T next;
        while (i < limit) {
            next = queue.poll();
            if (next == null) {
                break;
            }

            if (!consumer.continueDraining(next)) {
                logger.warn("Breaking at task! (size={})", queue.size());
                return false;
            }

            i++;
        }
        return true;
    }

}