package de.maxikg.minepm.reporter;

import de.maxikg.minepm.reporter.adapter.ReportingAdapter;
import org.aspectj.lang.Signature;
import org.bukkit.entity.Player;

import java.util.Collection;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Reporter {

    private static volatile boolean initialized = false;
    private static ExecutorService worker;
    private static ReportingAdapter adapter;

    private Reporter() {
    }

    public static synchronized void init(ReportingAdapter adapter) {
        if (initialized)
            throw new IllegalStateException("Already initialized.");
        Reporter.initialized = true;

        Objects.requireNonNull(adapter, "adapter must be not null.");
        try {
            adapter.init();
        } catch (Throwable throwable) {
            Reporter.initialized = false;
            throw new RuntimeException(throwable);
        }

        Reporter.worker = Executors.newSingleThreadExecutor();
        Reporter.adapter = adapter;
    }

    public static void reportEventExecution(String eventClass, Signature signature, long millis, boolean async) {
        checkInitialized();
        worker.submit((Runnable) () -> adapter.saveEventExecutionReport(eventClass, signature, millis, async));
    }

    public static void reportChunkLoad(Object world, int x, int z, long millis) {
        checkInitialized();
        worker.submit((Runnable) () -> adapter.saveChunkLoadReport(world, x, z, millis));
    }

    public static void reportTps(double tps) {
        checkInitialized();
        worker.submit((Runnable) () -> adapter.saveTps(tps));
    }

    public static void reportPlayers(Collection<? extends Player> players, int maxPlayers) {
        checkInitialized();
        worker.submit((Runnable) () -> adapter.savePlayers(players, maxPlayers));
    }

    public static void shutdown() {
        checkInitialized();
        worker.shutdown();
        worker = null;
        try {
            adapter.shutdown();
        } catch (Throwable throwable) {
            throw new RuntimeException(throwable);
        }
        adapter = null;
        initialized = false;
    }

    private static void checkInitialized() {
        if (!initialized)
            throw new IllegalStateException("Not initialized.");
    }
}
