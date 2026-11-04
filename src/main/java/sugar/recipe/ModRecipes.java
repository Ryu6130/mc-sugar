package sugar.recipe;

import net.fabricmc.fabric.api.recipe.v1.sync.RecipeSynchronization;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
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

    public static void initialize() {
        RecipeSynchronization.synchronizeRecipeSerializer(ModRecipes.DESUGARER_SERIALIZER);
    }
}
