/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  java.lang.Object
 *  java.lang.String
 *  net.minecraftforge.common.MinecraftForge
 *  net.minecraftforge.eventbus.api.IEventBus
 *  net.minecraftforge.fml.common.Mod
 *  net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext
 *  org.apache.logging.log4j.LogManager
 *  org.apache.logging.log4j.Logger
 */
package com.horrormod;

import com.horrormod.entity.ModEntities;
import com.horrormod.events.HorrorEvents;
import com.horrormod.world.HorrorVillageHandler;
import java.lang.Object;
import java.lang.String;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(value="horrormod")
public class HorrorMod {
    public static final String MOD_ID = "horrormod";
    public static final Logger LOGGER = LogManager.getLogger();

    public HorrorMod() {
        IEventBus modBus = FMLJavaModLoadingContext.get().getModEventBus();
        ModEntities.register(modBus);
        MinecraftForge.EVENT_BUS.register((Object)new HorrorEvents());
        MinecraftForge.EVENT_BUS.register((Object)new HorrorVillageHandler());
    }
}
