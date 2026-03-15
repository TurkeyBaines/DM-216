package org.dm;

import java.util.concurrent.locks.LockSupport;

/**
 * @author Jire
 */
public final class Threads {

    private Threads() {
        // Private constructor to prevent instantiation
    }

    /**
     * Precise sleep using a combination of parking and busy-waiting.
     * * @param totalNanos The total time to sleep in nanoseconds.
     */
    public static void preciseSleep(long totalNanos) {
        preciseSleep(totalNanos, 1_000_000L); // Default 1ms busy-wait
    }

    /**
     * Precise sleep using a combination of parking and busy-waiting.
     * * @param totalNanos     The total time to sleep in nanoseconds.
     * @param busyWaitNanos  The amount of time at the end of the sleep to spin-wait for high precision.
     */
    public static void preciseSleep(long totalNanos, long busyWaitNanos) {
        long start = System.nanoTime();
        long sleepUntil = start + totalNanos - busyWaitNanos;

        // Phase 1: Passive sleep (park)
        while (System.nanoTime() < sleepUntil) {
            long remaining = sleepUntil - System.nanoTime();
            if (remaining > 1_000_000L) {
                // Sleep with 0.5ms headroom to avoid oversleeping due to OS scheduler latency
                LockSupport.parkNanos(remaining - 500_000L);
            } else {
                Thread.yield(); // Let scheduler breathe
            }
        }

        // Phase 2: Spin-wait for precision
        while (System.nanoTime() - start < totalNanos) {
            Thread.onSpinWait();
        }
    }

}