package com.impllife.mod.items;

import com.impllife.mod.BootMod;
import net.minecraft.item.Item;
import net.minecraft.item.SnowballItem;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

import static com.impllife.mod.items.ModItemGroup.GEOLOGY_TAB;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, BootMod.MOD_ID);
    public static final Map<String, RegistryObject<Item>> items = new HashMap<>();
    public static void register(String id) {
        ITEMS.register(id, MaterialItem::new);
    }
    public static void register(String id, Supplier<Item> item) {
        ITEMS.register(id, item);
    }

    static {
        register("cobble", () -> new SnowballItem(new Item.Properties().tab(GEOLOGY_TAB)));

        register("copper_cobble");
        register("copper_cobble_ore");
        register("copper_cobble_crushed");
        register("copper_cobble_ore_crushed");

        register("tin_cobble");
        register("tin_cobble_ore");
        register("tin_cobble_crushed");
        register("tin_cobble_ore_crushed");


        register("wire_copper");
        register("wire_steel");
    }

}
