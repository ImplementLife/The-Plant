package com.impllife.mod.worldgen.test;

import com.impllife.mod.BootMod;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilder;
import net.minecraftforge.common.world.BiomeGenerationSettingsBuilder;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import static com.impllife.mod.BootMod.MOD_ID;
import static net.minecraft.world.gen.GenerationStage.Decoration.UNDERGROUND_ORES;

@Mod.EventBusSubscriber(modid = BootMod.MOD_ID)
public class InitSurfaceBuilder {
    public static final DeferredRegister<SurfaceBuilder<?>> SURFACE_BUILDER = DeferredRegister.create(ForgeRegistries.SURFACE_BUILDERS, MOD_ID);
    public static final RegistryObject<ExampleSurfaceBuilder> EXAMPLE = SURFACE_BUILDER.register("example", ExampleSurfaceBuilder::new);

    @SubscribeEvent
    public static void generate(final BiomeLoadingEvent event) {
        BiomeGenerationSettingsBuilder generation = event.getGeneration();

        disableVanillaGeneration(generation);

        generation.surfaceBuilder(EXAMPLE.get().configured(SurfaceBuilder.CONFIG_OCEAN_SAND));
    }

    private static void disableVanillaGeneration(BiomeGenerationSettingsBuilder generation) {
        generation.getStructures().clear();
        for (GenerationStage.Carving carving : GenerationStage.Carving.values()) {
            generation.getCarvers(carving).clear();
        }
        /*for (GenerationStage.Decoration decor : GenerationStage.Decoration.values()) {
            generation.getFeatures(decor).clear();
        }*/
        generation.getFeatures(GenerationStage.Decoration.UNDERGROUND_ORES).clear();
    }
}
