package de.maxikg.minepm.plugin.timer;

import de.maxikg.minepm.reporter.Reporter;
import org.bukkit.Server;

import java.util.Objects;
import java.util.TimerTask;

public class PlayerTask extends TimerTask {

    private final Server server;

    public PlayerTask(Server server) {
        this.server = Objects.requireNonNull(server, "server must be not null.");
    }

    @Override
    public void run() {
        Reporter.reportPlayers(server.getOnlinePlayers(), server.getMaxPlayers());
    }
}
