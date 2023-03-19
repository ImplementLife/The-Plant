package com.impllife.mod.worldgen;

import com.impllife.mod.BootMod;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.OreFeatureConfig;
import net.minecraft.world.gen.placement.ConfiguredPlacement;
import net.minecraft.world.gen.placement.Placement;
import net.minecraft.world.gen.placement.TopSolidRangeConfig;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

//@Mod.EventBusSubscriber(modid = BootMod.MOD_ID)
public class OreGen {

   // @SubscribeEvent
    public static void generate(final BiomeLoadingEvent event) {
        for (OreType oreType : OreType.values()) {
            OreFeatureConfig oreFeatureConfig = new OreFeatureConfig(
                OreFeatureConfig.FillerBlockType.NATURAL_STONE,
                oreType.getBlock().get().defaultBlockState(), oreType.getMaxVeinSize()
            );
            ConfiguredPlacement<TopSolidRangeConfig> configuredPlacement = Placement.RANGE.configured(
                new TopSolidRangeConfig(oreType.getMinHeight(), oreType.getMinHeight(), oreType.getMaxHeight())
            );
            ConfiguredFeature<?, ?> register = register(oreType, oreFeatureConfig, configuredPlacement);

            event.getGeneration().addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, register);
        }
    }
    public static ConfiguredFeature<?, ?> register(OreType oreType, OreFeatureConfig config, ConfiguredPlacement<TopSolidRangeConfig> placement) {
        return Registry.register(
            WorldGenRegistries.CONFIGURED_FEATURE,
            oreType.getBlock().get().getRegistryName(),
            Feature.ORE
                .configured(config)
                .decorated(placement)
                .squared()
                .count(oreType.getMaxVeinSize()));
    }
}
