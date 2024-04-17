package com.keko.Cyra.item;

import com.keko.Cyra.helpers.Directional;
import com.keko.Cyra.mixin.FallDamageMixin;
import com.keko.Cyra.particles.ModParticles;
import com.mojang.datafixers.kinds.IdF;
import com.sammy.lodestone.systems.rendering.particle.ParticleBuilders;
import com.sammy.lodestone.systems.rendering.particle.type.LodestoneParticleType;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.TridentItem;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.awt.*;
import java.util.Random;

public class InfernumTrident extends TridentItem {
	static BlockPos first_pos;
	public static boolean grounded = true;
	public static boolean hasRiptided = false;
	public static boolean hasDashed = false;
	Vec3d second_pos;
	int distance = 30;
	int timer = 0;
	private int amplifierReducer = 20;
	public static boolean shouldDamage = false;

	public InfernumTrident(Settings settings) {
		super(settings);
	}



	@Override
	public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {

		if (selected && !((PlayerEntity)entity).getItemCooldownManager().isCoolingDown(this)) createParticles((PlayerEntity)entity, world);

		grounded = checkBeneathe(world, entity);
		if (grounded){
			hasDashed = false;
		}

		if (hasDashed){
			((PlayerEntity)entity).startRiptideAttack(2);
		}


		if (hasDashed && timer >0){
			goToTarget(second_pos, (PlayerEntity) entity);
			((PlayerEntity)entity).getItemCooldownManager().set(this, 80);

		}

		if (timer > 0){
			timer--;
		}

		super.inventoryTick(stack, world, entity, slot, selected);
	}

	private void createParticles(PlayerEntity entity, World world) {
		Random random = new Random();
		double x = random.nextDouble() + entity.getX() - 0.5;
		double y = random.nextDouble() * 1.5  + entity.getY() - 0.5;
		double z = random.nextDouble() + entity.getZ() - 0.5;

		LodestoneParticleType particles;
		Color startingColor = new Color(255, 225, 128, 255);
		Color endingColor = new Color(255, 178, 60, 255);

		if (new Random().nextBoolean()){
			particles = ModParticles.FLAME;
		}else particles = ModParticles.POWER;



		ParticleBuilders.create(particles)
			.setScale(0.05f)
			.setColor(startingColor, endingColor)
			.setLifetime(50)
			.setMotion((x - entity.getX()) * 0.2, 0.05f, (z - entity.getZ()) * 0.2)
			.setSpin(new Random().nextFloat() * 0.2f, new Random().nextFloat() * 0.2f)
			.spawn(world, x, y + 0.2, z);
	}

	private Boolean checkBeneathe(World world, Entity entity) {
		for (int i = -1; i <= 1; i++){
			for (int j = -1; j <= 1; j++){
				if (!world.getBlockState(new BlockPos(entity.getX() + i, entity.getY()-0.1f, entity.getZ() + j)).isOf(Blocks.AIR)){
					return true;
				}
			}
		}
		return false;
	}


	private void startMoving(PlayerEntity user) {
		double deltaX = first_pos.getX() - user.getX();
		double deltaY = first_pos.getY() - user.getY();
		double deltaZ = first_pos.getZ() - user.getZ();
		hasRiptided = true;

		user.addVelocity(deltaX / amplifierReducer, 2, deltaZ / amplifierReducer);

	}

	private void goToTarget(Vec3d pos, PlayerEntity user){
		if (pos != null) {
			int speed = 5;
			Vec3d currentPos = user.getPos();
			double distanceX = pos.getX() - currentPos.x;
			double distanceY = pos.getY() - currentPos.y;
			double distanceZ = pos.getZ() - currentPos.z;
			double distanceSquared = distanceX * distanceX + distanceY * distanceY + distanceZ * distanceZ;
			if (distanceSquared > 1.0) {

				double scale = speed / Math.sqrt(distanceSquared);
				double velocityX = distanceX * scale;
				double velocityY = distanceY * scale;
				double velocityZ = distanceZ * scale;

				user.setVelocity(velocityX, velocityY, velocityZ);
			}
		}
	}

	public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
		ItemStack itemStack = user.getStackInHand(hand);
		first_pos = new BlockPos(Directional.rayCast(world, user, user.getRotationVec(1f), distance));
		timer = 35;
		if (!hasRiptided) {
			startMoving(user);
		}else

		if (!hasDashed && hasRiptided && !grounded){
			timer = 10;
			second_pos = Directional.rayCast(world, user, user.getRotationVec(1f), distance*3);
			if (!world.getBlockState(new BlockPos(second_pos.getX(), second_pos.getY(), second_pos.getZ())).isOf(Blocks.AIR)) {
				hasDashed = true;
				goToTarget(second_pos, user);
			}
		}

		return TypedActionResult.success(itemStack, world.isClient());
	}



	@Override
	public boolean isFireproof() {
		return true;
	}
}
