package com.sjkz1.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.sjkz1.HappinessFood;
import net.minecraft.entity.player.HungerManager;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(HungerManager.class)
public abstract class HungerManagerMixin {

    @Shadow
    private float saturationLevel;

    @Shadow
    public abstract void update(PlayerEntity player);

    //TODO modify healing value
//    @Redirect(method = "update", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/PlayerEntity;heal(F)V", ordinal = 0))
//    public void update(PlayerEntity instance, float v) {
//        float f = Math.min(this.saturationLevel, 6.0F);
//        float originalHeal = f / 6.0f;
//        float healValue = originalHeal * ((float) HappinessFood.happiness / 10);
//        instance.heal(Math.abs(healValue));
//    }

    @ModifyExpressionValue(method = "update", at = @At(value = "CONSTANT", args = "intValue=10", ordinal = 0))
    public int heal2(int original) {
        int modifiedFoodTickTimer = HappinessFood.happiness <= 0 ? original + Math.abs(HappinessFood.happiness) : original - HappinessFood.happiness;
        HappinessFood.LOGGER.info("modifiedFoodTickTimer {}", modifiedFoodTickTimer);
        return modifiedFoodTickTimer;
    }
//
    @ModifyExpressionValue(method = "update", at = @At(value = "CONSTANT", args = "intValue=80", ordinal = 0))
    public int heal(int original) {
        int modifiedFoodTickTimer = HappinessFood.happiness <= 0 ? original + Math.abs(HappinessFood.happiness) : original - HappinessFood.happiness;
        HappinessFood.LOGGER.info("modifiedFoodTickTimer {}", modifiedFoodTickTimer);
        return modifiedFoodTickTimer;
    }

//    @Redirect(method = "update", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/PlayerEntity;heal(F)V", ordinal = 1))
//    public void update2(PlayerEntity instance, float v) {
//        instance.heal((float) 1 * Math.abs(HappinessFood.happiness / 10));
//    }
}
