package com.keko.Cyra.entity;

import com.keko.Cyra.CyraMod;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import org.quiltmc.qsl.entity.api.QuiltEntityTypeBuilder;

public class ModEntities {


	public static final EntityType<CyrusProjectile> CYRUS_PROJECTILE = Registry.register(Registry.ENTITY_TYPE,
		new Identifier(CyraMod.MOD_ID, "cyrus_projectile"), QuiltEntityTypeBuilder.<CyrusProjectile>create(SpawnGroup.MISC, CyrusProjectile::new)
			.setDimensions(EntityDimensions.fixed(0.7f, 0.7f)).build());

	public static void registerModEntities(){

	}
}
