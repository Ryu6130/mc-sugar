package sugar.entity;

import java.util.Optional;

import org.jspecify.annotations.Nullable;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.stats.Stats;
import net.minecraft.util.RandomSource;
import net.minecraft.util.Unit;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.trading.Merchant;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraft.world.item.trading.MerchantOffers;
import net.minecraft.world.item.trading.VillagerTrade;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;

public class Gnome extends PathfinderMob implements Merchant {
    private @Nullable MerchantOffers offers;
    private @Nullable Player tradingPlayer;

    public Gnome(Level level) {
        this(ModEntityTypes.GNOME, level);
    }

    public Gnome(EntityType<? extends Gnome> type, Level level) {
        super(type, level);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(1, new FloatGoal(this));
        this.goalSelector.addGoal(2, new WaterAvoidingRandomStrollGoal(this, 0.5));
        this.goalSelector.addGoal(3, new RandomLookAroundGoal(this));
    }

	public static AttributeSupplier.Builder createAttributes() {
		return Mob.createMobAttributes()
            .add(Attributes.MAX_HEALTH, 12)
            .add(Attributes.MOVEMENT_SPEED, 0.26)
            .add(Attributes.FOLLOW_RANGE, 16)
            .add(Attributes.ATTACK_DAMAGE, 1)
            .add(Attributes.ATTACK_KNOCKBACK, 1)
            .add(Attributes.ATTACK_SPEED, 1);
	}

    public static boolean checkSpawnRules(
        EntityType<Gnome> type,
        ServerLevelAccessor level,
        EntitySpawnReason spawnReason,
        BlockPos pos,
        RandomSource random
    ) {
        if (!Mob.checkMobSpawnRules(type, level, spawnReason, pos, random)) {
            return false;
        }

        if (level.getRawBrightness(pos, 0) < 10) {
            return false;
        }

        return true;
    }

    @Override
    public SoundEvent getNotifyTradeSound() {
        return SoundEvents.VILLAGER_YES;
    }

    @Override
    public MerchantOffers getOffers() {
        Level var2 = this.level();
        if (var2 instanceof ServerLevel serverLevel) {
            if (this.offers == null) {
                this.offers = new MerchantOffers();
                for (VillagerTrade trade : GnomeTrades.getTrades()) {
                    offers.add(trade.getOffer(new LootContext.Builder(
                        new LootParams.Builder(serverLevel)
                            .withParameter(LootContextParams.THIS_ENTITY, this)
                            .withParameter(LootContextParams.ORIGIN, this.position())
                            .withParameter(
                                LootContextParams.ADDITIONAL_COST_COMPONENT_ALLOWED,
                                Unit.INSTANCE
                            )
                            .create(LootContextParamSets.VILLAGER_TRADE)
                    ).create(Optional.empty())));
                }
            }
            return this.offers;
        } else {
            throw new IllegalStateException("Cannot load Gnome offers on the client");
        }
    }

    public InteractionResult mobInteract(final Player player, final InteractionHand hand) {
        if (
            this.isAlive() &&
            tradingPlayer == null &&
            !this.isSleeping() &&
            !this.level().isClientSide() &&
            player instanceof ServerPlayer serverPlayer
        ) {
            if (hand == InteractionHand.MAIN_HAND) {
                player.awardStat(Stats.TALKED_TO_VILLAGER);
            }

            setTradingPlayer(serverPlayer);
            this.openTradingScreen(
                serverPlayer,
                Component.translatable("entity.sugar.gnome"),
                1
            );

            return InteractionResult.SUCCESS;
        } else {
            return super.mobInteract(player, hand);
        }
    }

    @Override
    protected void addAdditionalSaveData(final ValueOutput output) {
        super.addAdditionalSaveData(output);
        if (!this.level().isClientSide() && this.offers != null) {
            output.store("Offers", MerchantOffers.CODEC, this.offers);
        }
    }

    @Override
    protected void readAdditionalSaveData(final ValueInput input) {
        super.readAdditionalSaveData(input);
        this.offers = (MerchantOffers)input.read("Offers", MerchantOffers.CODEC).orElse(null);
    }

    @Override
    public @Nullable Player getTradingPlayer() {
        return this.tradingPlayer;
    }

    @Override
    public int getVillagerXp() {
        return 0;
    }

    @Override
    public boolean isClientSide() {
        return level().isClientSide();
    }

    @Override
    public void notifyTrade(MerchantOffer offer) {
        offer.increaseUses();
        this.ambientSoundTime = -this.getAmbientSoundInterval();
        if (this.tradingPlayer instanceof ServerPlayer) {
            
        }
    }

    @Override
    public void notifyTradeUpdated(ItemStack itemStack) {
        if (!this.level().isClientSide() && this.ambientSoundTime > -this.getAmbientSoundInterval() + 20) {
            this.ambientSoundTime = -this.getAmbientSoundInterval();
            this.makeSound(this.getTradeUpdatedSound(!itemStack.isEmpty()));
        }
    }
    
    protected SoundEvent getTradeUpdatedSound(final boolean validTrade) {
        return validTrade ? SoundEvents.VILLAGER_YES : SoundEvents.VILLAGER_NO;
    }

    @Override
    public void overrideOffers(MerchantOffers offers) {}

    @Override
    public void overrideXp(int xp) {}

    @Override
    public void setTradingPlayer(@Nullable Player player) {
        this.tradingPlayer = player;
    }

    @Override
    public boolean showProgressBar() {
        return false;
    }

    @Override
    public boolean stillValid(Player player) {
        return this.getTradingPlayer() == player && this.isAlive() && player.isWithinEntityInteractionRange(this, (double)4.0F);
    }
}
