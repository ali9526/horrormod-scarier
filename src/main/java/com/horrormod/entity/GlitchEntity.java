/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  java.lang.Math
 *  java.lang.Object
 *  java.lang.String
 *  java.util.List
 *  java.util.Random
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.network.chat.Component
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
 *  net.minecraft.world.entity.ai.goal.MeleeAttackGoal
 *  net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal
 *  net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal
 *  net.minecraft.world.entity.monster.Monster
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.phys.Vec3
 */
package com.horrormod.entity;

import java.lang.Math;
import java.lang.Object;
import java.lang.String;
import java.util.List;
import java.util.Random;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
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
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class GlitchEntity
extends Monster {
    private Phase phase = Phase.STALK;
    private static boolean globalSpamDone = false;
    private static final String[] SPAM_MSGS = new String[]{"\u00a74&^&^&%&&&&*&", "\u00a7c\u00a7k&^&^&%&&&&*&", "\u00a74\u00a7l&^&^&%&&&&*& ERROR", "\u00a7c&^&^&%&&&&*& NULL", "\u00a74\u00a7k\u2591\u2591\u2591", "\u00a7c&^&^&%&&&&*&"};
    private boolean thisInstanceSpammed = false;
    private int stalkTimer = 150 + new Random().nextInt(300);
    private int teleportTimer = 30;
    private int vanishCooldown = 0;

    public GlitchEntity(EntityType<? extends Monster> type, Level level) {
        super(type, level);
        this.m_6593_((Component)Component.m_237113_((String)"\u00a74\u00a7k&^&^&%&&&&*&"));
        this.m_20340_(true);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Monster.m_33035_().m_22268_(Attributes.f_22276_, 150.0).m_22268_(Attributes.f_22279_, 0.6).m_22268_(Attributes.f_22281_, 16.0).m_22268_(Attributes.f_22277_, 96.0).m_22268_(Attributes.f_22278_, 1.0);
    }

    protected void m_8099_() {
        this.f_21345_.m_25352_(1, (Goal)new FloatGoal((Mob)this));
        this.f_21345_.m_25352_(2, (Goal)new MeleeAttackGoal((PathfinderMob)this, 1.5, true));
        this.f_21345_.m_25352_(3, (Goal)new WaterAvoidingRandomStrollGoal((PathfinderMob)this, 1.0));
        this.f_21346_.m_25352_(1, (Goal)new NearestAttackableTargetGoal<Player>((Mob)this, Player.class, true){

            public boolean m_8036_() {
                return GlitchEntity.this.phase == Phase.ATTACK && super.m_8036_();
            }
        });
    }

    public void m_8119_() {
        super.m_8119_();
        if (this.m_9236_().f_46443_) {
            return;
        }
        if (this.vanishCooldown > 0) {
            --this.vanishCooldown;
        }
        Player nearest = this.m_9236_().m_45930_((Entity)this, 64.0);
        if (this.phase == Phase.STALK) {
            if (nearest != null) {
                this.m_21573_().m_26573_();
                this.m_21563_().m_24960_((Entity)nearest, 30.0f, 30.0f);
                if (this.vanishCooldown <= 0 && this.isLookingAt(nearest)) {
                    this.vanishCooldown = 80;
                    if (this.f_19796_.m_188499_()) {
                        double angle = Math.toRadians((double)(nearest.m_146908_() + 180.0f + (float)this.f_19796_.m_188503_(120) - 60.0f));
                        double dist = 18 + this.f_19796_.m_188503_(16);
                        this.m_6021_(nearest.m_20185_() + Math.sin((double)angle) * dist, nearest.m_20186_(), nearest.m_20189_() - Math.cos((double)angle) * dist);
                        this.m_9236_().m_46796_(2003, this.m_20183_(), 0);
                        this.stalkTimer = 200 + this.f_19796_.m_188503_(400);
                    } else {
                        this.enterAttack(nearest);
                    }
                    return;
                }
                --this.stalkTimer;
                if (this.stalkTimer <= 0) {
                    this.enterAttack(nearest);
                }
            }
            --this.teleportTimer;
            if (this.teleportTimer <= 0) {
                double tx = this.m_20185_() + (this.f_19796_.m_188500_() - 0.5) * 12.0;
                double tz = this.m_20189_() + (this.f_19796_.m_188500_() - 0.5) * 12.0;
                this.m_6021_(tx, this.m_20186_(), tz);
                this.teleportTimer = 50 + this.f_19796_.m_188503_(40);
            }
        } else {
            --this.teleportTimer;
            if (this.teleportTimer <= 0) {
                double tx = this.m_20185_() + (this.f_19796_.m_188500_() - 0.5) * 20.0;
                double tz = this.m_20189_() + (this.f_19796_.m_188500_() - 0.5) * 20.0;
                this.m_6021_(tx, this.m_20186_(), tz);
                this.teleportTimer = 15 + this.f_19796_.m_188503_(15);
            }
        }
        if (this.f_19796_.m_188503_(40) == 0) {
            this.m_6593_((Component)Component.m_237113_((String)(this.f_19796_.m_188499_() ? "\u00a74\u00a7k&^&^&%&&&&*&" : "\u00a7c&^&^&%&&&&*&")));
        }
        if (!this.thisInstanceSpammed && !globalSpamDone && this.f_19797_ == 80) {
            this.doOneTimeSpam();
        }
    }

    private boolean isLookingAt(Player p) {
        double dz;
        double dy;
        double dx = this.m_20185_() - p.m_20185_();
        double len = Math.sqrt((double)(dx * dx + (dy = this.m_20186_() + (double)this.m_20206_() * 0.5 - p.m_20188_()) * dy + (dz = this.m_20189_() - p.m_20189_()) * dz));
        if (len < 0.001 || len > 28.0) {
            return false;
        }
        Vec3 look = p.m_20154_();
        return (look.f_82479_ * dx + look.f_82480_ * dy + look.f_82481_ * dz) / len > 0.96;
    }

    private void enterAttack(Player p) {
        this.phase = Phase.ATTACK;
        this.m_6710_((LivingEntity)p);
    }

    private void doOneTimeSpam() {
        List players = this.m_9236_().m_45976_(ServerPlayer.class, this.m_20191_().m_82400_(200.0));
        if (players.isEmpty()) {
            return;
        }
        int count = 4 + this.f_19796_.m_188503_(3);
        for (ServerPlayer sp : players) {
            for (int i = 0; i < count; ++i) {
                sp.m_213846_((Component)Component.m_237113_((String)SPAM_MSGS[this.f_19796_.m_188503_(SPAM_MSGS.length)]));
            }
            sp.m_5496_(SoundEvents.f_12563_, 1.5f, 0.6f);
        }
        this.thisInstanceSpammed = true;
        globalSpamDone = true;
    }

    public static void resetForNewSession() {
        globalSpamDone = false;
    }

    protected SoundEvent m_7515_() {
        return (SoundEvent)SoundEvents.f_11689_.m_203334_();
    }

    protected SoundEvent m_7975_(DamageSource ds) {
        return SoundEvents.f_12323_;
    }

    protected SoundEvent m_5592_() {
        return SoundEvents.f_12556_;
    }

    public void m_7378_(CompoundTag tag) {
        super.m_7378_(tag);
        this.thisInstanceSpammed = tag.m_128471_("spammed");
        this.phase = tag.m_128471_("attacking") ? Phase.ATTACK : Phase.STALK;
        this.stalkTimer = tag.m_128451_("stalkTimer");
    }

    public void m_7380_(CompoundTag tag) {
        super.m_7380_(tag);
        tag.m_128379_("spammed", this.thisInstanceSpammed);
        tag.m_128379_("attacking", this.phase == Phase.ATTACK);
        tag.m_128405_("stalkTimer", this.stalkTimer);
    }

    private static enum Phase {
        STALK,
        ATTACK;

    }
}
