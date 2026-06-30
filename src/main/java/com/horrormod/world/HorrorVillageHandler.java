/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  java.lang.Math
 *  java.lang.Object
 *  java.lang.String
 *  java.util.Random
 *  net.minecraft.core.BlockPos
 *  net.minecraft.network.chat.Component
 *  net.minecraft.server.level.ServerLevel
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.EntityType
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.LevelAccessor
 *  net.minecraft.world.level.block.Blocks
 *  net.minecraft.world.level.block.entity.BlockEntity
 *  net.minecraft.world.level.block.entity.SignBlockEntity
 *  net.minecraft.world.level.block.entity.SignText
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.level.levelgen.Heightmap$Types
 *  net.minecraftforge.event.level.LevelEvent$Load
 *  net.minecraftforge.eventbus.api.SubscribeEvent
 *  net.minecraftforge.fml.common.Mod$EventBusSubscriber
 */
package com.horrormod.world;

import com.horrormod.HorrorMod;
import com.horrormod.entity.HorrorVillagerEntity;
import com.horrormod.entity.ModEntities;
import java.lang.Math;
import java.lang.Object;
import java.lang.String;
import java.util.Random;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.SignBlockEntity;
import net.minecraft.world.level.block.entity.SignText;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraftforge.event.level.LevelEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid="horrormod")
public class HorrorVillageHandler {
    private static boolean villageGenerated = false;

    @SubscribeEvent
    public static void onWorldLoad(LevelEvent.Load event) {
        LevelAccessor levelAccessor = event.getLevel();
        if (!(levelAccessor instanceof ServerLevel)) {
            return;
        }
        ServerLevel level = (ServerLevel)levelAccessor;
        if (!level.m_46472_().equals((Object)Level.f_46428_)) {
            return;
        }
        if (villageGenerated) {
            return;
        }
        BlockPos spawnPos = level.m_220360_();
        BlockPos villageCenter = spawnPos.m_7918_(300, 0, 100);
        int y = level.m_6924_(Heightmap.Types.MOTION_BLOCKING, villageCenter.m_123341_(), villageCenter.m_123343_());
        villageCenter = new BlockPos(villageCenter.m_123341_(), y, villageCenter.m_123343_());
        HorrorVillageHandler.generateHorrorVillage(level, villageCenter);
        villageGenerated = true;
    }

    public static void generateHorrorVillage(ServerLevel level, BlockPos center) {
        int[][] crossOffsets;
        int[][] houseOffsets;
        Random rng = new Random();
        for (int dx = -15; dx <= 15; ++dx) {
            for (int dz = -15; dz <= 15; ++dz) {
                BlockPos pos = center.m_7918_(dx, 0, dz);
                BlockState floor = Math.abs((int)(dx + dz)) % 2 == 0 ? Blocks.f_50330_.m_49966_() : Blocks.f_50144_.m_49966_();
                floor = Blocks.f_50330_.m_49966_();
                level.m_7731_(pos, floor, 3);
            }
        }
        for (int[] off : houseOffsets = new int[][]{{-8, -8}, {8, -8}, {-8, 8}, {8, 8}, {0, 0}}) {
            HorrorVillageHandler.generateHorrorHouse(level, center.m_7918_(off[0], 0, off[1]));
        }
        for (int[] off : crossOffsets = new int[][]{{-12, 0}, {12, 0}, {0, -12}, {0, 12}, {-6, 6}, {6, -6}, {-6, -6}}) {
            HorrorVillageHandler.generateStoneCross(level, center.m_7918_(off[0], 0, off[1]));
        }
        for (int i = 0; i < 6; ++i) {
            HorrorVillagerEntity villager = (HorrorVillagerEntity)((EntityType)ModEntities.HORROR_VILLAGER.get()).m_20615_((Level)level);
            if (villager == null) continue;
            double vx = center.m_123341_() + rng.nextInt(24) - 12;
            double vz = center.m_123343_() + rng.nextInt(24) - 12;
            double vy = center.m_123342_() + 4 + rng.nextInt(4);
            villager.m_7678_(vx, vy, vz, 0.0f, 0.0f);
            level.m_7967_((Entity)villager);
        }
        BlockPos signPos = center.m_122013_(14);
        level.m_7731_(signPos.m_7495_(), Blocks.f_50705_.m_49966_(), 3);
        level.m_7731_(signPos, Blocks.f_50095_.m_49966_(), 3);
        BlockEntity vx = level.m_7702_(signPos);
        if (vx instanceof SignBlockEntity) {
            SignBlockEntity sign = (SignBlockEntity)vx;
            SignText st = sign.m_277157_(true);
            st = st.m_276913_(0, (Component)Component.m_237113_((String)"\u00a74\u00a7l[ \u0414\u0415\u0420\u0415\u0412\u041d\u042f ]"));
            st = st.m_276913_(1, (Component)Component.m_237113_((String)"\u00a7c192.168.0.13:25565"));
            st = st.m_276913_(2, (Component)Component.m_237113_((String)"\u00a74\u043c\u044b \u0437\u043d\u0430\u0435\u043c \u0442\u0435\u0431\u044f"));
            st = st.m_276913_(3, (Component)Component.m_237113_((String)"\u00a78..."));
            sign.m_276956_(st, true);
        }
        HorrorMod.LOGGER.info("Horror village generated at {}", (Object)center);
    }

