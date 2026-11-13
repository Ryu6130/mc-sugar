package sugar.item;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUseAnimation;
import net.minecraft.world.level.Level;
import sugar.entity.BlackHole;

public class EvenLightoo extends Item {
    public EvenLightoo(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult use(Level level, Player player, InteractionHand hand) {
        player.startUsingItem(hand);
        return InteractionResult.CONSUME;
    }

    @Override
    public boolean releaseUsing(ItemStack itemStack, Level level, LivingEntity entity, int remainingTime) {
        if (!(entity instanceof Player player)) return false;

        int timeHeld = this.getUseDuration(itemStack, entity) - remainingTime;
        float pow = BowItem.getPowerForTime(timeHeld);
        if (pow < 0.2) return false;

        if (level instanceof ServerLevel serverlevel) {
            BlackHole projectile = new BlackHole(serverlevel);
            projectile.setOwner(player);
            projectile.setPos(
                player.getEyePosition().add(player.getViewVector(1.0F).scale(0.5))
            );
            projectile.setXRot(player.getXRot());
            projectile.setYRot(player.getYRot());
            projectile.setDeltaMovement(player.getViewVector(1.0F).scale(pow));
            serverlevel.addFreshEntity(projectile);
            itemStack.hurtAndBreak(1, player, player.getUsedItemHand());
        }

        level.playSound(
            null,
            player.getX(),
            player.getY(),
            player.getZ(),
            SoundEvents.ARROW_SHOOT,
            SoundSource.PLAYERS,
            1.0F,
            1.0F / (level.getRandom().nextFloat() * 0.4F + 1.2F) + pow * 0.5F
        );
        player.awardStat(Stats.ITEM_USED.get(this));

        return true;
    }

    public int getUseDuration(final ItemStack itemStack, final LivingEntity user) {
        return 72000;
    }
    
    @Override
    public ItemUseAnimation getUseAnimation(final ItemStack itemStack) {
        return ItemUseAnimation.BOW;
    }
}
