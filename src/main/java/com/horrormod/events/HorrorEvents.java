/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  java.lang.Integer
 *  java.lang.Long
 *  java.lang.Math
 *  java.lang.Object
 *  java.lang.String
 *  java.util.HashMap
 *  java.util.Map
 *  java.util.Random
 *  java.util.UUID
 *  net.minecraft.core.BlockPos
 *  net.minecraft.network.chat.Component
 *  net.minecraft.server.level.ServerLevel
 *  net.minecraft.server.level.ServerPlayer
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.EntityType
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.level.BlockGetter
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.block.Blocks
 *  net.minecraft.world.level.block.entity.BlockEntity
 *  net.minecraft.world.level.block.entity.SignBlockEntity
 *  net.minecraft.world.level.block.entity.SignText
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.level.levelgen.Heightmap$Types
 *  net.minecraftforge.event.TickEvent$Phase
 *  net.minecraftforge.event.TickEvent$ServerTickEvent
 *  net.minecraftforge.event.entity.player.PlayerEvent$PlayerLoggedInEvent
 *  net.minecraftforge.eventbus.api.SubscribeEvent
 */
package com.horrormod.events;

import com.horrormod.entity.FormadiaEntity;
import com.horrormod.entity.GlitchEntity;
import com.horrormod.entity.ModEntities;
import com.horrormod.entity.ProInPvpEntity;
import com.horrormod.entity.ZeroEntity;
import java.lang.Integer;
import java.lang.Long;
import java.lang.Math;
import java.lang.Object;
import java.lang.String;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.UUID;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.SignBlockEntity;
import net.minecraft.world.level.block.entity.SignText;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class HorrorEvents {
    private static final Random RNG = new Random();
    private static final Map<UUID, Long> joinDay = new HashMap();
    private static final String[] FAKE_IPS = new String[]{"192.168.0.13:25565", "10.0.0.1:19132", "172.16.0.7:25565", "192.168.1.100:25565"};
    private static final Map<UUID, Map<String, Integer>> eventTimers = new HashMap();
    private static final Map<UUID, Integer> globalCooldown = new HashMap();
    private static final int GLOBAL_COOLDOWN_TICKS = 20;
    private static final Object[][] EVENT_DEFS = new Object[][]{{"spawnZero", 400, 1100}, {"spawnProInPvp", 300, 900}, {"spawnGlitch", 500, 1400}, {"spawnFormadia", 700, 2000}, {"bedrockSky", 150, 500}, {"disappearTree", 250, 600}, {"fakeSign", 900, 1800}, {"ambientSound", 80, 300}, {"chatGlitch", 300, 900}, {"redTorches", 200, 600}, {"fallingBedrock", 500, 1500}, {"entitySwap", 700, 1500}};
    private static final String[] RANDOM_CHAT = new String[]{"\u00a74...", "\u00a7c\u00a7k\u2588", "\u00a74\u0442\u044b \u0432\u0438\u0434\u0435\u043b \u044d\u0442\u043e?", "\u00a7c\u00a7k\u2591\u2591\u2591", "\u00a74\u0437\u0430 \u0442\u043e\u0431\u043e\u0439", "\u00a7c\u0437\u0435\u0440\u043e", "\u00a74\u00a7k&", "\u00a7c!", "\u00a74\u0444\u043e\u0440\u043c\u0430\u0434\u0438\u044f \u0441\u043c\u043e\u0442\u0440\u0438\u0442", "\u00a7c\u00a7k\u2593\u2593\u2593"};

    @SubscribeEvent
    public void onPlayerJoin(PlayerEvent.PlayerLoggedInEvent event) {
        Player player = event.getEntity();
        if (!(player instanceof ServerPlayer)) {
            return;
        }
        ServerPlayer player2 = (ServerPlayer)player;
        long day = player2.m_9236_().m_46468_() / 24000L;
        joinDay.putIfAbsent((Object)player2.m_20148_(), (Object)day);
        this.initTimers(player2.m_20148_());
        this.spawnSignsNear(player2);
    }

    private void initTimers(UUID uuid) {
        HashMap timers = new HashMap();
        for (Object[] def : EVENT_DEFS) {
            int min = (Integer)def[1];
            int extra = (Integer)def[2];
            int startOffset = 60 + RNG.nextInt(200);
            timers.put((Object)((String)def[0]), (Object)(min + startOffset + RNG.nextInt(extra)));
        }
        eventTimers.put((Object)uuid, (Object)timers);
        globalCooldown.put((Object)uuid, (Object)60);
    }

    @SubscribeEvent
    public void onServerTick(TickEvent.ServerTickEvent event) {
        if (event.phase != TickEvent.Phase.END) {
            return;
        }
        for (ServerLevel level : event.getServer().m_129785_()) {
            for (ServerPlayer player : level.m_6907_()) {
                UUID uuid = player.m_20148_();
                if (!eventTimers.containsKey((Object)uuid)) {
                    this.initTimers(uuid);
                }
                long curDay = level.m_46468_() / 24000L;
                long start = (Long)joinDay.getOrDefault((Object)uuid, (Object)curDay);
                long days = curDay - start;
                int cooldown = (Integer)globalCooldown.getOrDefault((Object)uuid, (Object)0) - 1;
                globalCooldown.put((Object)uuid, (Object)Math.max((int)0, (int)cooldown));
                if (cooldown > 0) continue;
                Map timers = (Map)eventTimers.get((Object)uuid);
                boolean firedThisTick = false;
                for (Object[] def : EVENT_DEFS) {
                    String name = (String)def[0];
                    int t = (Integer)timers.getOrDefault((Object)name, (Object)0) - 1;
                    if (t <= 0) {
                        if (!firedThisTick) {
                            this.fireEvent(name, player, level, days);
                            firedThisTick = true;
                            globalCooldown.put((Object)uuid, (Object)60);
                        }
                        int min = (Integer)def[1];
                        int extra = (Integer)def[2];
                        timers.put((Object)name, (Object)(min + RNG.nextInt(extra)));
                        continue;
                    }
                    timers.put((Object)name, (Object)t);
                }
                if (days < 1L || firedThisTick || RNG.nextInt(60) != 0) continue;
                this.worldDecay(player, level);
            }
        }
    }

    /*
     * Exception decompiling
     */
    private void fireEvent(String name, ServerPlayer player, ServerLevel level, long days) {
        /*
         * This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
         * 
         * org.benf.cfr.reader.bytecode.analysis.opgraph.op4rewriters.SwitchStringRewriter$TooOptimisticMatchException
         *     at java.lang.Throwable$FakeClass.fakeMethod(Throwable.java:1580986)
         *     at java.lang.Throwable$FakeClass.fakeMethod(Throwable.java:4198595)
         *     at java.lang.Throwable$FakeClass.fakeMethod(Throwable.java:4200357)
         *     at java.lang.Throwable$FakeClass.fakeMethod(Throwable.java:3554934)
         *     at java.lang.Throwable$FakeClass.fakeMethod(Throwable.java:3555056)
         *     at java.lang.Throwable$FakeClass.fakeMethod(Throwable.java:907620)
         *     at java.lang.Throwable$FakeClass.fakeMethod(Throwable.java:4176904)
         *     at java.lang.Throwable$FakeClass.fakeMethod(Throwable.java:524672)
         *     at java.lang.Throwable$FakeClass.fakeMethod(Throwable.java:3959296)
         */
        throw new IllegalStateException("Decompilation failed");
    }

    private void spawnZero(ServerPlayer p, ServerLevel l) {
        ZeroEntity e = (ZeroEntity)((EntityType)ModEntities.ZERO.get()).m_20615_((Level)l);
        if (e == null) {
            return;
        }
        double angle = RNG.nextDouble() * Math.PI * 2.0;
        double dist = 10 + RNG.nextInt(20);
        e.m_7678_(p.m_20185_() + Math.cos((double)angle) * dist, p.m_20186_(), p.m_20189_() + Math.sin((double)angle) * dist, 0.0f, 0.0f);
        l.m_7967_((Entity)e);
        if (RNG.nextBoolean()) {
            p.m_213846_((Component)Component.m_237113_((String)"\u00a74..."));
        }
    }

    private void spawnProInPvp(ServerPlayer p, ServerLevel l) {
        ProInPvpEntity e = (ProInPvpEntity)((EntityType)ModEntities.PRO_IN_PVP.get()).m_20615_((Level)l);
        if (e == null) {
            return;
        }
        e.m_7678_(p.m_20185_() + (double)RNG.nextInt(30) - 15.0, p.m_20186_(), p.m_20189_() + (double)RNG.nextInt(30) - 15.0, 0.0f, 0.0f);
        l.m_7967_((Entity)e);
    }

    private void spawnGlitch(ServerPlayer p, ServerLevel l) {
        double angle = RNG.nextDouble() * Math.PI * 2.0;
        double dist = 5 + RNG.nextInt(30);
        GlitchEntity e = (GlitchEntity)((EntityType)ModEntities.GLITCH.get()).m_20615_((Level)l);
        if (e == null) {
            return;
        }
        e.m_7678_(p.m_20185_() + Math.cos((double)angle) * dist, p.m_20186_(), p.m_20189_() + Math.sin((double)angle) * dist, 0.0f, 0.0f);
        l.m_7967_((Entity)e);
    }

    private void spawnFormadia(ServerPlayer p, ServerLevel l) {
        double angle = RNG.nextDouble() * Math.PI * 2.0;
        double dist = 15 + RNG.nextInt(30);
        FormadiaEntity e = (FormadiaEntity)((EntityType)ModEntities.FORMADIA.get()).m_20615_((Level)l);
        if (e == null) {
            return;
        }
        e.m_7678_(p.m_20185_() + Math.cos((double)angle) * dist, p.m_20186_(), p.m_20189_() + Math.sin((double)angle) * dist, 0.0f, 0.0f);
        l.m_7967_((Entity)e);
        if (RNG.nextInt(3) == 0) {
            p.m_213846_((Component)Component.m_237113_((String)"\u00a75..."));
        }
    }

    private void spawnBedrockSky(ServerPlayer p, ServerLevel l) {
        int x = p.m_146903_() + RNG.nextInt(80) - 40;
        int z = p.m_146907_() + RNG.nextInt(80) - 40;
        int y = 190 + RNG.nextInt(60);
        l.m_7731_(new BlockPos(x, y, z), Blocks.f_50752_.m_49966_(), 3);
    }

    private void disappearTree(ServerPlayer p, ServerLevel l) {
        int x = p.m_146903_() + RNG.nextInt(80) - 40;
        int z = p.m_146907_() + RNG.nextInt(80) - 40;
        for (int dy = 0; dy < 20; ++dy) {
            int y = l.m_6924_(Heightmap.Types.MOTION_BLOCKING, x, z) + dy;
            BlockState bs = l.m_8055_(new BlockPos(x, y, z));
            if (!bs.m_60713_(Blocks.f_49999_) && !bs.m_60713_(Blocks.f_50050_) && !bs.m_60713_(Blocks.f_50000_) && !bs.m_60713_(Blocks.f_50051_) && !bs.m_60713_(Blocks.f_50001_) && !bs.m_60713_(Blocks.f_50052_) && !bs.m_60713_(Blocks.f_50004_) && !bs.m_60713_(Blocks.f_50002_)) continue;
            l.m_7731_(new BlockPos(x, y, z), Blocks.f_50016_.m_49966_(), 2);
        }
    }

    private void spawnFakeSign(ServerPlayer p, ServerLevel l) {
        String ip = FAKE_IPS[RNG.nextInt(FAKE_IPS.length)];
        int dist = 5 + RNG.nextInt(40);
        double angle = RNG.nextDouble() * Math.PI * 2.0;
        int x = (int)(p.m_20185_() + Math.cos((double)angle) * (double)dist);
        int z = (int)(p.m_20189_() + Math.sin((double)angle) * (double)dist);
        int y = l.m_6924_(Heightmap.Types.MOTION_BLOCKING, x, z);
        BlockPos pos = new BlockPos(x, y, z);
        l.m_7731_(pos.m_7495_(), Blocks.f_50705_.m_49966_(), 3);
        l.m_7731_(pos, Blocks.f_50095_.m_49966_(), 3);
        BlockEntity blockEntity = l.m_7702_(pos);
        if (blockEntity instanceof SignBlockEntity) {
            SignBlockEntity sign = (SignBlockEntity)blockEntity;
            String[] lines = new String[]{"\u00a74" + ip, "\u00a7c\u041c\u042b \u0417\u041d\u0410\u0415\u041c", "\u00a74\u0422\u042b \u041d\u0410\u0428\u0401\u043b", "\u00a78zero..."};
            SignText st = sign.m_277157_(true);
            for (int i = 0; i < 4; ++i) {
                st = st.m_276913_(i, (Component)Component.m_237113_((String)lines[i]));
            }
            sign.m_276956_(st, true);
        }
    }

    private void sendRandomGlitch(ServerPlayer p) {
        int count = 1 + RNG.nextInt(2);
        for (int i = 0; i < count; ++i) {
            p.m_213846_((Component)Component.m_237113_((String)RANDOM_CHAT[RNG.nextInt(RANDOM_CHAT.length)]));
        }
    }

    private void spawnRedTorches(ServerPlayer p, ServerLevel l) {
        int count = 5 + RNG.nextInt(6);
        for (int i = 0; i < count; ++i) {
            int z;
            int y;
            int x = p.m_146903_() + RNG.nextInt(50) - 25;
            BlockPos pos = new BlockPos(x, y = l.m_6924_(Heightmap.Types.MOTION_BLOCKING, x, z = p.m_146907_() + RNG.nextInt(50) - 25), z);
            if (!l.m_8055_(pos).m_60795_() || !l.m_8055_(pos.m_7495_()).m_60804_((BlockGetter)l, pos.m_7495_())) continue;
            l.m_7731_(pos, Blocks.f_50174_.m_49966_(), 3);
        }
    }

    private void fallingBedrock(ServerPlayer p, ServerLevel l) {
        int x = p.m_146903_() + RNG.nextInt(16) - 8;
        int z = p.m_146907_() + RNG.nextInt(16) - 8;
        int startY = 255 - RNG.nextInt(20);
        int height = 5 + RNG.nextInt(5);
        for (int dy = 0; dy < height; ++dy) {
            l.m_7731_(new BlockPos(x, startY - dy, z), Blocks.f_50752_.m_49966_(), 3);
        }
    }

    private void randomEntitySwap(ServerPlayer p, ServerLevel l) {
        switch (RNG.nextInt(4)) {
            case 0: {
                this.spawnZero(p, l);
                break;
            }
            case 1: {
                this.spawnGlitch(p, l);
                break;
            }
            case 2: {
                this.spawnFormadia(p, l);
                break;
            }
            case 3: {
                this.spawnProInPvp(p, l);
            }
        }
    }

    private void worldDecay(ServerPlayer p, ServerLevel l) {
        for (int i = 0; i < 6; ++i) {
            int z;
            int y;
            int x = p.m_146903_() + RNG.nextInt(100) - 50;
            BlockState bs = l.m_8055_(new BlockPos(x, y = l.m_6924_(Heightmap.Types.MOTION_BLOCKING, x, z = p.m_146907_() + RNG.nextInt(100) - 50) - 1, z));
            if (!bs.m_60713_(Blocks.f_50440_) && !bs.m_60713_(Blocks.f_49999_) && !bs.m_60713_(Blocks.f_50050_) && !bs.m_60713_(Blocks.f_50493_)) continue;
            l.m_7731_(new BlockPos(x, y, z), Blocks.f_50016_.m_49966_(), 2);
        }
    }

    private void spawnSignsNear(ServerPlayer p) {
        Level level = p.m_9236_();
        if (!(level instanceof ServerLevel)) {
            return;
        }
        ServerLevel l = (ServerLevel)level;
        String ip = FAKE_IPS[RNG.nextInt(FAKE_IPS.length)];
        int x = p.m_146903_() + RNG.nextInt(20) - 10;
        int z = p.m_146907_() + RNG.nextInt(20) - 10;
        int y = l.m_6924_(Heightmap.Types.MOTION_BLOCKING, x, z);
        BlockPos pos = new BlockPos(x, y, z);
        l.m_7731_(pos.m_7495_(), Blocks.f_50705_.m_49966_(), 3);
        l.m_7731_(pos, Blocks.f_50095_.m_49966_(), 3);
        BlockEntity blockEntity = l.m_7702_(pos);
        if (blockEntity instanceof SignBlockEntity) {
            SignBlockEntity sign = (SignBlockEntity)blockEntity;
            SignText st = sign.m_277157_(true);
            st = st.m_276913_(0, (Component)Component.m_237113_((String)("\u00a74" + ip)));
            st = st.m_276913_(1, (Component)Component.m_237113_((String)"\u00a7c\u00a7l\u041c\u042b \u0417\u041d\u0410\u0415\u041c"));
            st = st.m_276913_(2, (Component)Component.m_237113_((String)"\u00a74\u0422\u042b \u041d\u0410\u0428\u0401\u043b \u041d\u0410\u0421"));
            st = st.m_276913_(3, (Component)Component.m_237113_((String)"\u00a78zero..."));
            sign.m_276956_(st, true);
        }
    }
}
