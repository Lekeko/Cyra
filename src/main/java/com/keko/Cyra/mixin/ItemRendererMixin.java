package com.keko.Cyra.mixin;

import com.keko.Cyra.CyraMod;
import com.keko.Cyra.item.ModItems;
import net.minecraft.client.model.ModelTransform;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.util.ModelIdentifier;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(ItemRenderer.class)
public class ItemRendererMixin {
	@ModifyVariable(method = "renderItem", at = @At(value = "HEAD"), argsOnly = true)
	public BakedModel useCyraTModel(BakedModel value, ItemStack stack,  ModelTransformation.Mode modelTransformation, boolean leftHanded, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int light, int overlay){
		if (stack.isOf(ModItems.CYRA_LORD_TRIDENT) && modelTransformation != ModelTransformation.Mode.GUI){
			return ((ItemRendererAccsesor) this).cyra$getModel().getModelManager().getModel(new ModelIdentifier(CyraMod.MOD_ID, "cyra_lord_trident_3d", "inventory"));
		}

		return value;
	}
}
