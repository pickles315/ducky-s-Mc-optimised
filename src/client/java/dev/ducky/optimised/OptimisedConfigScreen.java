package dev.ducky.optimised;

import dev.isxander.yacl3.api.ConfigCategory;
import dev.isxander.yacl3.api.Option;
import dev.isxander.yacl3.api.OptionDescription;
import dev.isxander.yacl3.api.YetAnotherConfigLib;
import dev.isxander.yacl3.api.controller.BooleanControllerBuilder;
import dev.isxander.yacl3.api.controller.DoubleSliderControllerBuilder;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

public final class OptimisedConfigScreen {
    private OptimisedConfigScreen() {}

    public static Screen create(Screen parent) {
        return YetAnotherConfigLib.createBuilder()
                .title(Component.literal("Ducky's MC Optimised"))
                .category(ConfigCategory.createBuilder()
                        .name(Component.literal("Entity Rendering"))
                        .option(Option.<Boolean>createBuilder()
                                .name(Component.literal("Entity culling"))
                                .description(OptionDescription.of(Component.literal(
                                        "Skip rendering entities beyond the selected distance.")))
                                .binding(true, () -> PerformanceConfig.entityCulling, value -> {
                                    PerformanceConfig.entityCulling = value;
                                    PerformanceConfig.save();
                                })
                                .controller(BooleanControllerBuilder::create)
                                .build())
                        .option(Option.<Double>createBuilder()
                                .name(Component.literal("Entity render distance"))
                                .description(OptionDescription.of(Component.literal(
                                        "Maximum entity render distance in blocks.")))
                                .binding(96.0, () -> PerformanceConfig.maxEntityDistance, value -> {
                                    PerformanceConfig.maxEntityDistance = value;
                                    PerformanceConfig.save();
                                })
                                .controller(option -> DoubleSliderControllerBuilder.create(option)
                                        .range(16.0, 512.0)
                                        .step(8.0)
                                        .valueFormatter(value -> Component.literal(Math.round(value) + " blocks")))
                                .build())
                        .build())
                .category(ConfigCategory.createBuilder()
                        .name(Component.literal("Block Entity Rendering"))
                        .option(Option.<Boolean>createBuilder()
                                .name(Component.literal("Block entity culling"))
                                .description(OptionDescription.of(Component.literal(
                                        "Skip distant block entities. Globally rendered and off-screen renderers are preserved.")))
                                .binding(true, () -> PerformanceConfig.blockEntityCulling, value -> {
                                    PerformanceConfig.blockEntityCulling = value;
                                    PerformanceConfig.save();
                                })
                                .controller(BooleanControllerBuilder::create)
                                .build())
                        .option(Option.<Double>createBuilder()
                                .name(Component.literal("Block entity render distance"))
                                .description(OptionDescription.of(Component.literal(
                                        "Maximum block entity render distance in blocks.")))
                                .binding(64.0, () -> PerformanceConfig.maxBlockEntityDistance, value -> {
                                    PerformanceConfig.maxBlockEntityDistance = value;
                                    PerformanceConfig.save();
                                })
                                .controller(option -> DoubleSliderControllerBuilder.create(option)
                                        .range(16.0, 256.0)
                                        .step(8.0)
                                        .valueFormatter(value -> Component.literal(Math.round(value) + " blocks")))
                                .build())
                        .build())
                .build()
                .generateScreen(parent);
    }
}
