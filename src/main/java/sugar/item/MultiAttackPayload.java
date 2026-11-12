package sugar.item;

import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import sugar.SugarMod;

public record MultiAttackPayload() implements CustomPacketPayload {
    public static final Type<MultiAttackPayload> TYPE =
        new Type<>(SugarMod.id("multi_attack"));

    public static final StreamCodec<RegistryFriendlyByteBuf, MultiAttackPayload> CODEC =
        StreamCodec.unit(new MultiAttackPayload());

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
