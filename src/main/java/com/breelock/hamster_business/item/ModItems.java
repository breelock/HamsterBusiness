package com.breelock.hamster_business.item;

import com.breelock.hamster_business.HamsterBusiness;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.Identifier;

public class ModItems {
    public static final Item hamster_phone = registerItem("hamster_phone", new HamsterPhoneItem(new Item.Settings()), ItemGroups.REDSTONE);


    private static Item registerItem(String id, Item item, RegistryKey<ItemGroup> itemGroup) {
         Item newItem = Registry.register(Registries.ITEM, new Identifier(HamsterBusiness.MOD_ID, id), item);
         ItemGroupEvents.modifyEntriesEvent(itemGroup).register(entries -> entries.add(item));
         return newItem;
    }

    public static void register() {
    }
}
