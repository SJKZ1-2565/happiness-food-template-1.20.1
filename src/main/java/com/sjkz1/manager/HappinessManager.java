package com.sjkz1.manager;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.stat.ServerStatHandler;
import net.minecraft.stat.Stats;
import net.minecraft.util.math.MathHelper;

public class HappinessManager {
    private float happiness = 10;

    public void readNbt(NbtCompound nbt) {
        if (nbt.contains("happiness", NbtElement.NUMBER_TYPE)) {
            this.happiness = nbt.getFloat("happiness");
        }
    }

    public void update(PlayerEntity player) {
        this.happiness = this.getHappiness();
        if (happiness >= 10) {
            happiness = 10;
        } else if (happiness <= -10) {
            happiness = -10;
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
        nbt.putFloat("happiness", this.happiness);
    }

    public boolean isSad() {
        return happiness <= 0;
    }

    public float getHappiness() {
        return happiness;
    }

    public void increase(float happiness) {
        this.happiness += happiness;
    }

    public void decrease(float happiness) {
        this.happiness -= happiness;
    }

}
