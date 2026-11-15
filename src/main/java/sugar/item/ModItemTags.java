package sugar.item;

import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import sugar.SugarMod;

public class ModItemTags {
    public static final TagKey<Item> BERRIES = bind("berries");
    public static final TagKey<Item> REPAIRS_JET_BOOTS = bind("repairs_jet_boots");
    public static final TagKey<Item> REPAIRS_CANDY = bind("repairs_candy");

    private static TagKey<Item> bind(final String name) {
        return TagKey.create(Registries.ITEM, SugarMod.id(name));
    }
}
