/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  java.lang.Math
 *  java.lang.Object
 *  java.lang.String
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.network.chat.Component
 *  net.minecraft.sounds.SoundEvent
 *  net.minecraft.sounds.SoundEvents
 *  net.minecraft.world.damagesource.DamageSource
 *  net.minecraft.world.entity.EntityType
 *  net.minecraft.world.entity.Mob
 *  net.minecraft.world.entity.MoverType
 *  net.minecraft.world.entity.ai.attributes.AttributeSupplier$Builder
 *  net.minecraft.world.entity.ai.attributes.Attributes
 *  net.minecraft.world.entity.ai.goal.FloatGoal
 *  net.minecraft.world.entity.ai.goal.Goal
 *  net.minecraft.world.entity.ai.goal.LookAtPlayerGoal
 *  net.minecraft.world.entity.monster.Monster
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.levelgen.Heightmap$Types
 */
package com.horrormod.entity;

import java.lang.Math;
import java.lang.Object;
import java.lang.String;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.levelgen.Heightmap;

public class HorrorVillagerEntity
extends Monster {
    private double floatY;
    private double floatOffset = 0.0;

    public HorrorVillagerEntity(EntityType<? extends Monster> type, Level level) {
        super(type, level);
        this.m_6593_((Component)Component.m_237113_((String)"\u00a78???"));
        this.m_20340_(false);
        this.f_19794_ = true;
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Monster.m_33035_().m_22268_(Attributes.f_22276_, 50.0).m_22268_(Attributes.f_22279_, 0.15).m_22268_(Attributes.f_22281_, 3.0).m_22268_(Attributes.f_22277_, 32.0);
    }

    protected void m_8099_() {
        this.f_21345_.m_25352_(1, (Goal)new LookAtPlayerGoal((Mob)this, Player.class, 16.0f));
        this.f_21345_.m_25352_(2, (Goal)new FloatGoal((Mob)this));
    }

    public void m_8119_() {
        super.m_8119_();
        this.floatOffset += 0.05;
        double targetY = this.m_20186_();
        int groundY = this.m_9236_().m_6924_(Heightmap.Types.MOTION_BLOCKING, this.m_146903_(), this.m_146907_());
        double desiredY = (double)groundY + 3.5 + Math.sin((double)this.floatOffset) * 0.5;
        double dy = desiredY - this.m_20186_();
        this.m_20334_(this.m_20184_().f_82479_ * 0.8, dy * 0.1, this.m_20184_().f_82481_ * 0.8);
        this.m_6478_(MoverType.SELF, this.m_20184_());
    }

    protected SoundEvent m_7515_() {
        return null;
    }

    protected SoundEvent m_7975_(DamageSource ds) {
        return SoundEvents.f_12506_;
    }

    protected SoundEvent m_5592_() {
        return SoundEvents.f_12505_;
    }

    public void m_7378_(CompoundTag tag) {
        super.m_7378_(tag);
    }

    public void m_7380_(CompoundTag tag) {
        super.m_7380_(tag);
    }
}
