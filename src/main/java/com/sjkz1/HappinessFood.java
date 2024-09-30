package com.sjkz1;

import com.google.common.collect.Maps;
import com.sjkz1.effect.HappyStatusEffect;
import com.sjkz1.effect.UnHappyStatusEffect;
import net.fabricmc.api.ModInitializer;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

public class HappinessFood implements ModInitializer {
    public static final String MOD_ID = "happiness-food";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
    public static final StatusEffect HAPPY_EFFECT;
    public static final StatusEffect SAD_EFFECT;
    public static final Identifier HAPPINESS = Identifier.of(MOD_ID, "happiness");
    public static int happiness;


    static {
        HAPPY_EFFECT = Registry.register(Registries.STATUS_EFFECT, Identifier.of(MOD_ID, "happy"), new HappyStatusEffect());
        SAD_EFFECT = Registry.register(Registries.STATUS_EFFECT, Identifier.of(MOD_ID, "sad"), new UnHappyStatusEffect());
    }

    public static final Map<Item, Integer> GOOD_FOODS_HAPPINESS_LEVEL = Util.make(Maps.newHashMap(), map -> {
        map.put(Items.APPLE, 1);
        map.put(Items.GOLDEN_APPLE, 4);
        map.put(Items.ENCHANTED_GOLDEN_APPLE, 5);
        map.put(Items.GOLDEN_CARROT, 4);
        map.put(Items.COOKED_BEEF, 3);
        map.put(Items.COOKED_MUTTON, 3);
        map.put(Items.COOKED_PORKCHOP, 3);
        map.put(Items.COOKED_SALMON, 3);
        map.put(Items.BAKED_POTATO, 2);
        map.put(Items.BEETROOT, 2);
        map.put(Items.BEETROOT_SOUP, 2);
        map.put(Items.BREAD, 2);
        map.put(Items.CARROT, 2);
        map.put(Items.COOKED_CHICKEN, 2);
        map.put(Items.COOKED_COD, 2);
        map.put(Items.COOKED_RABBIT, 2);
        map.put(Items.MUSHROOM_STEW, 2);
        map.put(Items.RABBIT_STEW, 2);
        map.put(Items.SUSPICIOUS_STEW, 2);
        map.put(Items.CHORUS_FRUIT, 1);
        map.put(Items.DRIED_KELP, 1);
        map.put(Items.PUMPKIN_PIE, 1);
        map.put(Items.SWEET_BERRIES, 1);
        map.put(Items.GLOW_BERRIES, 1);
        map.put(Items.CAKE, 3);
        map.put(Items.COOKIE, 3);
        map.put(Items.HONEY_BOTTLE, 2);
        map.put(Items.SALMON, 2);
    });

    public static final Map<Item, Integer> BAD_FOODS_HAPPINESS_LEVEL = Util.make(Maps.newHashMap(), map -> {
        map.put(Items.POISONOUS_POTATO, 2);
        map.put(Items.POTATO, 1);
        map.put(Items.BEEF, 3);
        map.put(Items.CHICKEN, 3);
        map.put(Items.MUTTON, 3);
        map.put(Items.PORKCHOP, 3);
        map.put(Items.RABBIT, 3);
        map.put(Items.COD, 2);
        map.put(Items.ROTTEN_FLESH, 5);
        map.put(Items.SPIDER_EYE, 5);
    });


    @Override
    public void onInitialize() {
        LOGGER.info("Hello Fabric world!");
    }
}