package com.sjkz1.manager;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.stat.ServerStatHandler;
import net.minecraft.stat.Stats;
import net.minecraft.util.math.MathHelper;

public class HappinessManager {
    private int happiness = 50;

    public void readNbt(NbtCompound nbt) {
        if (nbt.contains("happiness", NbtElement.NUMBER_TYPE)) {
            this.happiness = nbt.getInt("happiness");
        }
    }

    public void update(PlayerEntity player) {
        this.happiness = this.getHappiness();
        if (happiness >= 50) {
            happiness = 50;
        }
        else if (happiness <= -50) {
            happiness = -50;
        }

        if (player.canFoodHeal()) {
            if (!this.isSad()) {
                player.heal(this.getHappiness() * 0.001F);
            } else {
                if (this.getHappiness() == 0) {
                    player.heal(0.1F);
                } else {
                    player.heal(Math.abs(this.getHappiness()) * 0.0005F);
                }
            }
        }

        ServerStatHandler serverStatHandler = ((ServerPlayerEntity) player).getStatHandler();
        int j = MathHelper.clamp(serverStatHandler.getStat(Stats.CUSTOM.getOrCreateStat(Stats.TIME_SINCE_REST)), 1, Integer.MAX_VALUE);
        int k = 24000;
        if (j > k) {
            if (player.getRandom().nextFloat() < 0.007) {
                this.decrease(2);
            }
        }
    }

    public void writeNbt(NbtCompound nbt) {
        nbt.putInt("happiness", this.happiness);
    }

    public boolean isSad() {
        return happiness <= 0;
    }

    public int getHappiness() {
        return happiness;
    }

    public void increase(int happiness) {
        this.happiness += happiness;
    }

    public void decrease(int happiness) {
        this.happiness -= happiness;
    }

}
