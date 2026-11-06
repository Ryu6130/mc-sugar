package sugar.item;

import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import sugar.SugarMod;

public record JetPayload(boolean pressed) implements CustomPacketPayload {
    public static final Type<JetPayload> TYPE =
        new Type<>(SugarMod.id("jet"));

    public static final StreamCodec<RegistryFriendlyByteBuf, JetPayload> CODEC =
        StreamCodec.composite(
            ByteBufCodecs.BOOL,
            JetPayload::pressed,
            JetPayload::new
        );

    @Override
    public Type<? extends JetPayload> type() {
        return TYPE;
    }
}