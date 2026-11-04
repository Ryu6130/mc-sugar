package sugar.recipe;

import javax.annotation.Nullable;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.PlacementInfo;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeBookCategory;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;

public class DesugarerRecipe implements Recipe<DesugarerRecipeInput> {
	private final ItemStackTemplate result;
    private final int time;
	private final Ingredient ingredient;

	public DesugarerRecipe(ItemStackTemplate result, int time, Ingredient ingredient) {
		this.ingredient = ingredient;
        this.time = time;
		this.result = result;
	}

	public ItemStackTemplate getResult() {
		return this.result;
	}

	public Ingredient getIngredient() {
		return this.ingredient;
	}

	public int getTime() {
		return this.time;
	}

    @Override
    public boolean matches(DesugarerRecipeInput recipeInput, Level level) {
        return this.ingredient.test(recipeInput.ingredient());
    }

    @Override
    public ItemStack assemble(DesugarerRecipeInput recipeInput) {
        return this.result.create();
    }

    public static final MapCodec<DesugarerRecipe> CODEC = RecordCodecBuilder.mapCodec(instance ->
        instance.group(
            ItemStackTemplate.CODEC.fieldOf("result").forGetter(DesugarerRecipe::getResult),
            Codec.INT.fieldOf("time").forGetter(DesugarerRecipe::getTime),
            Ingredient.CODEC.fieldOf("ingredient").forGetter(DesugarerRecipe::getIngredient)
        ).apply(instance, DesugarerRecipe::new)
    );

    public static final StreamCodec<RegistryFriendlyByteBuf, DesugarerRecipe> STREAM_CODEC = StreamCodec.composite(
        ItemStackTemplate.STREAM_CODEC,
        DesugarerRecipe::getResult,
        ByteBufCodecs.INT,
        DesugarerRecipe::getTime,
        Ingredient.CONTENTS_STREAM_CODEC,
        DesugarerRecipe::getIngredient,
        DesugarerRecipe::new
    );

    @Override
    public RecipeSerializer<? extends Recipe<DesugarerRecipeInput>> getSerializer() {
        return ModRecipes.DESUGARER_SERIALIZER;
    }

    @Override
    public RecipeType<? extends Recipe<DesugarerRecipeInput>> getType() {
        return ModRecipes.DESUGARER;
    }

    @Override
    public @Nullable RecipeBookCategory recipeBookCategory() {
        return null;
    }

    @Override
    public PlacementInfo placementInfo() {
        return PlacementInfo.NOT_PLACEABLE;
    }

    @Override
    public boolean isSpecial() {
        return true;
    }

    @Override
    public boolean showNotification() {
        return true;
    }

    @Override
    public String group() {
        return "desugaring";
    }
}
