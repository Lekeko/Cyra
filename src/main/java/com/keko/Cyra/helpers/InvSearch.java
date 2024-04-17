package com.keko.Cyra.helpers;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class InvSearch {
	public static ItemStack hasItemInInv(PlayerEntity user, Item item){
		for (ItemStack stack : user.getInventory().main){
			if (stack.isOf(item)){
				return stack;
			}
		}
		return null;
	}
}
