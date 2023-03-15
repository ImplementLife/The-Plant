package com.impllife.mod.items;

import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;

import static com.impllife.mod.block.ModBlocks.COPPER_ORE;

public class ModItemGroup {
    public static ItemGroup createGroup(String id) {
        return new ItemGroup(id) {
            @Override
            public ItemStack makeIcon() {
                return new ItemStack(COPPER_ORE.getItem().get());
            }
        };
    }
    public static ItemGroup MOD_TAB = createGroup("mod_materials_tab");
}
