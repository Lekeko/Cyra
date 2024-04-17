package com.keko.Cyra.entity;

import com.keko.Cyra.item.ModItems;
import com.keko.Cyra.particles.ModParticles;
import com.sammy.lodestone.systems.rendering.particle.ParticleBuilders;
import com.sammy.lodestone.systems.rendering.particle.type.LodestoneParticleType;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.entity.projectile.TridentEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.awt.*;
import java.util.Random;

public class CyrusProjectile extends PersistentProjectileEntity {
	ItemStack axeStack;
	int iDontKnowHowToHandleThisStupidShitOfAGameSohereWeGo = 0;
	PlayerEntity player;
	Vec3d pos;
	private BlockState inBlockState;
	public boolean wasCalled = false;
	public static int bonusDamage;
	private static final TrackedData<Boolean> ENCHANTED;


	public CyrusProjectile(EntityType<CyrusProjectile> entityType, World world) {
		super(entityType, world);
		this.axeStack = new ItemStack(ModItems.CYRUS_AXE);
	}

	@Override
	public boolean canUsePortals() {
		return false;
	}


	@Override
	protected void onEntityHit(EntityHitResult entityHitResult) {
		entityHitResult.getEntity().damage(DamageSource.player(player),5 + bonusDamage);
	}

	@Override
	public void tick() {
		iDontKnowHowToHandleThisStupidShitOfAGameSohereWeGo++;
		if (player!= null && this.inGroundTime > 4){
			wasCalled = true;
			this.setNoClip(true);

		}

		if (!world.isClient && this.getBlockStateAtPos().isOf(Blocks.END_PORTAL) || this.getBlockStateAtPos().isOf(Blocks.NETHER_PORTAL)){
			world.setBlockState(this.getBlockPos(), Blocks.AIR.getDefaultState());

		}

		createParticles(this, world);

		if (wasCalled && player != null){
			this.pos = player.getPos();
			goToTarget(pos, this);
		}

		if (player != null){
			if (player.isDead()){
				checkForHandle(player, world);
			}
		}


		super.tick();
	}

	private void createParticlesLanded() {
		double x = this.getX();
		double y = this.getY();
		double z = this.getZ();
		if (this.world.isClient){
			Color startingColor = new Color(255, 225, 128, 100);
			Color endingColor = new Color(255, 178, 60, 100);
			LodestoneParticleType particles;
			for (int i = 0; i < 5; i++) {
				double d1;
				double d2;
				double d3;
				d1 =  new Random().nextFloat() * 5 - 1.5f;
				d2 = 0;
				d3 =  new Random().nextFloat() * 5 - 1.5f;

				if (new Random().nextBoolean()){
					particles = ModParticles.FLAME;
				}else particles = ModParticles.POWER;



				ParticleBuilders.create(particles)
					.setScale(0.5f, 1f)
					.setColor(startingColor, endingColor)
					.setLifetime(50)
					.enableNoClip()
					.setMotion(d1, d2, d3)
					.setSpin(new Random().nextFloat(), new Random().nextFloat())
					.spawn(this.world, x, y + 0.2, z);
			}

		}
	}

	@Override
	protected void onBlockCollision(BlockState state) {
		super.onBlockCollision(state);
	}


	protected void onBlockHit(BlockHitResult blockHitResult) {
		createParticlesLanded();
		SoundEvent soundEvent = SoundEvents.ENTITY_GENERIC_EXPLODE;
		float g = 1.0F;
		this.playSound(soundEvent, g, 1.0F);


		this.inBlockState = this.world.getBlockState(blockHitResult.getBlockPos());
		super.onBlockHit(blockHitResult);
		Vec3d vec3d = blockHitResult.getPos().subtract(this.getX(), this.getY(), this.getZ());
		this.setVelocity(vec3d);
		Vec3d vec3d2 = vec3d.normalize().multiply(0.05000000074505806);
		this.setPos(this.getX() - vec3d2.x, this.getY() - vec3d2.y, this.getZ() - vec3d2.z);
		this.playSound(this.getSound(), 1.0F, 1.2F / (this.random.nextFloat() * 0.2F + 0.9F));
		this.inGround = true;
		this.shake = 7;
		this.setCritical(false);
		this.setPierceLevel((byte)0);
		this.setShotFromCrossbow(false);

	}


	private void createParticles(Entity entity, World world) {
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

	private void goToTarget(Vec3d pos, Entity user){
		if (pos != null) {
			int speed = 3;
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

	@Override
	public void onPlayerCollision(PlayerEntity player) {
		boolean found = false;

		if (iDontKnowHowToHandleThisStupidShitOfAGameSohereWeGo > 10){

			assert player != null;
			checkForHandle(player, world);
		}

	}

	public void checkForHandle(PlayerEntity player, World world){
		boolean found = false;

		if (player.getOffHandStack().isOf(ModItems.CYRUS_HANDLE)){
			player.getOffHandStack().decrement(1);
			this.discard();
			player.setStackInHand(Hand.OFF_HAND, this.asItemStack());
			found = true;
		}

		for (ItemStack stack : player.getInventory().main) {
			if (stack.isOf(ModItems.CYRUS_HANDLE) && !found) {
				stack.decrement(1);
				if (!this.world.isClient && (this.inGround || this.isNoClip()) && this.shake <= 0) {
					if (this.tryPickup(player)) {
						player.sendPickup(this, 1);
						this.discard();
					}

				}
				found = true;
				return;
			}
		}

		if (!this.world.isClient && (this.inGround || this.isNoClip()) && this.shake <= 0 && !found) {
			if (this.tryPickup(player)) {
				player.sendPickup(this, 1);
				this.discard();
			}

		}


	}

	public CyrusProjectile(LivingEntity livingEntity, World world, ItemStack stack){
		super(ModEntities.CYRUS_PROJECTILE, livingEntity, world);
		this.axeStack = new ItemStack(ModItems.CYRUS_AXE);
		this.axeStack = stack.copy();
		this.player = (PlayerEntity)livingEntity;
		this.pos = livingEntity.getPos();
		this.dataTracker.set(ENCHANTED, stack.hasGlint());

	}


	@Override
	protected void initDataTracker() {
		super.initDataTracker();
		this.dataTracker.startTracking(ENCHANTED, false);
	}


	public boolean shouldRender(double cameraX, double cameraY, double cameraZ) {
		return true;
	}

	public boolean isEnchanted() {
		return (Boolean)this.dataTracker.get(ENCHANTED);
	}

	/**
	 * @return
	 */
	@Override
	protected ItemStack asItemStack() {
		return this.axeStack.copy();
	}

	static {
		ENCHANTED = DataTracker.registerData(CyrusProjectile.class, TrackedDataHandlerRegistry.BOOLEAN);
	}
}
