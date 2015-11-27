package de.maxikg.minepm.plugin.timer;

import de.maxikg.minepm.reporter.Reporter;
import org.bukkit.Server;

import java.lang.reflect.Field;
import java.util.Objects;
import java.util.TimerTask;

public class TpsTask extends TimerTask {

    private final Server server;
    private Field consoleField;
    private Field recentTpsField;

    public TpsTask(Server server) {
        this.server = Objects.requireNonNull(server, "server must be not null.");
    }

    @Override
    public void run() {
        double[] recentTps = getRecentTps();
        if (recentTps.length > 0)
            Reporter.reportTps(recentTps[0]);
    }

    private double[] getRecentTps() {
        try {
            Object context = getConsole();
            return (double[]) getRecentTpsField(context).get(context);
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException(e);
        }
    }

    private Field getRecentTpsField(Object context) {
        if (recentTpsField == null) {
            try {
                recentTpsField = context.getClass().getField("recentTps");
            } catch (ReflectiveOperationException e) {
                throw new RuntimeException(e);
            }
        }
        return recentTpsField;
    }

    private Object getConsole() {
        try {
            return getConsoleField().get(server);
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException(e);
        }
    }

    private Field getConsoleField() {
        if (consoleField == null) {
            try {
                consoleField = server.getClass().getDeclaredField("console");
                consoleField.setAccessible(true);
            } catch (ReflectiveOperationException e) {
                throw new RuntimeException(e);
            }
        }
        return consoleField;
    }
}
