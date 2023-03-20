package com.impllife.mod.worldgen.test;

import net.minecraft.block.BlockState;

public class StatesBlocks {
    private final BlockState defaultBlock;
    private final BlockState defaultFluid;
    private final BlockState topBlock;
    private final BlockState middleBlock;
    private final BlockState underwaterBlock;
    private final int seaLevel;

    public StatesBlocks(BlockState defaultBlock, BlockState defaultFluid, BlockState topBlock, BlockState middleBlock, BlockState underwaterBlock, int seaLevel) {
        this.defaultBlock = defaultBlock;
        this.defaultFluid = defaultFluid;
        this.topBlock = topBlock;
        this.middleBlock = middleBlock;
        this.underwaterBlock = underwaterBlock;
        this.seaLevel = seaLevel;
    }

    public BlockState getDefaultBlock() {
        return defaultBlock;
    }

    public BlockState getDefaultFluid() {
        return defaultFluid;
    }

    public BlockState getTopBlock() {
        return topBlock;
    }

    public BlockState getMiddleBlock() {
        return middleBlock;
    }

    public BlockState getUnderwaterBlock() {
        return underwaterBlock;
    }

    public int getSeaLevel() {
        return seaLevel;
    }
}
