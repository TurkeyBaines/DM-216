package org.dm;

import net.openhft.affinity.AffinityLock;
import net.openhft.affinity.CpuLayout;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Looping main thread with core-affinity and highly accurate cycle delay.
 *
 * @author Jire
 */
public abstract class MainThread extends Thread {

    private static final Logger logger = LoggerFactory.getLogger(MainThread.class);

    private final long cycleNanos;
    private final long minSleepNanos;
    private final int targetCpuId;

    private boolean running = false;

    public MainThread(String name) {
        this(name, 600L * 1000 * 1000); // 600ms default
    }

    public MainThread(String name, long cycleNanos) {
        this(name, cycleNanos, 1L * 1000 * 1000); // 1ms default
    }

    public MainThread(String name, long cycleNanos, long minSleepNanos) {
        this(name, cycleNanos, minSleepNanos, Thread.MAX_PRIORITY);
    }

    public MainThread(String name, long cycleNanos, long minSleepNanos, int priority) {
        this(name, cycleNanos, minSleepNanos, priority, AffinityLock.cpuLayout());
    }

    public MainThread(String name, long cycleNanos, long minSleepNanos, int priority, CpuLayout layout) {
        this(name, cycleNanos, minSleepNanos, priority, layout, calculateDefaultCpuId(layout));
    }

    public MainThread(String name, long cycleNanos, long minSleepNanos, int priority, CpuLayout layout, int targetCpuId) {
        super(name);
        this.cycleNanos = cycleNanos;
        this.minSleepNanos = minSleepNanos;
        this.targetCpuId = targetCpuId;
        setPriority(priority);
    }

    /**
     * Logic to pick only physical cores (threadId == 0), falling back to 0.
     * Replicates the Kotlin: (layout.cpus() - 1 downTo 0).firstOrNull { ... }
     */
    private static int calculateDefaultCpuId(CpuLayout layout) {
        for (int i = layout.cpus() - 1; i >= 0; i--) {
            if (layout.threadId(i) == 0) {
                return i;
            }
        }
        return 0;
    }

    /**
     * Performs a cycle.
     * Assumed not to throw exceptions. If one is thrown, the thread will stop.
     */
    public abstract void cycle();

    @Override
    public void run() {
        AffinityLock affinityLock = null;

        // only acquire lock if we have at least 2 processors (threads) and not aarch64
        boolean isAarch64 = "aarch64".equals(System.getProperty("os.arch"));
        if (!isAarch64 && Runtime.getRuntime().availableProcessors() > 1) {
            logger.debug("Locking main thread to CPU ID {}", targetCpuId);
            affinityLock = AffinityLock.acquireLock(targetCpuId);
        }

        try {
            running = true;
            while (running && !isInterrupted()) {
                long startTime = System.nanoTime();

                cycle();

                long endTime = System.nanoTime();
                long elapsedNanos = endTime - startTime;
                long sleepNanos = cycleNanos - elapsedNanos;

                if (sleepNanos >= minSleepNanos) {
                    Threads.preciseSleep(sleepNanos);
                }
            }
        } finally {
            if (affinityLock != null) {
                affinityLock.release();
            }
        }
    }

    @Override
    public void start() {
        super.start();
        started();
    }

    /**
     * Called when the thread is started.
     */
    public void started() {
        // Optional override
    }

    public boolean isRunning() {
        return running;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }
}