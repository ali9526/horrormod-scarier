/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  java.lang.Object
 *  net.minecraft.client.model.HumanoidModel
 *  net.minecraft.client.model.geom.ModelLayers
 *  net.minecraft.client.renderer.entity.HumanoidMobRenderer
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.entity.EntityType
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.client.event.EntityRenderersEvent$RegisterRenderers
 *  net.minecraftforge.eventbus.api.SubscribeEvent
 *  net.minecraftforge.fml.common.Mod$EventBusSubscriber
 *  net.minecraftforge.fml.common.Mod$EventBusSubscriber$Bus
 */
package com.horrormod;

import com.horrormod.entity.FormadiaEntity;
import com.horrormod.entity.GlitchEntity;
import com.horrormod.entity.HorrorVillagerEntity;
import com.horrormod.entity.ModEntities;
import com.horrormod.entity.ProInPvpEntity;
import com.horrormod.entity.ZeroEntity;
import java.lang.Object;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.HumanoidMobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid="horrormod", bus=Mod.EventBusSubscriber.Bus.MOD, value={Dist.CLIENT})
public class ClientSetup {
    @SubscribeEvent
    public static void onRegisterRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer((EntityType)ModEntities.ZERO.get(), ctx -> new HumanoidMobRenderer<ZeroEntity, HumanoidModel<ZeroEntity>>(ctx, new HumanoidModel(ctx.m_174023_(ModelLayers.f_171162_)), 0.5f){

            public ResourceLocation getTextureLocation(ZeroEntity e) {
                return new ResourceLocation("horrormod", "textures/entity/zero.png");
            }
        });
        event.registerEntityRenderer((EntityType)ModEntities.PRO_IN_PVP.get(), ctx -> new HumanoidMobRenderer<ProInPvpEntity, HumanoidModel<ProInPvpEntity>>(ctx, new HumanoidModel(ctx.m_174023_(ModelLayers.f_171162_)), 0.5f){

            public ResourceLocation getTextureLocation(ProInPvpEntity e) {
                return new ResourceLocation("horrormod", "textures/entity/pro_in_pvp.png");
            }
        });
        event.registerEntityRenderer((EntityType)ModEntities.GLITCH.get(), ctx -> new HumanoidMobRenderer<GlitchEntity, HumanoidModel<GlitchEntity>>(ctx, new HumanoidModel(ctx.m_174023_(ModelLayers.f_171162_)), 0.5f){

            public ResourceLocation getTextureLocation(GlitchEntity e) {
                return new ResourceLocation("horrormod", "textures/entity/glitch.png");
            }
        });
        event.registerEntityRenderer((EntityType)ModEntities.FORMADIA.get(), ctx -> new HumanoidMobRenderer<FormadiaEntity, HumanoidModel<FormadiaEntity>>(ctx, new HumanoidModel(ctx.m_174023_(ModelLayers.f_171162_)), 0.5f){

            public ResourceLocation getTextureLocation(FormadiaEntity e) {
                return new ResourceLocation("horrormod", "textures/entity/formadia.png");
            }
        });
        event.registerEntityRenderer((EntityType)ModEntities.HORROR_VILLAGER.get(), ctx -> new HumanoidMobRenderer<HorrorVillagerEntity, HumanoidModel<HorrorVillagerEntity>>(ctx, new HumanoidModel(ctx.m_174023_(ModelLayers.f_171162_)), 0.5f){

            public ResourceLocation getTextureLocation(HorrorVillagerEntity e) {
                return new ResourceLocation("horrormod", "textures/entity/horror_villager.png");
            }
        });
    }
}
