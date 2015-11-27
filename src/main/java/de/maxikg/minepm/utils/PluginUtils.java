package de.maxikg.minepm.utils;

import org.bukkit.plugin.SimplePluginManager;

import java.io.File;
import java.lang.reflect.Field;
import java.net.URISyntaxException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PluginUtils {

    private static final Logger LOGGER = Logger.getLogger(PluginUtils.class.getName());

    private PluginUtils() {
    }

    public static File getFile() {
        try {
            return new File(PluginUtils.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath());
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    public static SimplePluginManager getPluginManager(Object target) {
        try {
            Field field = target.getClass().getDeclaredField("pluginManager");
            field.setAccessible(true);
            return (SimplePluginManager) field.get(target);
        } catch (ReflectiveOperationException e) {
            LOGGER.log(Level.SEVERE, "An error occurred while intercepting MinePM service plugin.", e);
            return null;
        }
    }
}
