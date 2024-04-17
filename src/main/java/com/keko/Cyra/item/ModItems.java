package com.keko.Cyra.item;

import com.keko.Cyra.CyraMod;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ToolMaterial;
import net.minecraft.item.ToolMaterials;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;
import net.minecraft.util.registry.Registry;
import org.quiltmc.qsl.item.setting.api.QuiltItemSettings;

public class ModItems {

	public static final Item CYRA_LORD_TRIDENT = registerItem("cyra_lord_trident", new InfernumTrident(new QuiltItemSettings().group(ItemGroup.COMBAT).rarity(Rarity.EPIC)));
	public static final Item OLD_CYRA_LORD_TRIDENT = registerItem("old_cyra_lord_trident", new OldInfernumTrident(new QuiltItemSettings().group(ItemGroup.MATERIALS)));
	public static final Item CYRUS_HANDLE = registerItem("cyrus_handle", new Cyrushandle(new QuiltItemSettings().group(ItemGroup.MATERIALS)));
	public static final Item CYRA_GEM = registerItem("cyra_gem", new CyraGem(new QuiltItemSettings().group(ItemGroup.MATERIALS)));
	public static final Item OLD_CYRUS_AXE = registerItem("old_cyrus_axe", new OldCyrusAxe(new QuiltItemSettings().group(ItemGroup.MATERIALS)));
	public static final Item CYRUS_AXE = registerItem("cyrus_axe", new CyrusAxe(ToolMaterials.NETHERITE, 9f, -2.5f, new Item.Settings().maxDamage(0).rarity(Rarity.RARE).group(ItemGroup.COMBAT)));

	private static Item registerItem(String name , Item item){
		return Registry.register(Registry.ITEM, new Identifier(CyraMod.MOD_ID, name), item);
	}

	public static void registerModitems(){

	}
}
