package sugar.effect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;

public class GazeOfEvil extends MobEffect {
    protected GazeOfEvil() {
        super(MobEffectCategory.HARMFUL, 0x6b821b);
    }
    
    @Override
    public boolean shouldApplyEffectTickThisTick(int tickCount, int amplification) {
        return true;
    }
}
