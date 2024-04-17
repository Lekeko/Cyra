package com.keko.Cyra.mixin;


import carpet.script.utils.ShapeDispatcher;
import com.keko.Cyra.item.InfernumTrident;
import com.keko.Cyra.particles.ModParticles;
import com.sammy.lodestone.setup.LodestoneParticles;
import com.sammy.lodestone.systems.rendering.particle.Easing;
import com.sammy.lodestone.systems.rendering.particle.ParticleBuilders;
import com.sammy.lodestone.systems.rendering.particle.type.LodestoneParticleType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.boss.dragon.EnderDragonEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.World;
import net.minecraft.world.explosion.Explosion;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.awt.*;
import java.util.Random;

import static net.minecraft.world.explosion.Explosion.getExposure;

@Mixin(PlayerEntity.class)
public class FallDamageMixin {
	@Unique
	final int RADIUS = 5;
	@Unique
	final int DAMAGE = 6;


	@Inject(method = "handleFallDamage", at = @At("HEAD"), cancellable = true)
	private void onFall(float fallDistance, float damageMultiplier, DamageSource damageSource, CallbackInfoReturnable<Boolean> cir){
		PlayerEntity player = (PlayerEntity) (Object)this;
		double x = player.getX();
		double y = player.getY();
		double z = player.getZ();
		if (player.world.isClient && InfernumTrident.hasRiptided){
			Color startingColor = new Color(255, 225, 128, 255);
			Color endingColor = new Color(255, 178, 60, 255);
			LodestoneParticleType particles;
			for (int i = 0; i < 19; i++) {
				double d1;
				double d2;
				d1 =  new Random().nextFloat() * 3 - 1.5f;
				d2 =  new Random().nextFloat()* 3 - 1.5f;

				if (new Random().nextBoolean()){
					 particles = ModParticles.FLAME;
				}else particles = ModParticles.POWER;



				ParticleBuilders.create(particles)
					.setScale(1, 2)
					.setColor(startingColor, endingColor)
					.setLifetime(50)
					.setMotion(d1, 0.15f, d2)
					.setSpin(new Random().nextFloat(), new Random().nextFloat())
					.spawn(player.world, x, y + 0.2, z);
			}

		}

		if (InfernumTrident.hasRiptided && !player.world.isClient){
			cir.setReturnValue(false);
			player.getItemCooldownManager().set(player.getActiveItem().getItem(), 50);


				player.world.playSound((PlayerEntity) null, player.getX(), player.getY(), player.getZ(), SoundEvents.BLOCK_BEACON_ACTIVATE , SoundCategory.NEUTRAL, 3.5F, 5.4F / (player.world.getRandom().nextFloat() * 0.4F + 0.8F));
				player.world.playSound((PlayerEntity) null, player.getX(), player.getY(), player.getZ(), SoundEvents.ENTITY_DRAGON_FIREBALL_EXPLODE, SoundCategory.NEUTRAL, 3.5F, 2.4F / (player.world.getRandom().nextFloat() * 0.4F + 0.8F));
				player.world.playSound((PlayerEntity) null, player.getX(), player.getY(), player.getZ(), SoundEvents.ENTITY_PLAYER_HURT_ON_FIRE, SoundCategory.NEUTRAL, 5.5F, 2.4F / (player.world.getRandom().nextFloat() * 0.4F + 0.8F));
				player.world.playSound((PlayerEntity) null, player.getX(), player.getY(), player.getZ(), SoundEvents.BLOCK_FIRE_EXTINGUISH, SoundCategory.NEUTRAL, 5.5F, 2.4F / (player.world.getRandom().nextFloat() * 0.4F + 0.8F));
				player.world.playSound((PlayerEntity) null, player.getX(), player.getY(), player.getZ(), SoundEvents.ENTITY_PLAYER_HURT_ON_FIRE, SoundCategory.NEUTRAL, 5.5F, 2.4F / (player.world.getRandom().nextFloat() * 0.4F + 0.8F));
				player.world.playSound((PlayerEntity) null, player.getX(), player.getY(), player.getZ(), SoundEvents.BLOCK_FURNACE_FIRE_CRACKLE, SoundCategory.NEUTRAL, 3.5F, 2.4F / (player.world.getRandom().nextFloat() * 0.4F + 0.8F));

				for (int i = -RADIUS; i <= RADIUS; i++) {
					for (int j = -RADIUS; j <= RADIUS; j++) {
						for (int k = -RADIUS; k <= RADIUS; k++) {
							if (i * i + j * j + k * k <= RADIUS * RADIUS) {
								BlockPos blockPos = new BlockPos(x + i, y + j, z + k);
								Box box = new Box(blockPos);
								if (!player.world.isClient) {
									for (Entity entity : player.world.getOtherEntities(null, box)) {
										if (!(entity instanceof ItemEntity) && entity != player) {
											double distance = player.squaredDistanceTo(entity);
											entity.damage(DamageSource.MAGIC, fallDistance);
										}
										if (!player.world.isClient && entity != player)
											launchPlayer(entity.getBlockPos(), entity, 0);
									}
								}
							}
						}
					}

			    }
			player.getItemCooldownManager().set(player.getActiveItem().getItem().asItem(), 40);

			InfernumTrident.hasRiptided = false;
		}
	}



	private static void launchPlayer(BlockPos blockPos, Entity player, float xDecreaser) {
		double decreaser = 2.1;
		double bonusJumpV = 0.1;
		if(!player.getWorld().isClient){
			float intensity = (float) (Math.abs(player.getX() - blockPos.getX()) + Math.abs(player.getY() - blockPos.getY()) + Math.abs(player.getZ() - blockPos.getZ()));
			Vec3i vec3i = new Vec3i(blockPos.getX(), blockPos.getY(), blockPos.getZ());
			if (intensity <= 15) {

				double xx = player.getX() - blockPos.getX();
				double yy = player.getY() - blockPos.getY();
				double zz = player.getZ() - blockPos.getZ();
				double aa = Math.sqrt(xx * xx + yy * yy + zz * zz);
				if (aa != 0.0) {
					xx /= aa;
					yy /= aa;
					zz /= aa;
					double ab = (double) getExposure(new Vec3d(blockPos.getX(), blockPos.getY(), blockPos.getZ()), player);
					double ac = (2.0 - 0.1) * ab;
					double ad = ac;
					player.addVelocity((xx * ad)/(decreaser + xDecreaser), (yy * ad)/(decreaser+ xDecreaser) + bonusJumpV, (zz * ad)/(decreaser+ xDecreaser) );
					player.velocityModified = true;
				}
			}
		}
	}
}


