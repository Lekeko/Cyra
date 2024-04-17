package com.keko.Cyra.mixin;


import com.keko.Cyra.item.ModItems;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.TridentRiptideFeatureRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

import static com.keko.Cyra.CyraModClient.CYRA_TRIDENT_RIPTIDE;

@Mixin(TridentRiptideFeatureRenderer.class)
public class CyraRiptideMixin {


	@ModifyVariable(method = "render*", at = @At("STORE"))
	private VertexConsumer CyraRiptideBecauseItLooksBetter(VertexConsumer orig, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int light, LivingEntity livingEntity) {
		if (livingEntity instanceof PlayerEntity && livingEntity.isUsingRiptide() && (livingEntity.getMainHandStack().getItem() == ModItems.CYRA_LORD_TRIDENT )) {
			return vertexConsumerProvider.getBuffer(RenderLayer.getEntityCutoutNoCull(CYRA_TRIDENT_RIPTIDE));
		}
		return orig;
	}
}
