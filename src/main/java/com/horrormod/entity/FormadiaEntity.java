/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  java.lang.Math
 *  java.lang.Object
 *  java.lang.String
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
 *  net.minecraft.world.entity.ai.goal.LookAtPlayerGoal
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
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class FormadiaEntity
extends Monster {
    private Phase phase = Phase.STALK;
    private int stalkTimer = 500 + new Random().nextInt(900);
    private int messageTimer = 300 + new Random().nextInt(300);
    private int vanishCooldown = 0;
    private static final String[] MSGS = new String[]{"\u00a75...", "\u00a75\u043d\u0435 \u0441\u043c\u043e\u0442\u0440\u0438", "\u00a75\u044f \u0437\u0434\u0435\u0441\u044c", "\u00a75Formadia \u0432\u0438\u0434\u0438\u0442"};

    public FormadiaEntity(EntityType<? extends Monster> type, Level level) {
        super(type, level);
        this.m_6593_((Component)Component.m_237113_((String)"\u00a75\u00a7lFormadia"));
        this.m_20340_(true);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Monster.m_33035_().m_22268_(Attributes.f_22276_, 700.0).m_22268_(Attributes.f_22279_, 0.3).m_22268_(Attributes.f_22281_, 22.0).m_22268_(Attributes.f_22277_, 96.0).m_22268_(Attributes.f_22278_, 2.0);
    }

    protected void m_8099_() {
        this.f_21345_.m_25352_(1, (Goal)new FloatGoal((Mob)this));
        this.f_21345_.m_25352_(2, (Goal)new MeleeAttackGoal((PathfinderMob)this, 0.9, false));
        this.f_21345_.m_25352_(3, (Goal)new WaterAvoidingRandomStrollGoal((PathfinderMob)this, 0.4));
        this.f_21345_.m_25352_(4, (Goal)new LookAtPlayerGoal((Mob)this, Player.class, 32.0f));
        this.f_21346_.m_25352_(1, (Goal)new NearestAttackableTargetGoal<Player>((Mob)this, Player.class, true){

            public boolean m_8036_() {
                return FormadiaEntity.this.phase == Phase.ATTACK && super.m_8036_();
            }
        });
    }

    public void m_8119_() {
        Player nearest;
        super.m_8119_();
        if (this.m_9236_().f_46443_) {
            return;
        }
        if (this.vanishCooldown > 0) {
            --this.vanishCooldown;
        }
        if (this.messageTimer > 0) {
            --this.messageTimer;
        }
        if ((nearest = this.m_9236_().m_45930_((Entity)this, 64.0)) == null) {
            return;
        }
        boolean looking = this.isLookingAt(nearest);
        if (this.phase == Phase.STALK) {
            if (looking) {
                this.m_21051_(Attributes.f_22279_).m_22100_(0.0);
                this.m_21573_().m_26573_();
                this.m_20256_(Vec3.f_82478_);
                this.vanishCooldown = 10;
            } else {
                this.m_21051_(Attributes.f_22279_).m_22100_(0.2);
                double dist = this.m_20270_((Entity)nearest);
                if (dist > 20.0) {
                    this.m_21573_().m_5624_((Entity)nearest, 0.5);
                } else if (dist < 8.0) {
                    double angle = Math.atan2((double)(this.m_20189_() - nearest.m_20189_()), (double)(this.m_20185_() - nearest.m_20185_()));
                    this.m_21573_().m_26519_(this.m_20185_() + Math.cos((double)angle) * 6.0, this.m_20186_(), this.m_20189_() + Math.sin((double)angle) * 6.0, 0.4);
                } else {
                    this.m_21573_().m_26573_();
                }
                --this.stalkTimer;
                if (this.stalkTimer <= 0) {
                    this.enterAttack(nearest);
                }
            }
            this.m_21563_().m_24960_((Entity)nearest, 30.0f, 30.0f);
            if (this.m_20270_((Entity)nearest) < 3.0f && nearest instanceof ServerPlayer) {
                ServerPlayer sp = (ServerPlayer)nearest;
                double tx = sp.m_20185_() + (this.f_19796_.m_188500_() - 0.5) * 80.0;
                double tz = sp.m_20189_() + (this.f_19796_.m_188500_() - 0.5) * 80.0;
                sp.m_6021_(tx, sp.m_20186_(), tz);
                sp.m_213846_((Component)Component.m_237113_((String)"\u00a75..."));
            }
        } else {
            this.m_21051_(Attributes.f_22279_).m_22100_(0.32);
        }
        if (this.messageTimer <= 0 && nearest instanceof ServerPlayer) {
            ServerPlayer sp = (ServerPlayer)nearest;
            sp.m_213846_((Component)Component.m_237113_((String)MSGS[this.f_19796_.m_188503_(MSGS.length)]));
            this.messageTimer = 700 + this.f_19796_.m_188503_(700);
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
        return (look.f_82479_ * dx + look.f_82480_ * dy + look.f_82481_ * dz) / len > 0.95;
    }

    private void enterAttack(Player p) {
        this.phase = Phase.ATTACK;
        this.m_6710_((LivingEntity)p);
        if (p instanceof ServerPlayer) {
            ServerPlayer sp = (ServerPlayer)p;
            sp.m_213846_((Component)Component.m_237113_((String)"\u00a75\u00a7lFormadia \u043f\u0440\u0438\u0448\u043b\u0430."));
        }
    }

    protected SoundEvent m_7515_() {
        return SoundEvents.f_11899_;
    }

    protected SoundEvent m_7975_(DamageSource ds) {
        return SoundEvents.f_11849_;
    }

    protected SoundEvent m_5592_() {
        return SoundEvents.f_11900_;
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
