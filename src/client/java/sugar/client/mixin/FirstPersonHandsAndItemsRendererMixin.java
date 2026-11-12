package sugar.client.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.renderer.FirstPersonHandsAndItemsRenderer;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.state.AvatarRenderState;
import net.minecraft.client.renderer.state.level.FirstPersonHandsAndItemsRenderState;
import net.minecraft.client.renderer.state.level.PlayerRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.item.ItemStack;
import sugar.render.FirstPersonAnimationRegistry;
import sugar.render.FirstPersonAnimationRegistry.ArmAnimation;

@Mixin(FirstPersonHandsAndItemsRenderer.class)
public abstract class FirstPersonHandsAndItemsRendererMixin {
    @Invoker("renderPlayerArm")
    protected abstract void sugar$renderPlayerArm(
        PoseStack poseStack,
        SubmitNodeCollector collector,
        int lightCoords,
        float inverseArmHeight,
        float attack,
        HumanoidArm arm,
        PlayerRenderState playerState
    );

    @Inject(
        method = "submitArmWithItem",
        at = @At("HEAD"),
        cancellable = true
    )
    private void rotateArm(
        PlayerRenderState playerState,
        FirstPersonHandsAndItemsRenderState state,
        float partialTicks,
        float xRot,
        InteractionHand hand,
        float attack,
        ItemStack itemStack,
        float inverseArmHeight,
        PoseStack poseStack,
        SubmitNodeCollector collector,
        int lightCoords,
        CallbackInfo ci
    ) {
        ArmAnimation animation = FirstPersonAnimationRegistry.get(itemStack);

        if (animation == null) return;

        if (state.isScoping) {
            return;
        }

        AvatarRenderState avatar = playerState.avatarRenderState;
        if (avatar == null) return;

        boolean isMainHand = hand == InteractionHand.MAIN_HAND;
        HumanoidArm arm = isMainHand
            ? avatar.mainArm
            : avatar.mainArm.getOpposite();

        poseStack.pushPose();

        try {
            if (animation.apply(
                poseStack,
                arm,
                state,
                partialTicks,
                attack
            )) {
                ci.cancel();

                if (itemStack.isEmpty()) {
                    if (isMainHand && !avatar.isInvisible) {
                        sugar$renderPlayerArm(
                            poseStack,
                            collector,
                            lightCoords,
                            inverseArmHeight,
                            attack,
                            arm,
                            playerState
                        );
                    }
                } else {
                    (isMainHand
                        ? state.mainHandRenderState
                        : state.offHandRenderState
                    ).submit(
                        poseStack,
                        collector,
                        lightCoords,
                        OverlayTexture.NO_OVERLAY,
                        0
                    );
                }
            };
        } finally {
            poseStack.popPose();
        }
    }
}