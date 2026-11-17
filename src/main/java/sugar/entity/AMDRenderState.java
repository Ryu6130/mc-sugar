package sugar.entity;

import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.world.entity.AnimationState;

public class AMDRenderState extends LivingEntityRenderState{
    public final AnimationState attackAnimationState = new AnimationState();
    public final AnimationState abilityAnimationState = new AnimationState();
    public final AnimationState deathAnimationState = new AnimationState();
}
