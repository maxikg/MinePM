package de.maxikg.minepm.plugin;

import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginLoader;

import java.io.File;

public class MinePMServicePlugin extends InterceptedPlugin {

    public static final PluginDescriptionFile DESCRIPTION = new PluginDescriptionFile("MinePMServicePlugin", "included", MinePMServicePlugin.class.getName());

    public MinePMServicePlugin(File dataFolder, PluginLoader pluginLoader) {
        super(dataFolder, pluginLoader, DESCRIPTION);
    }

    @Override
    public void onEnable() {
        getLogger().info("Hello World!");
    }
}
