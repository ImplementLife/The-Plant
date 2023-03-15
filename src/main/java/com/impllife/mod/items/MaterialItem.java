package com.impllife.mod.items;

import net.minecraft.item.Item;

import static com.impllife.mod.items.ModItemGroup.GEOLOGY_TAB;

public class MaterialItem extends Item {
    public MaterialItem() {
        super(new Item.Properties().tab(GEOLOGY_TAB));
    }
}
