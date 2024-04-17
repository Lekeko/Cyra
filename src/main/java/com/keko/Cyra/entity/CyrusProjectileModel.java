package com.keko.Cyra.entity;

import com.mojang.blaze3d.vertex.VertexConsumer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.*;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.model.SinglePartEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class CyrusProjectileModel extends Model {

	private final ModelPart cyrus_projectile;

	public CyrusProjectileModel(ModelPart part) {
		super(RenderLayer::getEntitySolid);
		this.cyrus_projectile = part;
	}

	public static TexturedModelData getTexturedModelData() {
		ModelData modelData = new ModelData();
		ModelPartData modelPartData = modelData.getRoot();
		ModelPartData bone = modelPartData.addChild("bone", ModelPartBuilder.create().uv(19, 0).cuboid(-8.0F, -2.0F, 3.0F, 1.0F, 1.0F, 2.0F, new Dilation(0.0F))
			.uv(0, 13).cuboid(-8.0F, -4.0F, 4.0F, 1.0F, 2.0F, 3.0F, new Dilation(0.0F))
			.uv(8, 0).cuboid(-8.0F, -4.0F, 3.0F, 1.0F, 1.0F, 1.0F, new Dilation(0.0F))
			.uv(19, 12).cuboid(-8.0F, -5.0F, 5.0F, 1.0F, 1.0F, 3.0F, new Dilation(0.0F))
			.uv(8, 18).cuboid(-8.0F, -6.0F, 6.0F, 1.0F, 1.0F, 3.0F, new Dilation(0.0F))
			.uv(0, 18).cuboid(-8.0F, -7.0F, 7.0F, 1.0F, 1.0F, 3.0F, new Dilation(0.0F))
			.uv(17, 8).cuboid(-8.0F, -8.0F, 8.0F, 1.0F, 1.0F, 3.0F, new Dilation(0.0F))
			.uv(7, 7).cuboid(-8.0F, -2.0F, 6.0F, 1.0F, 1.0F, 1.0F, new Dilation(0.0F))
			.uv(16, 4).cuboid(-8.0F, -9.0F, 9.0F, 1.0F, 1.0F, 3.0F, new Dilation(0.0F))
			.uv(9, 10).cuboid(-8.0F, -14.0F, 10.0F, 1.0F, 5.0F, 3.0F, new Dilation(0.0F))
			.uv(0, 0).cuboid(-8.0F, -11.0F, 4.0F, 1.0F, 1.0F, 6.0F, new Dilation(0.0F))
			.uv(14, 15).cuboid(-8.0F, -10.0F, 4.0F, 1.0F, 1.0F, 3.0F, new Dilation(0.0F))
			.uv(5, 18).cuboid(-8.0F, -9.0F, 4.0F, 1.0F, 1.0F, 2.0F, new Dilation(0.0F))
			.uv(0, 7).cuboid(-8.0F, -12.0F, 5.0F, 1.0F, 1.0F, 5.0F, new Dilation(0.0F))
			.uv(10, 5).cuboid(-8.0F, -13.0F, 6.0F, 1.0F, 1.0F, 4.0F, new Dilation(0.0F))
			.uv(14, 0).cuboid(-8.0F, -14.0F, 7.0F, 1.0F, 1.0F, 3.0F, new Dilation(0.0F))
			.uv(8, 0).cuboid(-8.0F, -15.0F, 8.0F, 1.0F, 1.0F, 4.0F, new Dilation(0.0F))
			.uv(16, 19).cuboid(-8.0F, -12.0F, 13.0F, 1.0F, 6.0F, 1.0F, new Dilation(0.0F))
			.uv(0, 7).cuboid(-8.0F, -9.0F, 12.0F, 1.0F, 2.0F, 1.0F, new Dilation(0.0F))
			.uv(0, 0).cuboid(-8.0F, -10.0F, 14.0F, 1.0F, 5.0F, 1.0F, new Dilation(0.0F)), ModelTransform.pivot(8.0F, 31.0F, -8.0F));
		return TexturedModelData.of(modelData, 32, 32);
	}


	@Override
	public void render(MatrixStack matrices, VertexConsumer vertexConsumer, int light, int overlay, float red, float green, float blue, float alpha) {
		this.cyrus_projectile.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
	}

}
