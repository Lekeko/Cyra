package com.keko.Cyra;

import com.keko.Cyra.entity.*;
import com.keko.Cyra.particles.ModParticles;
import com.sammy.lodestone.systems.rendering.particle.type.LodestoneParticleType;
import net.fabricmc.fabric.api.client.model.ModelLoadingRegistry;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.event.registry.RegistryAttribute;
import net.fabricmc.fabric.api.event.registry.RegistryAttributeHolder;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.minecraft.client.render.entity.FlyingItemEntityRenderer;
import net.minecraft.client.render.entity.TridentEntityRenderer;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.render.entity.model.EntityModelLayers;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.qsl.base.api.entrypoint.client.ClientModInitializer;

public class CyraModClient implements ClientModInitializer {
	public static final Identifier CYRA_TRIDENT_RIPTIDE = new Identifier(CyraMod.MOD_ID, "textures/entity/cyra_trident_riptide.png");
	public static final EntityModelLayer CYRUS_PROJECTILE = new EntityModelLayer(new Identifier(CyraMod.MOD_ID, "entity/cyrus_projectile"), "main");
	@Override
	public void onInitializeClient(ModContainer mod) {

		EntityRendererRegistry.register(ModEntities.CYRUS_PROJECTILE, ctx -> new CyrusProjectileRenderer(ctx, new Identifier(CyraMod.MOD_ID, "textures/entity/cyrus_projectile.png"), ModModelLayers.CYRUS_PROJECTILE));
		EntityModelLayerRegistry.registerModelLayer(CYRUS_PROJECTILE, CyrusProjectileModel::getTexturedModelData);
		ModParticles.registerFactories();
		ParticleFactoryRegistry.getInstance().register(ModParticles.FLAME, LodestoneParticleType.Factory::new);
		ParticleFactoryRegistry.getInstance().register(ModParticles.POWER, LodestoneParticleType.Factory::new);
	}
}