    private static void generateHorrorHouse(ServerLevel level, BlockPos base) {
        for (int dx = 0; dx <= 5; ++dx) {
            for (int dy = 0; dy <= 4; ++dy) {
                for (int dz = 0; dz <= 5; ++dz) {
                    boolean isWall;
                    boolean bl = isWall = dx == 0 || dx == 5 || dz == 0 || dz == 5 || dy == 4;
                    if (isWall) {
                        level.m_7731_(base.m_7918_(dx, dy, dz), Blocks.f_50330_.m_49966_(), 3);
                        continue;
                    }
                    if (dy != 0) continue;
                    level.m_7731_(base.m_7918_(dx, dy, dz), Blocks.f_50330_.m_49966_(), 3);
                }
            }
        }
        level.m_7731_(base.m_7918_(2, 1, 0), Blocks.f_50016_.m_49966_(), 3);
        level.m_7731_(base.m_7918_(2, 2, 0), Blocks.f_50016_.m_49966_(), 3);
        level.m_7731_(base.m_7918_(3, 1, 0), Blocks.f_50016_.m_49966_(), 3);
        level.m_7731_(base.m_7918_(3, 2, 0), Blocks.f_50016_.m_49966_(), 3);
        level.m_7731_(base.m_7918_(1, 2, 0), Blocks.f_50214_.m_49966_(), 3);
        level.m_7731_(base.m_7918_(4, 2, 0), Blocks.f_50214_.m_49966_(), 3);
        level.m_7731_(base.m_7918_(2, 1, 3), Blocks.f_50174_.m_49966_(), 3);
    }

    private static void generateStoneCross(ServerLevel level, BlockPos base) {
        for (int dy = 0; dy <= 4; ++dy) {
            level.m_7731_(base.m_6630_(dy), Blocks.f_50652_.m_49966_(), 3);
        }
        level.m_7731_(base.m_6630_(3).m_122024_(), Blocks.f_50652_.m_49966_(), 3);
        level.m_7731_(base.m_6630_(3).m_122025_(2), Blocks.f_50652_.m_49966_(), 3);
        level.m_7731_(base.m_6630_(3).m_122029_(), Blocks.f_50652_.m_49966_(), 3);
        level.m_7731_(base.m_6630_(3).m_122030_(2), Blocks.f_50652_.m_49966_(), 3);
        level.m_7731_(base.m_6630_(5), Blocks.f_50174_.m_49966_(), 3);
    }
}
