package de.maxikg.minepm.plugin;

import com.avaje.ebean.EbeanServer;
import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginLoader;
import org.bukkit.plugin.PluginLogger;

import java.io.File;
import java.io.InputStream;
import java.util.List;
import java.util.Objects;
import java.util.logging.Logger;

public abstract class InterceptedPlugin implements Plugin {

    private final PluginLogger logger;
    private final File dataFolder;
    private final PluginLoader pluginLoader;
    private final PluginDescriptionFile description;
    private boolean enabled;

    protected InterceptedPlugin(File dataFolder, PluginLoader pluginLoader, PluginDescriptionFile description) {
        this.dataFolder = Objects.requireNonNull(dataFolder, "dataFolder must be not null.");
        this.pluginLoader = Objects.requireNonNull(pluginLoader, "pluginLoader must be not null.");
        this.description = Objects.requireNonNull(description, "description must be not null.");
        this.logger = new PluginLogger(this);
    }

    @Override
    public File getDataFolder() {
        return dataFolder;
    }

    @Override
    public PluginDescriptionFile getDescription() {
        return description;
    }

    @Override
    public FileConfiguration getConfig() {
        return null;
    }

    @Override
    public InputStream getResource(String s) {
        return null;
    }

    @Override
    public void saveConfig() {
    }

    @Override
    public void saveDefaultConfig() {
    }

    @Override
    public void saveResource(String s, boolean b) {
    }

    @Override
    public void reloadConfig() {
    }

    @Override
    public PluginLoader getPluginLoader() {
        return pluginLoader;
    }

    @Override
    public Server getServer() {
        return Bukkit.getServer();
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    @Override
    public void onDisable() {
    }

    @Override
    public void onLoad() {
    }

    @Override
    public void onEnable() {
    }

    @Override
    public boolean isNaggable() {
        return false;
    }

    @Override
    public void setNaggable(boolean b) {
    }

    @Override
    public EbeanServer getDatabase() {
        return null;
    }

    @Override
    public ChunkGenerator getDefaultWorldGenerator(String s, String s1) {
        return null;
    }

    @Override
    public Logger getLogger() {
        return logger;
    }

    @Override
    public String getName() {
        return getDescription().getName();
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] strings) {
        return null;
    }

    synchronized void enable() {
        enabled = true;
        onEnable();
    }

    synchronized void disable() {
        onDisable();
        enabled = false;
    }
}
