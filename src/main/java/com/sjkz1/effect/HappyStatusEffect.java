package com.sjkz1.effect;

import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;

public class HappyStatusEffect extends StatusEffect {
    public HappyStatusEffect() {
        super(StatusEffectCategory.BENEFICIAL, 0Xffdf78);
    }

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        return true;
    }

}
