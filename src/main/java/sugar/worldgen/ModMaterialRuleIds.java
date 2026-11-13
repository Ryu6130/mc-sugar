package sugar.worldgen;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.material.rule.MaterialRule;
import sugar.SugarMod;

public class ModMaterialRuleIds {
    public static final ResourceKey<MaterialRule> SUGAR_FOREST = create("sugar_forest");

    public static ResourceKey<MaterialRule> create(String name) {
        return ResourceKey.create(Registries.MATERIAL_RULE, SugarMod.id(name));
    }
}
