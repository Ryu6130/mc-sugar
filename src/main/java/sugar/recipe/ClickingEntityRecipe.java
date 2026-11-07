package sugar.recipe;

import javax.annotation.Nullable;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.PlacementInfo;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeBookCategory;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraft.advancements.predicates.entity.EntityPredicate;

public class ClickingEntityRecipe implements Recipe<ClickingEntityRecipeInput> {
	private final ItemStackTemplate result;
	private final Ingredient item;
	private final EntityPredicate entity;

	public ClickingEntityRecipe(ItemStackTemplate result, Ingredient item, EntityPredicate entity) {
		this.item = item;
		this.result = result;
        this.entity = entity;
	}

	public ItemStackTemplate getResult() {
		return this.result;
	}

	public Ingredient getItem() {
		return this.item;
	}

	public EntityPredicate getEntity() {
		return this.entity;
	}

    @Override
    public boolean matches(ClickingEntityRecipeInput recipeInput, Level level) {
        return
            level instanceof ServerLevel serverLevel &&
            this.item.test(recipeInput.item()) &&
            this.entity.matches(serverLevel, recipeInput.entity().position(), recipeInput.entity());
    }

    @Override
    public ItemStack assemble(ClickingEntityRecipeInput recipeInput) {
        return this.result.create();
    }

    public static final MapCodec<ClickingEntityRecipe> CODEC = RecordCodecBuilder.mapCodec(instance ->
        instance.group(
            ItemStackTemplate.CODEC.fieldOf("result").forGetter(ClickingEntityRecipe::getResult),
            Ingredient.CODEC.fieldOf("item").forGetter(ClickingEntityRecipe::getItem),
            EntityPredicate.CODEC.fieldOf("entity").forGetter(ClickingEntityRecipe::getEntity)
        ).apply(instance, ClickingEntityRecipe::new)
    );

    public static final StreamCodec<RegistryFriendlyByteBuf, ClickingEntityRecipe> STREAM_CODEC = StreamCodec.composite(
        ItemStackTemplate.STREAM_CODEC,
        ClickingEntityRecipe::getResult,
        Ingredient.CONTENTS_STREAM_CODEC,
        ClickingEntityRecipe::getItem,
        ByteBufCodecs.fromCodecWithRegistries(EntityPredicate.CODEC),
        ClickingEntityRecipe::getEntity,
        ClickingEntityRecipe::new
    );

    @Override
    public RecipeSerializer<? extends Recipe<ClickingEntityRecipeInput>> getSerializer() {
        return ModRecipes.CLICKING_ENTITY_SERIALIZER;
    }

    @Override
    public RecipeType<? extends Recipe<ClickingEntityRecipeInput>> getType() {
        return ModRecipes.CLICKING_ENTITY;
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
        return "clicking_entity";
    }
}
