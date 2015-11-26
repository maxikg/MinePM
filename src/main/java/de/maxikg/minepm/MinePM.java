package de.maxikg.minepm;

import de.maxikg.minepm.aspects.AspectConfiguration;
import de.maxikg.minepm.reporter.Reporter;
import de.maxikg.minepm.reporter.adapter.ZeroMQAdapter;
import org.aspectj.weaver.loadtime.Agent;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.instrument.Instrumentation;
import java.util.Properties;

public class MinePM {

    private MinePM() {
    }

    public static void premain(String args, Instrumentation instrumentation) {
        Properties config = readConfiguration();
        AspectConfiguration.BUKKIT_EVENT_HANDLER_THRESHOLD = Integer.parseInt(config.getProperty("de_maxikg_minepm_aspects_BukkitEventHandler_THRESHOLD", "0"));

        Runtime.getRuntime().addShutdownHook(new Thread(Reporter::shutdown));
        Reporter.init(new ZeroMQAdapter(config.getProperty("zeromq")));
        Agent.premain(args, instrumentation);
    }

    private static Properties readConfiguration() {
        Properties properties = new Properties();
        try (FileReader fileReader = new FileReader(new File("minepm", "config.properties"))) {
            properties.load(fileReader);
        } catch (IOException e) {
            // ToDo: Exception handling
            e.printStackTrace();
        }
        return properties;
    }
}
