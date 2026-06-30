/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  java.lang.Object
 *  net.minecraft.world.entity.EntityType
 *  net.minecraftforge.event.entity.EntityAttributeCreationEvent
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
import net.minecraft.world.entity.EntityType;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid="horrormod", bus=Mod.EventBusSubscriber.Bus.MOD)
public class CommonSetup {
    @SubscribeEvent
    public static void onEntityAttributes(EntityAttributeCreationEvent event) {
        event.put((EntityType)ModEntities.ZERO.get(), ZeroEntity.createAttributes().m_22265_());
        event.put((EntityType)ModEntities.PRO_IN_PVP.get(), ProInPvpEntity.createAttributes().m_22265_());
        event.put((EntityType)ModEntities.GLITCH.get(), GlitchEntity.createAttributes().m_22265_());
        event.put((EntityType)ModEntities.FORMADIA.get(), FormadiaEntity.createAttributes().m_22265_());
        event.put((EntityType)ModEntities.HORROR_VILLAGER.get(), HorrorVillagerEntity.createAttributes().m_22265_());
    }
}
