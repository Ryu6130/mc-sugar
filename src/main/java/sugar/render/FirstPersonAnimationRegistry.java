package sugar.render;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Nullable;

import org.joml.Quaternionf;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;

import net.minecraft.client.renderer.state.level.FirstPersonHandsAndItemsRenderState;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import sugar.item.ModItems;

public final class FirstPersonAnimationRegistry {
    private static final Map<Item, ArmAnimation> ANIMATIONS =
        new HashMap<>();

    public static void register(
        ItemLike item,
        ArmAnimation handler
    ) {
        ANIMATIONS.put(item.asItem(), handler);
    }

    public static @Nullable ArmAnimation get(ItemLike item) {
        return ANIMATIONS.get(item);
    }

    public static @Nullable ArmAnimation get(ItemStack item) {
        return ANIMATIONS.get(item.getItem());
    }

    @FunctionalInterface
    public interface ArmAnimation {
        boolean apply(
            PoseStack poseStack,
            HumanoidArm arm,
            FirstPersonHandsAndItemsRenderState state,
            float progress,
            float attackProgress
        );
    }

    public static void initialize() {
        FirstPersonAnimationRegistry.register(
            ModItems.FLAGLESS,
            (poseStack, arm, state, tickDelta, attack) -> {
                float progress = Mth.clamp(attack, 0.0F, 1.0F);
                float eased = Mth.sin(progress * Mth.PI);

                if (progress > 0.0F) {
                    poseStack.translate(0.2, -0.2, -0.4);
                    
                    poseStack.rotateDegrees(Axis.ZP, -90);
                    poseStack.rotateDegrees(Axis.YP, 10);
                    Quaternionf rotation = new Quaternionf()
                        .rotationAxis(-Mth.PI * eased, 1, 0, 0);
                    poseStack.rotateAround(rotation, 0.0F, -0.1F, 0.0F);

                    return true;
                }
                return false;
            }
        );
        FirstPersonAnimationRegistry.register(
            ModItems.JUST_NIL,
            (poseStack, arm, state, tickDelta, attack) -> {
                float progress = Mth.clamp(attack, 0.0F, 1.0F);
                float eased = Mth.sin(Mth.PI / 4 + progress * Mth.PI * 1.25);
                
                if (progress > 0.0F) {
                    poseStack.translate(0.2, -0.2, -0.1 + eased * -0.5);
                    
                    poseStack.rotateDegrees(Axis.ZP, -90);
                    poseStack.rotateDegrees(Axis.YP, 10);
                    Quaternionf rotation = new Quaternionf()
                        .rotationAxis(-Mth.PI / 2 * Mth.clamp(eased, 0.5f, 1.0f), 1, 0, 0);
                    poseStack.rotateAround(rotation, 0.0F, -0.1F, 0.0F);

                    return true;
                }
                return false;
            }
        );
    }
}
