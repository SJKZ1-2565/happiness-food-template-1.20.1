package com.sjkz1.mixin.client;

import com.sjkz1.HappinessFood;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.AbstractInventoryScreen;
import net.minecraft.client.gui.screen.ingame.InventoryScreen;
import net.minecraft.client.gui.screen.recipebook.RecipeBookProvider;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.PlayerScreenHandler;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(InventoryScreen.class)
public abstract class PlayerInventoryMixin extends AbstractInventoryScreen<PlayerScreenHandler> implements RecipeBookProvider {


    public PlayerInventoryMixin(PlayerScreenHandler screenHandler, PlayerInventory playerInventory, Text text) {
        super(screenHandler, playerInventory, text);
    }

    private static final Identifier NEW_BG = Identifier.of(HappinessFood.MOD_ID, "texture/gui/inventory.png");


    @Inject(method = "drawBackground", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screen/ingame/InventoryScreen;drawEntity(Lnet/minecraft/client/gui/DrawContext;IIIFFLnet/minecraft/entity/LivingEntity;)V"))
    public void drawBackground(DrawContext context, float delta, int mouseX, int mouseY, CallbackInfo ci) {
        int i = this.x;
        int j = this.y;
        if (HappinessFood.happiness > 0) {
            context.drawTexture(NEW_BG, i + 77, j + 7 + 50 - HappinessFood.happiness, 192, 123 - HappinessFood.happiness, 14, HappinessFood.happiness + 1);
        } else {
            context.drawTexture(NEW_BG, i + 77, j + 7 + 50 - Math.abs(HappinessFood.happiness), 225, 123 - Math.abs(HappinessFood.happiness), 14, Math.abs(HappinessFood.happiness) + 1);
        }
    }
}
