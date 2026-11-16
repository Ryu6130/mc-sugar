package sugar.component;

import java.util.List;
import java.util.function.Consumer;

import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.minecraft.ChatFormatting;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponentGetter;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.world.item.Item.TooltipContext;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.item.component.TooltipProvider;

public record InventoryAttributesComponent(List<Entry> entries) implements TooltipProvider {
    public static final Codec<InventoryAttributesComponent> CODEC =
        Entry.CODEC.listOf().xmap(
            InventoryAttributesComponent::new,
            InventoryAttributesComponent::entries
        );

    public static class Builder {
        private final ImmutableList.Builder<Entry> entries = ImmutableList.builder();

        public Builder add(final Holder<Attribute> attribute, final AttributeModifier modifier) {
            this.entries.add(new Entry(attribute, modifier));
            return this;
        }

        public InventoryAttributesComponent build() {
            return new InventoryAttributesComponent(this.entries.build());
        }
    }

    public static Builder builder() {
        return new Builder();
    }

    public static record Entry(Holder<Attribute> attribute, AttributeModifier modifier) {
        public static final Codec<Entry> CODEC =
            RecordCodecBuilder.create(
                (i) -> i.group(
                    Attribute.CODEC.fieldOf("type").forGetter(Entry::attribute),
                    AttributeModifier.MAP_CODEC.forGetter(Entry::modifier)
                ).apply(i, Entry::new)
            );
        public static final StreamCodec<RegistryFriendlyByteBuf, Entry> STREAM_CODEC =
            StreamCodec.composite(
                Attribute.STREAM_CODEC,
                Entry::attribute,
                AttributeModifier.STREAM_CODEC,
                Entry::modifier,
                Entry::new
            );

        public boolean matches(final Holder<Attribute> attribute, final Identifier id) {
            return attribute.equals(this.attribute) && this.modifier.is(id);
        }
    }

    @Override
    public void addToTooltip(
        TooltipContext context,
        Consumer<Component> consumer,
        TooltipFlag flag,
        DataComponentGetter components
    ) {
        consumer.accept(CommonComponents.EMPTY);
        consumer.accept(Component.translatable("item.modifiers.inventory").withStyle(ChatFormatting.GRAY));
        
        for (Entry entry : entries) {
            AttributeModifier modifier = entry.modifier;
            Holder<Attribute> attribute = entry.attribute;

            double amount = modifier.amount();
            boolean displayWithBase = false;
            /*if (player != null) {
                if (modifier.is(Item.BASE_ATTACK_DAMAGE_ID)) {
                    amount += player.getAttributeBaseValue(Attributes.ATTACK_DAMAGE);
                    displayWithBase = true;
                } else if (modifier.is(Item.BASE_ATTACK_SPEED_ID)) {
                    amount += player.getAttributeBaseValue(Attributes.ATTACK_SPEED);
                    displayWithBase = true;
                }
            }*/

            double displayAmount;
            if (modifier.operation() != Operation.ADD_MULTIPLIED_BASE && modifier.operation() != Operation.ADD_MULTIPLIED_TOTAL) {
                if (attribute.equals(Attributes.KNOCKBACK_RESISTANCE)) {
                    displayAmount = amount * (double)10.0F;
                } else {
                    displayAmount = amount;
                }
            } else {
                displayAmount = amount * (double)100.0F;
            }

            if (displayWithBase) {
                consumer.accept(CommonComponents.space().append(Component.translatable("attribute.modifier.equals." + modifier.operation().id(), new Object[]{ItemAttributeModifiers.ATTRIBUTE_MODIFIER_FORMAT.format(displayAmount), Component.translatable(((Attribute)attribute.value()).getDescriptionId())})).withStyle(ChatFormatting.DARK_GREEN));
            } else if (amount > (double)0.0F) {
                consumer.accept(Component.translatable("attribute.modifier.plus." + modifier.operation().id(), new Object[]{ItemAttributeModifiers.ATTRIBUTE_MODIFIER_FORMAT.format(displayAmount), Component.translatable(((Attribute)attribute.value()).getDescriptionId())}).withStyle(((Attribute)attribute.value()).getStyle(true)));
            } else if (amount < (double)0.0F) {
                consumer.accept(Component.translatable("attribute.modifier.take." + modifier.operation().id(), new Object[]{ItemAttributeModifiers.ATTRIBUTE_MODIFIER_FORMAT.format(-displayAmount), Component.translatable(((Attribute)attribute.value()).getDescriptionId())}).withStyle(((Attribute)attribute.value()).getStyle(false)));
            }
        }
    }
}
