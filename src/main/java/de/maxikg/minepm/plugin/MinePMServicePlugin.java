package de.maxikg.minepm.plugin;

import de.maxikg.minepm.plugin.timer.PlayerTask;
import de.maxikg.minepm.plugin.timer.TpsTask;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginLoader;

import java.io.File;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

public class MinePMServicePlugin extends InterceptedPlugin {

    public static final PluginDescriptionFile DESCRIPTION = new PluginDescriptionFile("MinePMServicePlugin", "included", MinePMServicePlugin.class.getName());
    private static final long ONE_MINUTE = TimeUnit.MINUTES.toMillis(1);

    private final boolean playerObservationEnabled;
    private final boolean tpsObservationEnabled;
    private Timer timer;

    public MinePMServicePlugin(File dataFolder, PluginLoader pluginLoader, boolean playerObservationEnabled, boolean tpsObservationEnabled) {
        super(dataFolder, pluginLoader, DESCRIPTION);

        this.playerObservationEnabled = playerObservationEnabled;
        this.tpsObservationEnabled = tpsObservationEnabled;
    }

    @Override
    public void onEnable() {
        timer = new Timer();
        if (tpsObservationEnabled)
            timer.schedule(new TpsTask(getServer()), ONE_MINUTE, ONE_MINUTE);
        if (playerObservationEnabled)
            timer.schedule(new PlayerTask(getServer()), ONE_MINUTE, ONE_MINUTE);
    }

    @Override
    public void onDisable() {
        if (timer != null)
            timer.cancel();
    }
}
