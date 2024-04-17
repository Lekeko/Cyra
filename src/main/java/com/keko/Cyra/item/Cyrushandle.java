package com.keko.Cyra.item;

import com.keko.Cyra.entity.CyrusProjectile;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class Cyrushandle extends Item {
	PlayerEntity owner;
	CyrusProjectile projectile;
	final double modifierPLUH = 1.2;
	static boolean used = false;


	public Cyrushandle(Settings settings) {
		super(settings);
		used = false;
	}

	public Cyrushandle(Settings settings, PlayerEntity player){
		super(settings);
		owner = player;
	}


	public void setProjectile(CyrusProjectile projectile) {
		this.projectile = projectile;
	}

	@Override
	public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
		super.inventoryTick(stack, world, entity, slot, selected);
	}

	public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
		ItemStack itemStack = user.getStackInHand(hand);
		if (!used){
			Vec3d vec3d = user.getRotationVec(1f);
			vec3d =  vec3d.multiply(modifierPLUH);
			user.setVelocity(vec3d.x * Math.abs(vec3d.y + 1),  vec3d.y + 0.769696969f, vec3d.z *  Math.abs(vec3d.y + 1));
			user.velocityModified = true;
			used = true;
		}
			return TypedActionResult.success(itemStack, world.isClient());

	}
}
