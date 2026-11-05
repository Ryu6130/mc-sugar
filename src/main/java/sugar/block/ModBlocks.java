package sugar.block;

import java.util.function.Function;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.references.BlockItemId;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.PushReaction;

public class ModBlocks {
    public static final Block SUGAR_CUBE = register(
		ModBlockItemIds.SUGAR_CUBE,
		SugarCube::new,
		BlockBehaviour.Properties.of()
			.sound(SoundType.SAND)
			.strength(0.25f)
			.pushReaction(PushReaction.POPPED)
    );

    public static final Block KONPEITO_BLOCK = register(
		ModBlockItemIds.KONPEITO_BLOCK,
		Block::new,
		BlockBehaviour.Properties.of()
			.sound(SoundType.STONE)
			.strength(0.25f)
			.pushReaction(PushReaction.POPPED)
    );
	
    public static final Block DESUGARER = register(
		ModBlockItemIds.DESUGARER,
		Desugarer::new,
		BlockBehaviour.Properties.of()
			.sound(SoundType.STONE)
			.strength(1f)
			.pushReaction(PushReaction.IMMOVEABLE)
    );
	
    public static final Block SUGARRACK = register(
		ModBlockItemIds.SUGARRACK,
		Block::new,
		BlockBehaviour.Properties.of()
			.sound(SoundType.NETHERRACK)
			.strength(3f)
    );

	private static Block register(
        BlockItemId id,
        Function<BlockBehaviour.Properties, Block> blockFactory,
        BlockBehaviour.Properties properties
    ) {
		Block block = register(id.block(), blockFactory, properties);

		BlockItem blockItem = new BlockItem(block, new Item.Properties().useBlockDescriptionPrefix().setId(id.item()));
		Registry.register(BuiltInRegistries.ITEM, id.item(), blockItem);

		return block;
	}

	private static Block register(
        ResourceKey<Block> id,
        Function<BlockBehaviour.Properties, Block> blockFactory,
        BlockBehaviour.Properties properties
    ) {
		Block block = blockFactory.apply(properties.setId(id));

		return Registry.register(BuiltInRegistries.BLOCK, id, block);
	}

    public static void initialize() {}
}