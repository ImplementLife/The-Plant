package com.impllife.mod.worldgen;

import com.impllife.mod.block.ModBlocks;
import net.minecraft.block.Block;
import net.minecraftforge.common.util.Lazy;

import java.util.NoSuchElementException;

public enum OreType {
    COPPER_ORE(Lazy.of(ModBlocks.COPPER_ORE.getBlock()), 10, 15, 25, 50),
    TIN_ORE(Lazy.of(ModBlocks.TIN_ORE.getBlock()), 10, 15, 25, 50),
    ;
    private final Lazy<Block> block;
    private final int minVeinSize;
    private final int maxVeinSize;
    private final int minHeight;
    private final int maxHeight;

    OreType(Lazy<Block> block, int minVeinSize, int maxVeinSize, int minHeight, int maxHeight) {
        this.block = block;
        this.maxVeinSize = maxVeinSize;
        this.minVeinSize = minVeinSize;
        this.maxHeight = maxHeight;
        this.minHeight = minHeight;
    }

    public Lazy<Block> getBlock() {
        return block;
    }

    public int getMaxVeinSize() {
        return maxVeinSize;
    }

    public int getMinVeinSize() {
        return minVeinSize;
    }

    public int getMaxHeight() {
        return maxHeight;
    }

    public int getMinHeight() {
        return minHeight;
    }
    public static OreType get(Block block) {
        for (OreType value : values()) {
            if (block == value.block.get()) {
                return value;
            }
        }
        throw new NoSuchElementException();
    }
}
