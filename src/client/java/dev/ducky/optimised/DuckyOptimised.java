package dev.ducky.optimised;

import net.fabricmc.api.ClientModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class DuckyOptimised implements ClientModInitializer {
    public static final String MOD_ID = "ducky_optimised";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitializeClient() {
        PerformanceConfig.load();
        LOGGER.info("Ducky's MC Optimised loaded. Entity distance limit: {} blocks (0 = disabled).",
                PerformanceConfig.maxEntityDistance);
    }
}
