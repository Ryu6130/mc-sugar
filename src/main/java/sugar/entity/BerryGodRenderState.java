package sugar.entity;

import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.world.entity.AnimationState;

public class BerryGodRenderState extends LivingEntityRenderState {
    public final AnimationState attackAnimationState = new AnimationState();
    public final AnimationState shotAnimationState = new AnimationState();
    public final AnimationState deathAnimationState = new AnimationState();
}
