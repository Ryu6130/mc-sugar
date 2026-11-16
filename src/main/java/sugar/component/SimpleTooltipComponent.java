package sugar.component;

import java.util.List;
import java.util.function.Consumer;

import com.mojang.serialization.Codec;

import net.minecraft.core.component.DataComponentGetter;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentSerialization;
import net.minecraft.world.item.Item.TooltipContext;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipProvider;

public record SimpleTooltipComponent(List<Component> components) implements TooltipProvider {
    public static final Codec<SimpleTooltipComponent> CODEC =
        ComponentSerialization.CODEC.listOf().xmap(
            SimpleTooltipComponent::new,
            SimpleTooltipComponent::components
        );

    @Override
    public void addToTooltip(
        TooltipContext context,
        Consumer<Component> tooltip,
        TooltipFlag flag,
        DataComponentGetter components
    ) {
        for (Component component : this.components) {
            tooltip.accept(component);
        }
    }
    
}
