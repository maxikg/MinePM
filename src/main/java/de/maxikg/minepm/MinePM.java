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
import java.util.logging.Level;
import java.util.logging.Logger;

public class MinePM {

    private static final Logger LOGGER = Logger.getLogger(MinePM.class.getName());

    private MinePM() {
    }

    public static void premain(String args, Instrumentation instrumentation) {
        Properties config = readConfiguration();
        AspectConfiguration.load(config);

        Runtime.getRuntime().addShutdownHook(new Thread(Reporter::shutdown));
        Reporter.init(new ZeroMQAdapter(config.getProperty("zeromq", "tcp://localhost:2120"), config.getProperty("server_id", "test_server")));
        Agent.premain(args, instrumentation);
    }

    private static Properties readConfiguration() {
        Properties properties = new Properties();
        try (FileReader fileReader = new FileReader(new File("minepm", "config.properties"))) {
            properties.load(fileReader);
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "An error occurred while reading properties file.", e);
        }
        return properties;
    }
}
