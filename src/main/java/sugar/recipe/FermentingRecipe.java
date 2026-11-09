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

public class FermentingRecipe implements Recipe<FermentingRecipeInput> {
	private final ItemStackTemplate result;
    private final int time;
	private final Ingredient base;
	private final Ingredient starter;

	public FermentingRecipe(ItemStackTemplate result, int time, Ingredient base, Ingredient starter) {
		this.base = base;
        this.starter = starter;
        this.time = time;
		this.result = result;
	}

	public ItemStackTemplate getResult() {
		return this.result;
	}

	public Ingredient getBase() {
		return this.base;
	}

	public Ingredient getStarter() {
		return this.starter;
	}

	public int getTime() {
		return this.time;
	}

    @Override
    public boolean matches(FermentingRecipeInput recipeInput, Level level) {
        return this.base.test(recipeInput.base()) &&
            this.starter.test(recipeInput.starter());
    }

    @Override
    public ItemStack assemble(FermentingRecipeInput recipeInput) {
        return this.result.create();
    }

    public static final MapCodec<FermentingRecipe> CODEC = RecordCodecBuilder.mapCodec(instance ->
        instance.group(
            ItemStackTemplate.CODEC.fieldOf("result").forGetter(FermentingRecipe::getResult),
            Codec.INT.fieldOf("time").forGetter(FermentingRecipe::getTime),
            Ingredient.CODEC.fieldOf("base").forGetter(FermentingRecipe::getBase),
            Ingredient.CODEC.fieldOf("starter").forGetter(FermentingRecipe::getStarter)
        ).apply(instance, FermentingRecipe::new)
    );

    public static final StreamCodec<RegistryFriendlyByteBuf, FermentingRecipe> STREAM_CODEC = StreamCodec.composite(
        ItemStackTemplate.STREAM_CODEC,
        FermentingRecipe::getResult,
        ByteBufCodecs.INT,
        FermentingRecipe::getTime,
        Ingredient.CONTENTS_STREAM_CODEC,
        FermentingRecipe::getBase,
        Ingredient.CONTENTS_STREAM_CODEC,
        FermentingRecipe::getStarter,
        FermentingRecipe::new
    );

    @Override
    public RecipeSerializer<? extends Recipe<FermentingRecipeInput>> getSerializer() {
        return ModRecipes.FERMENTING_SERIALIZER;
    }

    @Override
    public RecipeType<? extends Recipe<FermentingRecipeInput>> getType() {
        return ModRecipes.FERMENTING;
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
        return "fermenting";
    }
}
