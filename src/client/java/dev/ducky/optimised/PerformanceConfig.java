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

    public static volatile boolean entityCulling = true;
    public static volatile double maxEntityDistance = 96.0;
    public static volatile boolean blockEntityCulling = true;
    public static volatile double maxBlockEntityDistance = 64.0;

    private PerformanceConfig() {}

    public static void load() {
        Properties properties = new Properties();
        if (Files.exists(CONFIG_PATH)) {
            try (Reader reader = Files.newBufferedReader(CONFIG_PATH)) {
                properties.load(reader);
            } catch (IOException exception) {
                DuckyOptimised.LOGGER.warn("Could not read Ducky Optimised config; using defaults.", exception);
            }
        }

        entityCulling = Boolean.parseBoolean(properties.getProperty("entityCulling", "true"));
        maxEntityDistance = readDistance(properties, "maxEntityDistance", 96.0);
        blockEntityCulling = Boolean.parseBoolean(properties.getProperty("blockEntityCulling", "true"));
        maxBlockEntityDistance = readDistance(properties, "maxBlockEntityDistance", 64.0);
        save();
    }

    private static double readDistance(Properties properties, String key, double fallback) {
        try {
            return Math.max(16.0, Math.min(512.0,
                    Double.parseDouble(properties.getProperty(key, Double.toString(fallback)))));
        } catch (NumberFormatException exception) {
            DuckyOptimised.LOGGER.warn("Invalid {} value; using {} blocks.", key, fallback);
            return fallback;
        }
    }

    public static synchronized void save() {
        Properties properties = new Properties();
        properties.setProperty("entityCulling", Boolean.toString(entityCulling));
        properties.setProperty("maxEntityDistance", Double.toString(maxEntityDistance));
        properties.setProperty("blockEntityCulling", Boolean.toString(blockEntityCulling));
        properties.setProperty("maxBlockEntityDistance", Double.toString(maxBlockEntityDistance));
        try {
            Files.createDirectories(CONFIG_PATH.getParent());
            try (Writer writer = Files.newBufferedWriter(CONFIG_PATH)) {
                properties.store(writer, "Ducky's MC Optimised client settings");
            }
        } catch (IOException exception) {
            DuckyOptimised.LOGGER.warn("Could not save Ducky Optimised config.", exception);
        }
    }
}
