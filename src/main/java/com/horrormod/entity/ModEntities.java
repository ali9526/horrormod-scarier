/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  java.lang.Object
 *  java.lang.String
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.entity.EntityType
 *  net.minecraft.world.entity.EntityType$Builder
 *  net.minecraft.world.entity.MobCategory
 *  net.minecraftforge.eventbus.api.IEventBus
 *  net.minecraftforge.registries.DeferredRegister
 *  net.minecraftforge.registries.ForgeRegistries
 *  net.minecraftforge.registries.IForgeRegistry
 *  net.minecraftforge.registries.RegistryObject
 */
package com.horrormod.entity;

import com.horrormod.entity.FormadiaEntity;
import com.horrormod.entity.GlitchEntity;
import com.horrormod.entity.HorrorVillagerEntity;
import com.horrormod.entity.ProInPvpEntity;
import com.horrormod.entity.ZeroEntity;
import java.lang.Object;
import java.lang.String;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.RegistryObject;

public class ModEntities {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create((IForgeRegistry)ForgeRegistries.ENTITY_TYPES, (String)"horrormod");
    public static final RegistryObject<EntityType<ZeroEntity>> ZERO = ENTITY_TYPES.register("zero", () -> EntityType.Builder.m_20704_(ZeroEntity::new, (MobCategory)MobCategory.MONSTER).m_20699_(0.6f, 1.8f).m_20702_(128).m_20712_(new ResourceLocation("horrormod", "zero").toString()));
    public static final RegistryObject<EntityType<ProInPvpEntity>> PRO_IN_PVP = ENTITY_TYPES.register("pro_in_pvp", () -> EntityType.Builder.m_20704_(ProInPvpEntity::new, (MobCategory)MobCategory.MONSTER).m_20699_(0.6f, 1.8f).m_20702_(128).m_20712_(new ResourceLocation("horrormod", "pro_in_pvp").toString()));
    public static final RegistryObject<EntityType<GlitchEntity>> GLITCH = ENTITY_TYPES.register("glitch", () -> EntityType.Builder.m_20704_(GlitchEntity::new, (MobCategory)MobCategory.MONSTER).m_20699_(0.6f, 1.8f).m_20702_(128).m_20712_(new ResourceLocation("horrormod", "glitch").toString()));
    public static final RegistryObject<EntityType<FormadiaEntity>> FORMADIA = ENTITY_TYPES.register("formadia", () -> EntityType.Builder.m_20704_(FormadiaEntity::new, (MobCategory)MobCategory.MONSTER).m_20699_(0.6f, 2.4f).m_20702_(128).m_20712_(new ResourceLocation("horrormod", "formadia").toString()));
    public static final RegistryObject<EntityType<HorrorVillagerEntity>> HORROR_VILLAGER = ENTITY_TYPES.register("horror_villager", () -> EntityType.Builder.m_20704_(HorrorVillagerEntity::new, (MobCategory)MobCategory.MONSTER).m_20699_(0.6f, 1.95f).m_20702_(64).m_20712_(new ResourceLocation("horrormod", "horror_villager").toString()));

    public static void register(IEventBus bus) {
        ENTITY_TYPES.register(bus);
    }
}
