package de.maxikg.minepm.aspects;

import java.util.Properties;

public class AspectConfiguration {

    public static boolean BUKKIT_EVENT_HANDLER_ENABLED = true;
    public static int BUKKIT_EVENT_HANDLER_THRESHOLD = 0;
    public static boolean SPIGOT_CHUNK_LOADER_TIMINGS_ENABLED = true;
    public static boolean BUKKIT_PLAYER_OBSERVATION_ENABLED = true;
    public static boolean BUKKIT_PLAYER_TPS_ENABLED = true;

    private AspectConfiguration() {
    }

    public static void load(Properties properties) {
        BUKKIT_EVENT_HANDLER_ENABLED = Boolean.parseBoolean(properties.getProperty("de_maxikg_minepm_aspects_BukkitEventHandler_ENABLED", Boolean.TRUE.toString()));
        BUKKIT_EVENT_HANDLER_THRESHOLD = Integer.parseInt(properties.getProperty("de_maxikg_minepm_aspects_BukkitEventHandler_THRESHOLD", "0"));
        SPIGOT_CHUNK_LOADER_TIMINGS_ENABLED = Boolean.parseBoolean(properties.getProperty("de_maxikg_minepm_aspects_SpigotChunkRegionLoader_ENABLED", Boolean.TRUE.toString()));
        BUKKIT_PLAYER_OBSERVATION_ENABLED = Boolean.parseBoolean(properties.getProperty("de_maxikg_minepm_plugin_timer_PlayerTask_ENABLED", Boolean.TRUE.toString()));
        BUKKIT_PLAYER_TPS_ENABLED = Boolean.parseBoolean(properties.getProperty("de_maxikg_minepm_plugin_timer_TpsTask_ENABLED", Boolean.TRUE.toString()));
    }

    public static boolean isPluginRequired() {
        return BUKKIT_PLAYER_OBSERVATION_ENABLED || BUKKIT_PLAYER_TPS_ENABLED;
    }
}
