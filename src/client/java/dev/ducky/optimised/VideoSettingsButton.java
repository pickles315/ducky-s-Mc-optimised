package dev.ducky.optimised;

import net.fabricmc.fabric.api.client.screen.v1.ScreenEvents;
import net.fabricmc.fabric.api.client.screen.v1.Screens;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.options.OptionsScreen;
import net.minecraft.network.chat.Component;

public final class VideoSettingsButton {
    private VideoSettingsButton() {}

    public static void register() {
        ScreenEvents.AFTER_INIT.register((client, screen, scaledWidth, scaledHeight) -> {
            if (screen instanceof OptionsScreen) {
                Screens.getWidgets(screen).add(Button.builder(
                                Component.literal("Ducky's Optimised"),
                                button -> client.gui.setScreen(OptimisedConfigScreen.create(screen)))
                        .bounds(scaledWidth - 132, 8, 124, 20)
                        .build());
            }
        });
    }
}
