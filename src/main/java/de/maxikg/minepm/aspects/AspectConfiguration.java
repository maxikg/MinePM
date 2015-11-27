package de.maxikg.minepm.aspects;

import java.util.Properties;

public class AspectConfiguration {

    public static boolean BUKKIT_EVENT_HANDLER_ENABLED = true;
    public static int BUKKIT_EVENT_HANDLER_THRESHOLD = 0;

    private AspectConfiguration() {
    }

    public static void load(Properties properties) {
        BUKKIT_EVENT_HANDLER_ENABLED = Boolean.parseBoolean(properties.getProperty("de_maxikg_minepm_aspects_BukkitEventHandler_ENABLED", Boolean.TRUE.toString()));
        BUKKIT_EVENT_HANDLER_THRESHOLD = Integer.parseInt(properties.getProperty("de_maxikg_minepm_aspects_BukkitEventHandler_THRESHOLD", "0"));
    }
}
