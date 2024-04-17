package com.keko.Cyra.item;

import net.minecraft.item.Item;

public class CyraGem extends Item {
	public CyraGem(Settings settings) {
		super(settings);
	}

	@Override
	public boolean isFireproof() {
		return true;
	}
}
