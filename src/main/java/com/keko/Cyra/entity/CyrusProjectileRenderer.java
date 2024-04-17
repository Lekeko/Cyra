package com.keko.Cyra.entity;

import com.keko.Cyra.CyraMod;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3f;

@Environment(EnvType.CLIENT)
public class CyrusProjectileRenderer extends EntityRenderer<CyrusProjectile> {

	private static final Identifier TEXTURE = new Identifier(CyraMod.MOD_ID, "textures/entity/cyrus_projectile.png");
	private final CyrusProjectileModel model;


	public CyrusProjectileRenderer(EntityRendererFactory.Context ctx, Identifier identifier, EntityModelLayer cyrusProjectile) {
		super(ctx);
		this.model = new CyrusProjectileModel(ctx.getPart(ModModelLayers.CYRUS_PROJECTILE));
	}



	@Override
	public void render(CyrusProjectile mobEntity, float f, float g, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i) {
		matrixStack.push();
		matrixStack.multiply(Vec3f.POSITIVE_Z.getDegreesQuaternion(MathHelper.lerp(g, mobEntity.prevPitch, mobEntity.getPitch()) + 90.0F));
		matrixStack.multiply(Vec3f.POSITIVE_X.getDegreesQuaternion(MathHelper.lerp(g, mobEntity.prevPitch, mobEntity.getPitch()) + 90.0F));
		matrixStack.scale(1f, 1f, 1f);
		matrixStack.translate(0.0, -1.5, 0.0);
		VertexConsumer vertexConsumer = ItemRenderer.getDirectItemGlintConsumer(vertexConsumerProvider, this.model.getLayer(this.getTexture(mobEntity)), false, mobEntity.isEnchanted());
		this.model.render(matrixStack, vertexConsumer, i, OverlayTexture.DEFAULT_UV, 1.0f, 1.0f, 1.0f, 1.0f);
		matrixStack.pop();
		super.render(mobEntity, f, g, matrixStack, vertexConsumerProvider, i);
	}

	@Override
	public Identifier getTexture(CyrusProjectile entity) {
		return TEXTURE;
	}
}
