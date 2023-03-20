package com.impllife.mod.worldgen.test;

import com.impllife.mod.BootMod;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.carver.ConfiguredCarver;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilder;
import net.minecraftforge.common.world.BiomeGenerationSettingsBuilder;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

import static com.impllife.mod.BootMod.MOD_ID;

@Mod.EventBusSubscriber(modid = BootMod.MOD_ID)
public class InitSurfaceBuilder {
    private static final Logger LOG = LogManager.getLogger();
    public static final DeferredRegister<SurfaceBuilder<?>> SURFACE_BUILDER = DeferredRegister.create(ForgeRegistries.SURFACE_BUILDERS, MOD_ID);
    public static final RegistryObject<ExampleSurfaceBuilder> EXAMPLE = SURFACE_BUILDER.register("example", ExampleSurfaceBuilder::new);

    @SubscribeEvent
    public static void generate(final BiomeLoadingEvent event) {
        BiomeGenerationSettingsBuilder generation = event.getGeneration();

        disableVanillaGeneration(generation);

        generation.surfaceBuilder(EXAMPLE.get().configured(SurfaceBuilder.CONFIG_OCEAN_SAND));
    }

    private static void disableVanillaGeneration(BiomeGenerationSettingsBuilder generation) {
//        generation.getStructures().clear();

//        for (GenerationStage.Carving carving : GenerationStage.Carving.values()) {
////            for (Supplier<ConfiguredCarver<?>> carver : generation.getCarvers(carving)) {
////                ConfiguredCarver<?> configuredCarver = carver.get();
////
////                getId(configuredCarver, "name");
////                LOG.info("Disable carving generator: {}", carving.getName());
////            }
//            generation.getCarvers(carving).clear();
//        }

        List<Supplier<ConfiguredFeature<?, ?>>> features = generation.getFeatures(GenerationStage.Decoration.UNDERGROUND_ORES);
        List<Supplier<ConfiguredFeature<?, ?>>> toSave = new ArrayList<>();
        if (features.size() > 0) {
            toSave.add(features.get(0)); //save dirt layer later
            toSave.add(features.get(1)); //and someone else
        }

        for (GenerationStage.Decoration decor : GenerationStage.Decoration.values()) {
            generation.getFeatures(decor).clear();
        }
        features.addAll(toSave);
    }

    private static String getId(Object object, String fieldName) {
        String result = "";
        try {
            Field field = object.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            Object value = field.get(object);
            result = String.valueOf(value);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static List<ConfiguredFeature<?, ?>> s(BiomeGenerationSettingsBuilder generation) {
        List<ConfiguredFeature<?, ?>> l = new ArrayList<>();
        generation.getFeatures(GenerationStage.Decoration.UNDERGROUND_ORES).forEach(e -> l.add(e.get()));
        return l;
    }

}
