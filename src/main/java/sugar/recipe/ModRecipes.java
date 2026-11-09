package sugar.recipe;

import java.util.Optional;

import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.fabricmc.fabric.api.event.player.UseEntityCallback;
import net.fabricmc.fabric.api.recipe.v1.sync.RecipeSynchronization;
import net.fabricmc.fabric.api.transfer.v1.item.ItemVariant;
import net.fabricmc.fabric.api.transfer.v1.item.PlayerInventoryStorage;
import net.fabricmc.fabric.api.transfer.v1.transaction.Transaction;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.block.state.BlockState;
import sugar.SugarMod;

public class ModRecipes {
    public static final RecipeSerializer<DesugarerRecipe> DESUGARER_SERIALIZER = Registry.register(
        BuiltInRegistries.RECIPE_SERIALIZER,
        SugarMod.id("desugaring"),
        new RecipeSerializer<>(DesugarerRecipe.CODEC, DesugarerRecipe.STREAM_CODEC)
    );

    public static final RecipeType<DesugarerRecipe> DESUGARER = Registry.register(
        BuiltInRegistries.RECIPE_TYPE,
        SugarMod.id("desugaring"),
        new RecipeType<DesugarerRecipe>() { }
    );
    
    public static final RecipeSerializer<ClickingBlockRecipe> CLICKING_BLOCK_SERIALIZER = Registry.register(
        BuiltInRegistries.RECIPE_SERIALIZER,
        SugarMod.id("clicking_block"),
        new RecipeSerializer<>(ClickingBlockRecipe.CODEC, ClickingBlockRecipe.STREAM_CODEC)
    );

    public static final RecipeType<ClickingBlockRecipe> CLICKING_BLOCK = Registry.register(
        BuiltInRegistries.RECIPE_TYPE,
        SugarMod.id("clicking_block"),
        new RecipeType<ClickingBlockRecipe>() { }
    );
    
    public static final RecipeSerializer<ClickingEntityRecipe> CLICKING_ENTITY_SERIALIZER = Registry.register(
        BuiltInRegistries.RECIPE_SERIALIZER,
        SugarMod.id("clicking_entity"),
        new RecipeSerializer<>(ClickingEntityRecipe.CODEC, ClickingEntityRecipe.STREAM_CODEC)
    );

    public static final RecipeType<ClickingEntityRecipe> CLICKING_ENTITY = Registry.register(
        BuiltInRegistries.RECIPE_TYPE,
        SugarMod.id("clicking_entity"),
        new RecipeType<ClickingEntityRecipe>() { }
    );
    
    public static final RecipeSerializer<FermentingRecipe> FERMENTING_SERIALIZER = Registry.register(
        BuiltInRegistries.RECIPE_SERIALIZER,
        SugarMod.id("fermenting"),
        new RecipeSerializer<>(FermentingRecipe.CODEC, FermentingRecipe.STREAM_CODEC)
    );

    public static final RecipeType<FermentingRecipe> FERMENTING = Registry.register(
        BuiltInRegistries.RECIPE_TYPE,
        SugarMod.id("fermenting"),
        new RecipeType<FermentingRecipe>() { }
    );

    public static void initialize() {
        RecipeSynchronization.synchronizeRecipeSerializer(DESUGARER_SERIALIZER);
        RecipeSynchronization.synchronizeRecipeSerializer(CLICKING_BLOCK_SERIALIZER);
        RecipeSynchronization.synchronizeRecipeSerializer(CLICKING_ENTITY_SERIALIZER);
        RecipeSynchronization.synchronizeRecipeSerializer(FERMENTING_SERIALIZER);
        
        UseBlockCallback.EVENT.register((player, level, hand, hitResult) -> {
            ItemStack stack = player.getItemInHand(hand);
            BlockState state = level.getBlockState(hitResult.getBlockPos());

            if (level.isClientSide() || !(level instanceof ServerLevel serverLevel))
                return InteractionResult.PASS;

            ClickingBlockRecipeInput recipeInput = new ClickingBlockRecipeInput(state, stack);
            Optional<RecipeHolder<ClickingBlockRecipe>> maybeRecipe = serverLevel.recipeAccess()
                .getRecipeFor(ModRecipes.CLICKING_BLOCK, recipeInput, level);
            
            if (maybeRecipe.isPresent()) {
                RecipeHolder<ClickingBlockRecipe> recipeHolder = maybeRecipe.get();
                ClickingBlockRecipe recipe = recipeHolder.value();

                ItemStack result = recipe.assemble(recipeInput);

                stack.shrink(1);
                try (Transaction transaction = Transaction.openOuter()) {
                    PlayerInventoryStorage.of(player).offerOrDrop(
                        ItemVariant.of(result),
                        result.getCount(),
                        transaction
                    );

                    transaction.commit();
                }

                return InteractionResult.CONSUME;
            }

            return InteractionResult.PASS;
        });

        UseEntityCallback.EVENT.register((player, level, hand, entity, hitResult) -> {
            ItemStack stack = player.getItemInHand(hand);

            if (level.isClientSide() || !(level instanceof ServerLevel serverLevel))
                return InteractionResult.PASS;

            ClickingEntityRecipeInput recipeInput = new ClickingEntityRecipeInput(entity, stack);
            Optional<RecipeHolder<ClickingEntityRecipe>> maybeRecipe = serverLevel.recipeAccess()
                .getRecipeFor(ModRecipes.CLICKING_ENTITY, recipeInput, level);
            
            if (maybeRecipe.isPresent()) {
                RecipeHolder<ClickingEntityRecipe> recipeHolder = maybeRecipe.get();
                ClickingEntityRecipe recipe = recipeHolder.value();

                ItemStack result = recipe.assemble(recipeInput);

                stack.shrink(1);
                try (Transaction transaction = Transaction.openOuter()) {
                    PlayerInventoryStorage.of(player).offerOrDrop(
                        ItemVariant.of(result),
                        result.getCount(),
                        transaction
                    );

                    transaction.commit();
                }

                return InteractionResult.CONSUME;
            }

            return InteractionResult.PASS;
        });
    }
}
