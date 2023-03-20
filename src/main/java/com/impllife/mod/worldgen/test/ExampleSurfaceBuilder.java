package com.impllife.mod.worldgen.test;

import com.google.common.collect.ImmutableList;
import com.impllife.mod.block.ModBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.SharedSeedRandom;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.IChunk;
import net.minecraft.world.gen.PerlinNoiseGenerator;
import net.minecraft.world.gen.surfacebuilders.DefaultSurfaceBuilder;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilderConfig;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Random;

public class ExampleSurfaceBuilder extends DefaultSurfaceBuilder {
    private static final Logger LOG = LogManager.getLogger();
    private BlockState COPPER_ORE;
    private BlockState TIN_ORE;
    private PerlinNoiseGenerator generator;

    public ExampleSurfaceBuilder() {
        super(SurfaceBuilderConfig.CODEC);
        COPPER_ORE = ModBlocks.COPPER_ORE.getBlock().get().defaultBlockState();
        TIN_ORE = ModBlocks.TIN_ORE.getBlock().get().defaultBlockState();

        //new ResourceLocation("minecraft", "textures/items/" + aResourceLocation + ".png");
        LOG.info("ExampleSurfaceBuilder created");
    }

    private ChunkPos pos;
    private boolean isProcessingChunk;
    private boolean isProcessingChunk(IChunk chunk) {
        if (pos == null || !pos.equals(chunk.getPos())) {
            pos = chunk.getPos();
            isProcessingChunk = generator.getValue(pos.x, pos.z, false) + 0.5 < 0.88;
        }
        return isProcessingChunk;
    }
    public PerlinNoiseGenerator getGenerator(long seed) {
        if (generator == null) {
            generator = new PerlinNoiseGenerator(new SharedSeedRandom(seed >> 2), ImmutableList.of(-2, -1, 0));
        }
        return generator;
    }
    private final int maxH = 176;
    private final int minH = 122;

    protected void generate(Random random, IChunk chunk, Biome biome, int x, int z, int terrainHeight, double noise, long seed, StatesBlocks statesBlocks) {
        PerlinNoiseGenerator generator = getGenerator(seed);
        if (isProcessingChunk(chunk)) return;

        BlockPos.Mutable mutable = new BlockPos.Mutable();
        double value = generator.getSurfaceNoiseValue(x * 0.0625, z * 0.0625, 0.0625, 0.0625) + 0.5;

        int space = (int) ((maxH - minH) * value);
        int escape = (maxH - minH - space) / 2;

        for (int y = minH + 1 + escape; y < maxH - escape; y++) {
            mutable.set(x, y, z);
//                BlockState currentBlockInWorld = chunk.getBlockState(mutable);
//                if (!currentBlockInWorld.isAir()) continue;
            if (random.nextDouble() > 0.67) {
                chunk.setBlockState(mutable, COPPER_ORE, false);
            } else {
                chunk.setBlockState(mutable, Blocks.STONE.defaultBlockState(), false);
            }
        }
        mutable.set(x, 100, z);
        chunk.setBlockState(mutable, Blocks.GLASS.defaultBlockState(), false);
    }

    @Override
    public void apply(Random random, IChunk chunk, Biome biome, int x, int z, int h, double noise, BlockState defaultBlock, BlockState defaultFluid, int seaLevel, long seed, SurfaceBuilderConfig config) {
        this.generate(random, chunk, biome, x, z, h, noise, seed, new StatesBlocks(defaultBlock, defaultFluid, config.getTopMaterial(), config.getUnderMaterial(), config.getUnderwaterMaterial(), seaLevel));
    }

    private void example(Random random, IChunk chunk, Biome biome, int x, int z, int terrainHeight, double noise, BlockState defaultBlock, BlockState defaultFluid, BlockState topBlock, BlockState middleBlock, BlockState underwaterBlock, int seaLevel) {
        if (true) return; // !!! that example code, don't use it !!!

        BlockPos.Mutable mutable = new BlockPos.Mutable();
        int depth = -1; // Will be used to know how deep we are in solid blocks so we know when to stop placing middleBlock
        int scaledNoise = (int)(noise / 3.0D + 3.0D + random.nextDouble() * 0.25D);

        // Start at top land and loop downward
        for (int y = terrainHeight; y >= 0; --y) {

            // Get the block in the world (Overworld will always give Air, Water, or Stone)
            mutable.set(x, y, z);
            BlockState currentBlockInWorld = chunk.getBlockState(mutable);

            // Reset the depth counter as we are not in land anymore
            if (currentBlockInWorld.isAir()) {
                depth = -1;
            }

            // Else if we are at liquid, we can use this to swap the default fluid in your biome
            else if (!currentBlockInWorld.getFluidState().isEmpty()) {
                chunk.setBlockState(mutable, defaultFluid, false);
            }

            // We are in solid land now. Skip Bedrock as we shouldn't replace that
            else if (currentBlockInWorld.getBlock() != Blocks.BEDROCK) {
                // -1 depth means we are switching from air to solid land. Place the surface block now
                if (depth == -1) {
                    // Signal that depth is now on the surface so we can start placing middle blocks when moving down next loop.
                    depth = 0;

                    // The typical normal dry surface of the biome.
                    if(y >= seaLevel - 1){
                        chunk.setBlockState(mutable, topBlock, false);
                    }
                    // Places middle block when starting to go under sealevel.
                    // Think of this as the top block of the bottom of shallow lakes in your biome.
                    else if(y >= seaLevel - scaledNoise - 7){
                        chunk.setBlockState(mutable, middleBlock, false);
                    }
                    // Places the underwater block when really deep under sealevel instead.
                    // This is like the top block of the sea floor.
                    else{
                        chunk.setBlockState(mutable, underwaterBlock, false);
                    }
                }
                // Place block only when under surface and down to as deep as the scaledNoise says to go.
                else if (depth <= scaledNoise) {
                    // Increment depth to keep track of how deep we have gone
                    depth++;
                    chunk.setBlockState(mutable, middleBlock, false);
                }
                // Place the default block if not placing top, middle, or underwater block anymore.
                else {
                    chunk.setBlockState(mutable, defaultBlock, false);
                }
            }
        }
    }
}
