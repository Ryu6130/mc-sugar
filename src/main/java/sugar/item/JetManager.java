package sugar.item;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.protocol.game.ClientboundSetEntityMotionPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.phys.Vec3;

public class JetManager {
    private static final Map<UUID, Boolean> JET_MAP = new HashMap<>();

    private static boolean wasPressed = false;

    public static void clientInitialize() {
        ClientTickEvents.START_CLIENT_TICK.register(client -> {
            if (client.player == null) return;

            boolean pressed = client.options.keyJump.isDown();
            
            if (client.player.onGround()) {
                if (wasPressed) {
                    ClientPlayNetworking.send(
                        new JetPayload(false)
                    );
                }
                wasPressed = pressed;
                return;
            }

            if (pressed != wasPressed) {
                ClientPlayNetworking.send(
                    new JetPayload(pressed)
                );

                wasPressed = pressed;
            }
        });
    }

    public static void initialize() {
        PayloadTypeRegistry.serverboundPlay().register(
            JetPayload.TYPE,
            JetPayload.CODEC
        );

        ServerPlayNetworking.registerGlobalReceiver(
            JetPayload.TYPE, (payload, context) -> {
                ServerPlayer player = context.player();
                
                JET_MAP.put(player.getUUID(), payload.pressed());
            }
        );

        ServerTickEvents.END_SERVER_TICK.register(server -> {
            for (ServerPlayer player : server.getPlayerList().getPlayers()) {

                if (!JET_MAP.getOrDefault(player.getUUID(), false)) continue;
                if (!player.getItemBySlot(EquipmentSlot.FEET).is(ModItems.JET_BOOTS)) continue;

                Vec3 velocity = player.getDeltaMovement();
                Vec3 angle = player.getHeadLookAngle();

                if (player.isShiftKeyDown())
                    player.setDeltaMovement(
                        angle.scale(0.3)
                    );
                else
                    player.setDeltaMovement(
                        velocity.x,
                        0.3,
                        velocity.z
                    );

                player.resetFallDistance();
                player.connection.send(
                    new ClientboundSetEntityMotionPacket(player)
                );
            }
        });
    }
}
