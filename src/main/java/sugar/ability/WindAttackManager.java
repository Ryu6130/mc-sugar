package sugar.ability;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.event.client.player.ClientPreAttackCallback;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import sugar.entity.WindAttack;
import sugar.item.ModItems;

public class WindAttackManager {
    public static void clientInitialize() {
        ClientPreAttackCallback.EVENT.register((client, player, clickCount) -> {
            if (clickCount == 0) return false;
            if (client.gui.screen() != null) return false;
            if (player.getMainHandItem().is(ModItems.SWORD_OF_THE_SAGE_REALM)) {
                ClientPlayNetworking.send(new WindAttackPayload());
            }
            return false;
        });
    }

    public static void initialize() {
        PayloadTypeRegistry.serverboundPlay().register(
            WindAttackPayload.TYPE,
            WindAttackPayload.CODEC
        );

        ServerPlayNetworking.registerGlobalReceiver(
            WindAttackPayload.TYPE,
            (payload, context) -> {
                ServerPlayer player = context.player();
                ItemStack item = player.getMainHandItem();

                if (
                    !item.is(ModItems.SWORD_OF_THE_SAGE_REALM) ||
                    player.getAttackStrengthScale(0) < 1
                ) {
                    return;
                }

                ServerLevel level = player.level();
                WindAttack projectile = new WindAttack(level);
                projectile.setOwner(player);
                projectile.setPos(
                    player.getEyePosition().add(player.getViewVector(1.0F).scale(0.5))
                );
                projectile.setDeltaMovement(player.getViewVector(1.0F).scale(0.8));
                level.addFreshEntity(projectile);
                item.hurtAndBreak(1, player, EquipmentSlot.MAINHAND);
            }
        );
    }
}
