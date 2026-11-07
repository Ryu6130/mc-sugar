package sugar.recipe;

import javax.annotation.Nullable;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.minecraft.network.RegistryFriendlyByteBuf;
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
import net.minecraft.advancements.predicates.BlockPredicate;

public class ClickingBlockRecipe implements Recipe<ClickingBlockRecipeInput> {
	private final ItemStackTemplate result;
	private final Ingredient item;
	private final BlockPredicate block;

	public ClickingBlockRecipe(ItemStackTemplate result, Ingredient item, BlockPredicate block) {
		this.item = item;
		this.result = result;
        this.block = block;
	}

	public ItemStackTemplate getResult() {
		return this.result;
	}

	public Ingredient getItem() {
		return this.item;
	}

	public BlockPredicate getBlock() {
		return this.block;
	}

    @Override
    public boolean matches(ClickingBlockRecipeInput recipeInput, Level level) {
        return this.item.test(recipeInput.item()) && this.block.matchesState(recipeInput.block());
    }

    @Override
    public ItemStack assemble(ClickingBlockRecipeInput recipeInput) {
        return this.result.create();
    }

    public static final MapCodec<ClickingBlockRecipe> CODEC = RecordCodecBuilder.mapCodec(instance ->
        instance.group(
            ItemStackTemplate.CODEC.fieldOf("result").forGetter(ClickingBlockRecipe::getResult),
            Ingredient.CODEC.fieldOf("item").forGetter(ClickingBlockRecipe::getItem),
            BlockPredicate.CODEC.fieldOf("block").forGetter(ClickingBlockRecipe::getBlock)
        ).apply(instance, ClickingBlockRecipe::new)
    );

    public static final StreamCodec<RegistryFriendlyByteBuf, ClickingBlockRecipe> STREAM_CODEC = StreamCodec.composite(
        ItemStackTemplate.STREAM_CODEC,
        ClickingBlockRecipe::getResult,
        Ingredient.CONTENTS_STREAM_CODEC,
        ClickingBlockRecipe::getItem,
        BlockPredicate.STREAM_CODEC,
        ClickingBlockRecipe::getBlock,
        ClickingBlockRecipe::new
    );

    @Override
    public RecipeSerializer<? extends Recipe<ClickingBlockRecipeInput>> getSerializer() {
        return ModRecipes.CLICKING_BLOCK_SERIALIZER;
    }

    @Override
    public RecipeType<? extends Recipe<ClickingBlockRecipeInput>> getType() {
        return ModRecipes.CLICKING_BLOCK;
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
        return "clicking_block";
    }
}
