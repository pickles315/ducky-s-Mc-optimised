package dev.ducky.optimised;

import net.fabricmc.loader.api.FabricLoader;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;

public final class PerformanceConfig {
    private static final Path CONFIG_PATH =
            FabricLoader.getInstance().getConfigDir().resolve("ducky-optimised.properties");

    public static volatile double maxEntityDistance = 96.0;

    private PerformanceConfig() {}

    public static void load() {
        Properties properties = new Properties();

        if (Files.exists(CONFIG_PATH)) {
            try (Reader reader = Files.newBufferedReader(CONFIG_PATH)) {
                properties.load(reader);
            } catch (IOException exception) {
                DuckyOptimised.LOGGER.warn("Could not read Ducky Optimised config; using defaults.", exception);
            }
        } else {
            properties.setProperty("maxEntityDistance", "96");
            save(properties);
        }

        try {
            double configured = Double.parseDouble(properties.getProperty("maxEntityDistance", "96"));
            maxEntityDistance = Math.max(0.0, Math.min(512.0, configured));
        } catch (NumberFormatException exception) {
            maxEntityDistance = 96.0;
            DuckyOptimised.LOGGER.warn("Invalid maxEntityDistance value; using 96 blocks.");
        }
    }

    private static void save(Properties properties) {
        try {
            Files.createDirectories(CONFIG_PATH.getParent());
            try (Writer writer = Files.newBufferedWriter(CONFIG_PATH)) {
                properties.store(writer, "Ducky's MC Optimised client settings");
            }
        } catch (IOException exception) {
            DuckyOptimised.LOGGER.warn("Could not create Ducky Optimised config.", exception);
        }
    }
}
