/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  java.lang.Class
 *  java.lang.Math
 *  java.lang.Object
 *  java.lang.String
 *  java.util.List
 *  java.util.Random
 *  net.minecraft.core.BlockPos
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.network.chat.Component
 *  net.minecraft.network.protocol.Packet
 *  net.minecraft.network.protocol.game.ClientboundSetSubtitleTextPacket
 *  net.minecraft.network.protocol.game.ClientboundSetTitleTextPacket
 *  net.minecraft.network.protocol.game.ClientboundSetTitlesAnimationPacket
 *  net.minecraft.server.level.ServerPlayer
 *  net.minecraft.sounds.SoundEvent
 *  net.minecraft.sounds.SoundEvents
 *  net.minecraft.world.damagesource.DamageSource
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.EntityType
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.Mob
 *  net.minecraft.world.entity.PathfinderMob
 *  net.minecraft.world.entity.ai.attributes.AttributeSupplier$Builder
 *  net.minecraft.world.entity.ai.attributes.Attributes
 *  net.minecraft.world.entity.ai.goal.FloatGoal
 *  net.minecraft.world.entity.ai.goal.Goal
 *  net.minecraft.world.entity.ai.goal.LookAtPlayerGoal
 *  net.minecraft.world.entity.ai.goal.MeleeAttackGoal
 *  net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal
 *  net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal
 *  net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal
 *  net.minecraft.world.entity.monster.Monster
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.level.BlockGetter
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.block.Blocks
 *  net.minecraft.world.level.levelgen.Heightmap$Types
 *  net.minecraft.world.phys.Vec3
 */
package com.horrormod.entity;

import java.lang.Class;
import java.lang.Math;
import java.lang.Object;
import java.lang.String;
import java.util.List;
import java.util.Random;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientboundSetSubtitleTextPacket;
import net.minecraft.network.protocol.game.ClientboundSetTitleTextPacket;
import net.minecraft.network.protocol.game.ClientboundSetTitlesAnimationPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.phys.Vec3;

