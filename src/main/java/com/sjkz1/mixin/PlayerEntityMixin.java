package com.sjkz1.mixin;

import com.sjkz1.HappinessFood;
import com.sjkz1.manager.HappinessManager;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Objects;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin extends LivingEntity {

    @Shadow
    public abstract ItemStack getEquippedStack(EquipmentSlot slot);

    @Unique
    protected HappinessManager happinessManager = new HappinessManager();

    protected PlayerEntityMixin(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(method = "tick", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/HungerManager;update(Lnet/minecraft/entity/player/PlayerEntity;)V"))
    public void tick(CallbackInfo ci) {
        this.happinessManager.update(PlayerEntity.class.cast(this));
        var buf = PacketByteBufs.create();
        buf.writeFloat(this.happinessManager.getHappiness());
        ServerPlayNetworking.send(ServerPlayerEntity.class.cast(this), HappinessFood.HAPPINESS, buf);
    }

    @Inject(method = "readCustomDataFromNbt", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/HungerManager;readNbt(Lnet/minecraft/nbt/NbtCompound;)V"))
    public void readCustomDataFromNbt(NbtCompound nbt, CallbackInfo ci) {
        this.happinessManager.readNbt(nbt);
    }

    @Inject(method = "writeCustomDataToNbt", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/HungerManager;writeNbt(Lnet/minecraft/nbt/NbtCompound;)V"))
    public void writeCustomDataToNbt(NbtCompound nbt, CallbackInfo ci) {
        this.happinessManager.writeNbt(nbt);
    }

    @Inject(method = "wakeUp()V", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/PlayerEntity;wakeUp(ZZ)V"))
    public void wakeUp(CallbackInfo ci) {
        if (happinessManager.isSad()) {
            this.happinessManager.increase(Math.abs(this.happinessManager.getHappiness()));
        } else {
            this.happinessManager.increase(5);
        }
    }

    @Inject(method = "eatFood", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/HungerManager;eat(Lnet/minecraft/item/Item;Lnet/minecraft/item/ItemStack;)V"))
    public void eatFood(World world, ItemStack stack, CallbackInfoReturnable<ItemStack> cir) {
        boolean isGood = HappinessFood.GOOD_FOODS_HAPPINESS_LEVEL.containsKey(stack.getItem());
        boolean isBad = HappinessFood.BAD_FOODS_HAPPINESS_LEVEL.containsKey(stack.getItem());
        if (isGood) {
            this.getHappinessManager().increase(Objects.requireNonNull(stack.getItem().getFoodComponent()).getSaturationModifier());
        } else if (isBad) {
            this.getHappinessManager().decrease(Objects.requireNonNull(stack.getItem().getFoodComponent()).getSaturationModifier());
        }
    }

    @Unique
    public HappinessManager getHappinessManager() {
        return happinessManager;
    }
}
