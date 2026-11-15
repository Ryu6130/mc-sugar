package sugar.item;

import java.util.List;

import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.component.Tool;
import net.minecraft.world.item.component.Tool.Rule;
import net.minecraft.world.level.block.Block;

public class ModTools {
    private static final HolderGetter<Block> registrationLookup =
        BuiltInRegistries.acquireBootstrapRegistrationLookup(BuiltInRegistries.BLOCK);
 
    public static final Tool SWORD =
        new Tool(
            List.of(
                Rule.minesAndDrops(
                    HolderSet.direct(
                        registrationLookup.getOrThrow(
                            ResourceKey.create(
                                Registries.BLOCK,
                                Identifier.withDefaultNamespace("cobweb")
                            )
                        )
                    ),
                    15.0F
                ),
                Rule.overrideSpeed(
                    registrationLookup.getOrThrow(BlockTags.SWORD_INSTANTLY_MINES),
                    Float.MAX_VALUE
                ),
                Rule.overrideSpeed(
                    registrationLookup.getOrThrow(BlockTags.SWORD_EFFICIENT),
                    1.5F
                )
            ),
            1.0F,
            2,
            false
        );
}
