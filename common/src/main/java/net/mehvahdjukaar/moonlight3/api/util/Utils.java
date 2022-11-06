package net.mehvahdjukaar.moonlight3.api.util;

import io.netty.util.internal.UnstableApi;
import net.mehvahdjukaar.moonlight3.api.platform.PlatformHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.core.Direction;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Supplier;


public class Utils {

    public static void swapItem(Player player, InteractionHand hand, ItemStack oldItem, ItemStack newItem, boolean bothSides) {
        if (!player.level.isClientSide || bothSides)
            player.setItemInHand(hand, ItemUtils.createFilledResult(oldItem.copy(), player, newItem, player.isCreative()));
    }

    public static void swapItem(Player player, InteractionHand hand, ItemStack oldItem, ItemStack newItem) {
        if (!player.level.isClientSide)
            player.setItemInHand(hand, ItemUtils.createFilledResult(oldItem.copy(), player, newItem, player.isCreative()));
    }

    public static void swapItemNBT(Player player, InteractionHand hand, ItemStack oldItem, ItemStack newItem) {
        if (!player.level.isClientSide)
            player.setItemInHand(hand, ItemUtils.createFilledResult(oldItem.copy(), player, newItem, false));
    }

    public static void swapItem(Player player, InteractionHand hand, ItemStack newItem) {
        if (!player.level.isClientSide)
            player.setItemInHand(hand, ItemUtils.createFilledResult(player.getItemInHand(hand).copy(), player, newItem, player.isCreative()));
    }

    public static void addStackToExisting(Player player, ItemStack stack) {
        var inv = player.getInventory();
        boolean added = false;
        for (int j = 0; j < inv.items.size(); j++) {
            if (inv.getItem(j).is(stack.getItem()) && inv.add(j, stack)) {
                added = true;
                break;
            }
        }
        if (!added && inv.add(stack)) {
            player.drop(stack, false);
        }
    }


    public static ResourceLocation getID(Block object) {
        return Registry.BLOCK.getKey(object);
    }

    public static ResourceLocation getID(EntityType<?> object) {
        return Registry.ENTITY_TYPE.getKey(object);
    }

    //TODO: not sure if this is correct
    public static ResourceLocation getID(Biome object) {
        return BuiltinRegistries.BIOME.getKey(object);
    }

    public static ResourceLocation getID(ConfiguredFeature<?,?> object) {
        return BuiltinRegistries.CONFIGURED_FEATURE.getKey(object);
    }

    public static ResourceLocation getID(Item object) {
        return Registry.ITEM.getKey(object);
    }

    public static ResourceLocation getID(Fluid object) {
        return Registry.FLUID.getKey(object);
    }

    public static ResourceLocation getID(BlockEntityType<?> object) {
        return Registry.BLOCK_ENTITY_TYPE.getKey(object);
    }

    public static ResourceLocation getID(RecipeSerializer<?> object) {
        return Registry.RECIPE_SERIALIZER.getKey(object);
    }

    @UnstableApi
    public static ResourceLocation getID(Object object) {
        if (object instanceof Block b) return getID(b);
        if (object instanceof Item b) return getID(b);
        if (object instanceof EntityType<?> b) return getID(b);
        if (object instanceof Biome b) return getID(b);
        if (object instanceof Fluid b) return getID(b);
        if (object instanceof BlockEntityType<?> b) return getID(b);
        if (object instanceof RecipeSerializer<?> b) return getID(b);
        if (object instanceof ConfiguredFeature<?,?> c) return getID(c);
        if (object instanceof Supplier<?> s) return getID(s.get());
        throw new UnsupportedOperationException("Unknown class type " + object.getClass());
    }

    //very hacky
    public static RegistryAccess hackyGetRegistryAccess() {
        if (PlatformHelper.getEnv().isClient()) {
            var level = Minecraft.getInstance().level;
            if (level != null) return level.registryAccess();
        }
        return PlatformHelper.getCurrentServer().registryAccess();
    }

}