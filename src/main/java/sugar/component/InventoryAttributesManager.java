package sugar.component;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.core.Holder;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ItemStack;
import sugar.component.InventoryAttributesComponent.Entry;;

public class InventoryAttributesManager {
    private static final Map<UUID, Map<Identifier, Map<Holder<Attribute>, AttributeModifier>>>
        APPLIED = new HashMap<>();

    public static void initialize() {
        ServerTickEvents.END_SERVER_TICK.register(server -> {
            if (server.getTickCount() % 20 != 0) return;

            for (ServerPlayer player : server.getPlayerList().getPlayers()) {
                Map<Identifier, Map<Holder<Attribute>, AttributeModifier>> applied =
                    APPLIED.computeIfAbsent(player.getUUID(), _ -> new HashMap<>());
                applied.forEach((id, entry) -> {
                    entry.forEach((attr, _) -> {
                        AttributeInstance instance = player.getAttribute(attr);
                        if (instance != null) instance.removeModifier(id);
                    });
                });
                applied.clear();

                for (ItemStack stack : player.getInventory()) {
                    if (stack.isEmpty()) continue;

                    InventoryAttributesComponent component =
                        stack.get(ModDataComponents.INVENTORY_ATTRIBUTES);

                    if (component == null) continue;

                    for (Entry entry : component.entries()) {
                        Map<Holder<Attribute>, AttributeModifier> appliedEntry =
                            applied.computeIfAbsent(entry.modifier().id(), _ -> new HashMap<>());
                        AttributeModifier preModifier = appliedEntry.get(entry.attribute());
                        
                        if (preModifier != null) {
                            AttributeModifier modifier = new AttributeModifier(
                                entry.modifier().id(),
                                entry.modifier().amount() + preModifier.amount(),
                                entry.modifier().operation()
                            );
                            entry = new Entry(entry.attribute(), modifier);
                        }

                        appliedEntry.put(entry.attribute(), entry.modifier());
                        AttributeInstance instance = player.getAttribute(entry.attribute());
                        if (instance != null)
                            instance.addOrUpdateTransientModifier(entry.modifier());
                    }
                }
            }
        });
    }
}
