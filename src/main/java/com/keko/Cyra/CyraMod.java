package com.keko.Cyra;

import com.keko.Cyra.item.ModItems;
import com.keko.Cyra.particles.ModParticles;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerEntityEvents;
import net.fabricmc.fabric.api.event.player.UseItemCallback;
import net.fabricmc.fabric.api.loot.v2.LootTableEvents;
import net.minecraft.block.ChestBlock;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.DefaultAttributeRegistry;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTables;
import net.minecraft.loot.condition.LootCondition;
import net.minecraft.loot.condition.RandomChanceLootCondition;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.provider.number.UniformLootNumberProvider;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.TypedActionResult;
import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.qsl.base.api.entrypoint.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CyraMod implements ModInitializer {
	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod name as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final Logger LOGGER = LoggerFactory.getLogger("Woomp woomp");
	public static final String MOD_ID = "cyra";
	private static final Identifier BASTION_REMNANT_CHEST_LOOT_TABLE_ID  = LootTables.BASTION_TREASURE_CHEST;

	@Override
	public void onInitialize(ModContainer mod) {

		UniformLootNumberProvider poor = UniformLootNumberProvider.create(1, 1);
		LootCondition thisShitAintWorking = RandomChanceLootCondition.builder(0.2f).build();
		LootTableEvents.MODIFY.register((resourceManager, lootManager, id, tableBuilder, source) -> {
			if (source.isBuiltin() && BASTION_REMNANT_CHEST_LOOT_TABLE_ID.equals(id)) {
				LootPool poolBuilder = LootPool.builder()
					.rolls(poor)
					.conditionally(thisShitAintWorking)
					.with(ItemEntry.builder(ModItems.CYRA_GEM).build()).build();

				tableBuilder.pool(poolBuilder);
			}
		});





		LOGGER.info("Fire fly's free trough the air.");
		ModItems.registerModitems();
		ModParticles.init();

	}

}
