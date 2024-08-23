package com.breelock.hamster_business.item;

import com.breelock.hamster_business.ModConfig;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

import java.util.List;
import java.util.Random;

public class HamsterPhoneItem extends Item {
    private static final float hungerCost = 0.3f;
    private static final int maxDamage = 500;

    public HamsterPhoneItem(Settings settings) {
        super(settings.maxDamage(maxDamage));
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
        ItemStack itemStack = player.getStackInHand(hand);

        if (!world.isClient) {
            if (player.getHungerManager().getFoodLevel() > 6) {
                ModConfig.hamsterCoins++;

                if (ModConfig.hamsterCoins % 5 == 0) {
                    List<Item> allItems = Registries.ITEM.stream().toList();

                    Random random = new Random();
                    Item randomItem = allItems.get(random.nextInt(allItems.size()));

                    ItemStack randomItemStack = new ItemStack(randomItem, 1);

                    if (!player.getInventory().insertStack(randomItemStack)) {
                        player.dropItem(randomItemStack, false);
                    }
                }

                String coinsText;
                if (ModConfig.hamsterCoins == 1)
                    coinsText = Text.translatable("gui.hamster_business.hamster_coin0").getString();
                else if (ModConfig.hamsterCoins >= 2 && ModConfig.hamsterCoins <= 4)
                    coinsText = Text.translatable("gui.hamster_business.hamster_coin1").getString();
                else
                    coinsText = Text.translatable("gui.hamster_business.hamster_coin2").getString();

                player.sendMessage(Text.literal(Text.translatable("gui.hamster_business.you_earned").getString() + ": " + ModConfig.hamsterCoins + " " + coinsText).formatted(Formatting.GREEN), true);

                itemStack.damage(1, player, (p) -> p.sendToolBreakStatus(hand));
                player.getHungerManager().addExhaustion(hungerCost);
            } else {
                player.sendMessage(Text.literal(Text.translatable("gui.hamster_business.cannot_tap").getString()).formatted(Formatting.RED), true);
            }
        }

        return new TypedActionResult<>(ActionResult.SUCCESS, itemStack);
    }

    @Override
    public boolean canRepair(ItemStack stack, ItemStack ingredient) {
        return ingredient.isOf(Items.IRON_INGOT) || ingredient.isOf(this);
    }
}
