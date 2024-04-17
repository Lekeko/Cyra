package com.keko.Cyra.item;

import com.keko.Cyra.entity.CyrusProjectile;
import com.keko.Cyra.helpers.Directional;
import net.minecraft.block.Blocks;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.AxeItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.ToolMaterial;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.UseAction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.Set;

public class CyrusAxe extends AxeItem {
	double projectileSpeed = 3.2f;


	public CyrusAxe(ToolMaterial material, float attackDamage, float attackSpeed, Settings settings) {
		super(material, 12f, attackSpeed, settings);
	}

	@Override
	public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {

		super.inventoryTick(stack, world, entity, slot, selected);

	}




	public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
		ItemStack itemStack = user.getStackInHand(hand);
		Vec3d vec3d = user.getRotationVec(1f);
		int bonusDamage = EnchantmentHelper.getLevel(Enchantments.SHARPNESS, itemStack);

		CyrusProjectile cyrusProjectile = new CyrusProjectile(user, world, itemStack);
		CyrusProjectile.bonusDamage = bonusDamage;
		cyrusProjectile.setProperties(user, user.getPitch(), user.getYaw(), 0.0F, 2.5F + (float)2 * 0.5F, 1.0F);

		cyrusProjectile.setPos(user.getX(), user.getEyeY(), user.getZ());
		world.spawnEntity(cyrusProjectile);
		cyrusProjectile.setYaw(user.getYaw());
		ItemStack item = new ItemStack(ModItems.CYRUS_HANDLE);
		Cyrushandle.used = false;
		user.setStackInHand(hand, ModItems.CYRUS_HANDLE.getDefaultStack());


		return TypedActionResult.success(itemStack, world.isClient());
	}



	@Override
	public boolean isFireproof() {
		return true;
	}
}
