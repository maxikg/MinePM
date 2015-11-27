package de.maxikg.minepm.plugin;

import org.bukkit.Bukkit;
import org.bukkit.event.Event;
import org.bukkit.event.Listener;
import org.bukkit.plugin.InvalidDescriptionException;
import org.bukkit.plugin.InvalidPluginException;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginLoader;
import org.bukkit.plugin.RegisteredListener;
import org.bukkit.plugin.UnknownDependencyException;
import org.bukkit.plugin.java.JavaPluginLoader;

import java.io.File;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

public class MinePMPluginLoader implements PluginLoader {

    public static final MinePMPluginLoader INSTANCE = new MinePMPluginLoader();

    @SuppressWarnings("deprecation")
    private final JavaPluginLoader loader = new JavaPluginLoader(Bukkit.getServer());

    @Override
    public Plugin loadPlugin(File file) throws InvalidPluginException, UnknownDependencyException {
        throw new UnsupportedOperationException("Can't load plugin with " + getClass().getName() + ".");
    }

    @Override
    public PluginDescriptionFile getPluginDescription(File file) throws InvalidDescriptionException {
        return MinePMServicePlugin.DESCRIPTION;
    }

    @Override
    public Pattern[] getPluginFileFilters() {
        return new Pattern[0];
    }

    @Override
    public Map<Class<? extends Event>, Set<RegisteredListener>> createRegisteredListeners(Listener listener, Plugin plugin) {
        return loader.createRegisteredListeners(listener, plugin);
    }

    @Override
    public void enablePlugin(Plugin plugin) {
        if (!(plugin instanceof InterceptedPlugin))
            throw new UnsupportedOperationException();
        ((InterceptedPlugin) plugin).enable();
    }

    @Override
    public void disablePlugin(Plugin plugin) {
        if (!(plugin instanceof InterceptedPlugin))
            throw new UnsupportedOperationException();
        ((InterceptedPlugin) plugin).disable();
    }
}
