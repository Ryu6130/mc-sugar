package sugar.component;

import net.fabricmc.fabric.api.item.v1.ItemComponentTooltipProviderRegistry;
import net.minecraft.core.Registry;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.BuiltInRegistries;
import sugar.SugarMod;

public class ModDataComponents {
    public static final DataComponentType<SimpleTooltipComponent> SIMPLE_TOOLTIP =
        Registry.register(
            BuiltInRegistries.DATA_COMPONENT_TYPE,
            SugarMod.id("simple_tooltip"),
            DataComponentType.<SimpleTooltipComponent>builder()
                .persistent(SimpleTooltipComponent.CODEC)
                .build()
        );
        
    public static final DataComponentType<InventoryAttributesComponent> INVENTORY_ATTRIBUTES =
        Registry.register(
            BuiltInRegistries.DATA_COMPONENT_TYPE,
            SugarMod.id("inventory_attributes"),
            DataComponentType.<InventoryAttributesComponent>builder()
                .persistent(InventoryAttributesComponent.CODEC)
                .build()
        );

    public static void initialize() {
        ItemComponentTooltipProviderRegistry.addLast(
            INVENTORY_ATTRIBUTES
        );
        ItemComponentTooltipProviderRegistry.addLast(
            SIMPLE_TOOLTIP
        );
        InventoryAttributesManager.initialize();
    }
}
