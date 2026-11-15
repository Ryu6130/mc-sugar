package sugar.ability;

import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import sugar.SugarMod;

public record WindAttackPayload() implements CustomPacketPayload {
    public static final Type<WindAttackPayload> TYPE =
        new Type<>(SugarMod.id("wind_attack"));

    public static final StreamCodec<RegistryFriendlyByteBuf, WindAttackPayload> CODEC =
        StreamCodec.unit(new WindAttackPayload());

    @Override
    public Type<? extends WindAttackPayload> type() {
        return TYPE;
    }
}