public class ProInPvpEntity
extends Monster {
    private Phase phase = Phase.STALK;
    private int stalkTimer = 250 + new Random().nextInt(500);
    private int repoTimer = 50;
    private int roseCooldown = 80;
    private int screamCooldown = 0;
    private int vanishCooldown = 0;
    private static final String SCREAMER = "\u00a74\u00a7l\u2593\u2593\u2593\u2593\u2593\u2593\u2593\u2593\u2593\u2593\u2593\u2593\u2593\u2593\u2593\n\u00a7c\u00a7l\u041e\u041d \u0412\u0418\u0414\u0418\u0422 \u0422\u0415\u0411\u042f";

    public ProInPvpEntity(EntityType<? extends Monster> type, Level level) {
        super(type, level);
        this.m_6593_((Component)Component.m_237113_((String)"\u00a76ProInPvp225"));
        this.m_20340_(true);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Monster.m_33035_().m_22268_(Attributes.f_22276_, 220.0).m_22268_(Attributes.f_22279_, 0.4).m_22268_(Attributes.f_22281_, 10.0).m_22268_(Attributes.f_22277_, 80.0);
    }

    protected void m_8099_() {
        this.f_21345_.m_25352_(1, (Goal)new FloatGoal((Mob)this));
        this.f_21345_.m_25352_(2, (Goal)new MeleeAttackGoal((PathfinderMob)this, 1.2, true));
        this.f_21345_.m_25352_(3, (Goal)new WaterAvoidingRandomStrollGoal((PathfinderMob)this, 0.5));
        this.f_21345_.m_25352_(4, (Goal)new LookAtPlayerGoal((Mob)this, Player.class, 32.0f));
        this.f_21346_.m_25352_(1, (Goal)new HurtByTargetGoal((PathfinderMob)this, new Class[0]));
        this.f_21346_.m_25352_(2, (Goal)new NearestAttackableTargetGoal<Player>((Mob)this, Player.class, true){

            public boolean m_8036_() {
                return ProInPvpEntity.this.phase == Phase.ATTACK && super.m_8036_();
            }
        });
    }

    public boolean m_6469_(DamageSource source, float amount) {
        boolean result = super.m_6469_(source, amount);
        if (result && this.phase == Phase.STALK) {
            this.enterAttack(this.m_9236_().m_45930_((Entity)this, 32.0));
        }
        return result;
    }

    public void m_8119_() {
        super.m_8119_();
        if (this.m_9236_().f_46443_) {
            return;
        }
        if (this.screamCooldown > 0) {
            --this.screamCooldown;
        }
        if (this.vanishCooldown > 0) {
            --this.vanishCooldown;
        }
        if (this.roseCooldown > 0) {
            --this.roseCooldown;
        }
        Player nearest = this.m_9236_().m_45930_((Entity)this, 64.0);
        if (this.phase == Phase.STALK && nearest != null) {
            double dist = this.m_20270_((Entity)nearest);
            --this.repoTimer;
            if (this.repoTimer <= 0) {
                if (dist < 12.0) {
                    double angle = Math.atan2((double)(this.m_20189_() - nearest.m_20189_()), (double)(this.m_20185_() - nearest.m_20185_()));
                    this.m_21573_().m_26519_(this.m_20185_() + Math.cos((double)angle) * 8.0, this.m_20186_(), this.m_20189_() + Math.sin((double)angle) * 8.0, 0.45);
                } else if (dist > 28.0) {
                    this.m_21573_().m_5624_((Entity)nearest, 0.45);
                } else {
                    this.m_21573_().m_26573_();
                }
                this.repoTimer = 40 + this.f_19796_.m_188503_(40);
            }
            this.m_21563_().m_24960_((Entity)nearest, 30.0f, 30.0f);
            if (this.vanishCooldown <= 0 && this.isLookingAt(nearest)) {
                this.vanishCooldown = 120;
                this.triggerScreamer();
                this.enterAttack(nearest);
                return;
            }
            --this.stalkTimer;
            if (this.stalkTimer <= 0) {
                this.enterAttack(nearest);
            }
        }
        if (this.roseCooldown <= 0) {
            this.spawnRoses();
            this.roseCooldown = 80 + this.f_19796_.m_188503_(80);
        }
    }

    private boolean isLookingAt(Player p) {
        double dz;
        double dy;
        double dx = this.m_20185_() - p.m_20185_();
        double len = Math.sqrt((double)(dx * dx + (dy = this.m_20186_() + (double)this.m_20206_() * 0.5 - p.m_20188_()) * dy + (dz = this.m_20189_() - p.m_20189_()) * dz));
        if (len < 0.001 || len > 26.0) {
            return false;
        }
        Vec3 look = p.m_20154_();
        return (look.f_82479_ * dx + look.f_82480_ * dy + look.f_82481_ * dz) / len > 0.96;
    }

    private void triggerScreamer() {
        List nearby = this.m_9236_().m_45976_(ServerPlayer.class, this.m_20191_().m_82400_(30.0));
        for (ServerPlayer sp : nearby) {
            sp.f_8906_.m_9829_((Packet)new ClientboundSetTitleTextPacket((Component)Component.m_237113_((String)"\u00a74\u00a7l\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac")));
            sp.f_8906_.m_9829_((Packet)new ClientboundSetSubtitleTextPacket((Component)Component.m_237113_((String)SCREAMER)));
            sp.f_8906_.m_9829_((Packet)new ClientboundSetTitlesAnimationPacket(3, 35, 8));
            sp.m_5496_(SoundEvents.f_12563_, 2.5f, 0.6f);
        }
    }

    private void enterAttack(Player p) {
        this.phase = Phase.ATTACK;
        this.m_6593_((Component)Component.m_237113_((String)"\u00a7c\u00a7l\u26a0 ProInPvp225 \u26a0"));
        this.m_21051_(Attributes.f_22279_).m_22100_(0.38);
        if (p != null) {
            this.m_6710_((LivingEntity)p);
        }
    }

    private void spawnRoses() {
        for (int i = 0; i < 2; ++i) {
            int x = this.m_146903_() + this.f_19796_.m_188503_(5) - 2;
            int z = this.m_146907_() + this.f_19796_.m_188503_(5) - 2;
            int y = this.m_9236_().m_6924_(Heightmap.Types.MOTION_BLOCKING, x, z);
            BlockPos pos = new BlockPos(x, y, z);
            if (!this.m_9236_().m_8055_(pos).m_60795_() || !this.m_9236_().m_8055_(pos.m_7495_()).m_60804_((BlockGetter)this.m_9236_(), pos.m_7495_())) continue;
            this.m_9236_().m_7731_(pos, Blocks.f_50112_.m_49966_(), 3);
        }
    }

    protected SoundEvent m_7515_() {
        return null;
    }

    protected SoundEvent m_7975_(DamageSource ds) {
        return SoundEvents.f_12323_;
    }

    protected SoundEvent m_5592_() {
        return SoundEvents.f_12556_;
    }

    public void m_7378_(CompoundTag tag) {
        super.m_7378_(tag);
        this.phase = tag.m_128471_("attacking") ? Phase.ATTACK : Phase.STALK;
        this.stalkTimer = tag.m_128451_("stalkTimer");
    }

    public void m_7380_(CompoundTag tag) {
        super.m_7380_(tag);
        tag.m_128379_("attacking", this.phase == Phase.ATTACK);
        tag.m_128405_("stalkTimer", this.stalkTimer);
    }

    private static enum Phase {
        STALK,
        ATTACK;

    }
}
