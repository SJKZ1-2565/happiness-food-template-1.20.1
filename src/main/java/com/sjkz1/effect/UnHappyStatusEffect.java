package com.sjkz1.effect;

import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;

public class UnHappyStatusEffect extends StatusEffect {
    public UnHappyStatusEffect() {
        super(StatusEffectCategory.HARMFUL, 0xff8789);
    }

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        return true;
    }

}
