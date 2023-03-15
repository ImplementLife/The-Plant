package com.impllife.mod.items;

import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;

import java.util.function.Supplier;

import static com.impllife.mod.block.ModBlocks.COPPER_ORE;

public class ModItemGroup {
    public static ItemGroup createGroup(String id, Supplier<Item> item) {
        ItemGroup itemGroup = new ItemGroup(id) {
            @Override
            public ItemStack makeIcon() {
                return new ItemStack(item.get());
            }
        };
        itemGroup.setRecipeFolderName(id);
        return itemGroup;
    }
    public static final ItemGroup GEOLOGY_TAB = createGroup("geology", () -> COPPER_ORE.getItem().get());
    public static final ItemGroup METALLURGICAL = createGroup("metallurgical", () -> COPPER_ORE.getItem().get());
}
