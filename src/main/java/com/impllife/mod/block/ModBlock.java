package com.impllife.mod.block;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class ModBlock extends Block {
    public ModBlock(Properties properties) {
        super(properties);
    }

    public static class Builder {
        ModBlock build() {
            return new ModBlock(AbstractBlock.Properties.of(Material.STONE));
        }
    }
}
