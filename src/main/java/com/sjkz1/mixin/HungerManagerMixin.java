package com.sjkz1.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.sjkz1.HappinessFood;
import net.minecraft.entity.player.HungerManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(HungerManager.class)
public abstract class HungerManagerMixin {

    @ModifyExpressionValue(method = "update", at = @At(value = "CONSTANT", args = "intValue=10", ordinal = 0))
    public int heal2(int original) {
        int modifiedFoodTickTimer = HappinessFood.happiness <= 0 ? original + Math.abs(HappinessFood.happiness) : HappinessFood.happiness / 2;
        return modifiedFoodTickTimer;
    }

    @ModifyExpressionValue(method = "update", at = @At(value = "CONSTANT", args = "intValue=80", ordinal = 0))
    public int heal(int original) {
        int modifiedFoodTickTimer = HappinessFood.happiness <= 0 ? original + Math.abs(HappinessFood.happiness) : original / HappinessFood.happiness;
        return modifiedFoodTickTimer;
    }

}
