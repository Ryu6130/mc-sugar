package sugar.item;

import java.util.HashSet;
import java.util.Set;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.event.client.player.ClientPreAttackCallback;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;

public class MultiAttackManager {
    public static void clientInitialize() {
        ClientPreAttackCallback.EVENT.register((client, player, clickCount) -> {
            if (clickCount == 0) return false;
            if (client.gui.screen() != null) return false;
            if (player.getMainHandItem().is(ModItems.FLAGLESS)) {
                ClientPlayNetworking.send(new MultiAttackPayload());
            }
            return false;
        });
    }

    public static void initialize() {
        PayloadTypeRegistry.serverboundPlay().register(
            MultiAttackPayload.TYPE,
            MultiAttackPayload.CODEC
        );

        ServerPlayNetworking.registerGlobalReceiver(
            MultiAttackPayload.TYPE,
            (payload, context) -> {
                ServerPlayer player = context.player();
                ItemStack item = player.getMainHandItem();

                if (!item.is(ModItems.FLAGLESS)) {
                    return;
                }

                ServerLevel level = player.level();
                Vec3 start = player.getEyePosition();
                Vec3 forward = player.getLookAngle();
                Set<LivingEntity> hitEntities = new HashSet<>();

                for (int i = -2; i <= 2; i++) {
                    float angle = Mth.DEG_TO_RAD * i * 15;

                    Vec3 direction = forward.yRot(angle);
                    double reach = player.getAttributeValue(Attributes.ENTITY_INTERACTION_RANGE);
                    Vec3 end = start.add(direction.scale(reach));

                    AABB searchBox = player.getBoundingBox()
                        .expandTowards(direction.scale(reach))
                        .inflate(1.0);

                    EntityHitResult hit = ProjectileUtil.getEntityHitResult(
                        player,
                        start,
                        end,
                        searchBox,
                        entity -> entity instanceof LivingEntity
                            && !entity.is(player)
                            && entity.isAlive(),
                        reach * reach
                    );

                    if (hit == null) continue;
                    if (hit.getEntity() instanceof LivingEntity target && hitEntities.add(target)) {
                        target.hurtServer(
                            level,
                            level.damageSources().playerAttack(player),
                            (float)player.getAttributeValue(Attributes.ATTACK_DAMAGE)
                        );
                    }
                }
            }
        );
    }
}
