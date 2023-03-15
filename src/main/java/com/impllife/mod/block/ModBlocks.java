package com.impllife.mod.block;

import com.impllife.mod.BootMod;
import com.impllife.mod.items.ModItems;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.BlockNamedItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.Supplier;

import static com.impllife.mod.items.ModItemGroup.MOD_TAB;

public class ModBlocks {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, BootMod.MOD_ID);

    public static final RegistryBlockContainer COPPER_ORE = registerBlock("copper_ore", () -> new Block(AbstractBlock.Properties.of(Material.STONE).requiresCorrectToolForDrops().strength(4,6)), MOD_TAB);
    public static final RegistryBlockContainer TIN_ORE = registerBlock("tin_ore", () -> new Block(AbstractBlock.Properties.of(Material.STONE).requiresCorrectToolForDrops().strength(2,3)), MOD_TAB);

    private static <T extends Block> RegistryBlockContainer registerBlock(String id, Supplier<T> block, ItemGroup group) {
        RegistryObject<Block> result = BLOCKS.register(id, block);
        RegistryObject<Item> itemRegistryObject = registerBlockItem(id, result, group);
        return new RegistryBlockContainer(result, itemRegistryObject);
    }
    private static <T extends Block> RegistryObject<Item> registerBlockItem(String name, Supplier<T> registryObjectBlock, ItemGroup group) {
        return ModItems.ITEMS.register(name, () -> new BlockNamedItem(registryObjectBlock.get(), new Item.Properties().tab(group)));
    }

    public static class RegistryBlockContainer {
        private final RegistryObject<Block> block;
        private final RegistryObject<Item> item;

        public RegistryBlockContainer(RegistryObject<Block> block, RegistryObject<Item> item) {
            this.block = block;
            this.item = item;
        }

        public RegistryObject<Block> getBlock() {
            return block;
        }
        public RegistryObject<Item> getItem() {
            return item;
        }
    }
}
