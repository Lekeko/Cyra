package com.keko.Cyra.particles;

import com.sammy.lodestone.helpers.DataHelper;
import com.sammy.lodestone.setup.LodestoneParticles;
import com.sammy.lodestone.systems.rendering.particle.type.LodestoneParticleType;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.minecraft.particle.ParticleType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.function.BiConsumer;

public class ModParticles extends LodestoneParticles {
	public  static final LodestoneParticleType FLAME = new LodestoneParticleType();
	public  static final LodestoneParticleType POWER = new LodestoneParticleType();

	public static void init(){
		initParticles(bind(Registry.PARTICLE_TYPE));
	}

	public static void registerFactories(){
		ParticleFactoryRegistry.getInstance().register(FLAME, LodestoneParticleType.Factory::new);
	}

	private static void initParticles(BiConsumer<ParticleType<?>, Identifier> registry){
		registry.accept(FLAME, DataHelper.prefix("custom_particle_flame"));
		registry.accept(POWER, DataHelper.prefix("custom_particle_power"));
	}

	private static <T> BiConsumer<T, Identifier> bind(Registry<? super T> registry) {
		return (t, id) -> Registry.register(registry, id, t);
	}

}
