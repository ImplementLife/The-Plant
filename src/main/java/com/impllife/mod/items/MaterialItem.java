package com.impllife.mod.items;

import net.minecraft.item.Item;

import static com.impllife.mod.utils.ModItemGroup.MOD_MATERIALS_TAB;

public class MaterialItem extends Item {
    public MaterialItem() {
        super(new Item.Properties().tab(MOD_MATERIALS_TAB));
    }
}
