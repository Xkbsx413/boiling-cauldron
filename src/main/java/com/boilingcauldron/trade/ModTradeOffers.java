package com.boilingcauldron.trade;

import com.boilingcauldron.item.ModItems;
import net.fabricmc.fabric.api.object.builder.v1.trade.TradeOfferHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.village.TradeOffer;
import net.minecraft.village.TradeOffers;

import java.util.List;

/**
 * 为流浪商人添加种子交易，作为村庄箱子之外的保底获取途径。
 */
public final class ModTradeOffers {

    private static final int MAX_USES = 2;
    private static final int MERCHANT_EXPERIENCE = 1;
    private static final float PRICE_MULTIPLIER = 0.05F;

    private ModTradeOffers() {
    }

    public static void register() {
        TradeOfferHelper.registerWanderingTraderOffers(1, factories -> {
            addSeedOffer(factories, ModItems.Ginger_Seeds);
            addSeedOffer(factories, ModItems.Garlic_Seeds);
            addSeedOffer(factories, ModItems.Gong_Cai_Seeds);
            addSeedOffer(factories, ModItems.Sichuan_Pepper_Seeds);
        });
    }

    private static void addSeedOffer(List<TradeOffers.Factory> factories, Item seed) {
        factories.add((entity, random) -> new TradeOffer(
            new ItemStack(Items.EMERALD, 1),
            new ItemStack(seed, 1),
            MAX_USES,
            MERCHANT_EXPERIENCE,
            PRICE_MULTIPLIER
        ));
    }
}
