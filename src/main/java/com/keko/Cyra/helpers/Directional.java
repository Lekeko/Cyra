package com.keko.Cyra.helpers;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public abstract class Directional {
	Block[] blocks = {
		Blocks.AIR,
		Blocks.WATER,
		Blocks.VOID_AIR,
		Blocks.SNOW,
		Blocks.FIRE,
		Blocks.GRASS,
		Blocks.TALL_GRASS,
		Blocks.POPPY,
		Blocks.DANDELION,
		Blocks.BLUE_ORCHID,
		Blocks.ALLIUM,
		Blocks.AZURE_BLUET,
		Blocks.RED_TULIP,
		Blocks.ORANGE_TULIP,
		Blocks.WHITE_TULIP,
		Blocks.PINK_TULIP,
		Blocks.OXEYE_DAISY,
		Blocks.CORNFLOWER,
		Blocks.LILY_OF_THE_VALLEY,
		Blocks.WITHER_ROSE,
		Blocks.BROWN_MUSHROOM_BLOCK,
		Blocks.RED_MUSHROOM,
		Blocks.SUNFLOWER,
		Blocks.LILAC,
		Blocks.ROSE_BUSH,
		Blocks.PEONY,
		Blocks.LARGE_FERN,
	};
	//I KNOW HOW IT LOOKS LIKE I KNOW I KNWO AAAAAAAAAAAA

	public static Vec3d rayCast(World world, Entity player, Vec3d direction, int distance){
		Vec3d vec3d = new Vec3d(player.getX(), player.getY(), player.getZ());
		for (double i = 0.1; i <= distance; i+= 0.1){
			BlockState state = world.getBlockState(new BlockPos(vec3d));
			if (!state.isOf(Blocks.AIR) && !state.isOf(Blocks.WATER) && !state.isOf(Blocks.VOID_AIR) && state.isSolidBlock(world, new BlockPos(vec3d))){
				return vec3d;
			}else {
				vec3d = new Vec3d(
					direction.getX() * i + player.getX(),
					direction.getY() * i + player.getY() + 1.75 ,
					direction.getZ() * i + player.getZ());
			}
		}
		return vec3d;

	}


}
