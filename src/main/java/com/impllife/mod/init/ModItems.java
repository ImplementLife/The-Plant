package com.impllife.mod.init;

import com.impllife.mod.BootMod;
import com.impllife.mod.items.MaterialItem;
import net.minecraft.item.Item;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, BootMod.MOD_ID);

    public static final RegistryObject<Item> WIRECOIL_COPPER = ITEMS.register("wirecoil_copper", MaterialItem::new);
    public static final RegistryObject<Item> WIRECOIL_STEEL = ITEMS.register("wirecoil_steel", MaterialItem::new);
}
