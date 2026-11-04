package sugar.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ServerLevelAccessor;

public class DesertDessert extends Monster {
	public DesertDessert(Level world) {
		this(ModEntityTypes.DESERT_DESSERT, world);
	}

	public DesertDessert(EntityType<? extends DesertDessert> entityType, Level world) {
		super(entityType, world);
        this.xpReward = XP_REWARD_MEDIUM;
	}

	public static AttributeSupplier.Builder createAttributes() {
		return Monster.createMonsterAttributes()
            .add(Attributes.MAX_HEALTH, 18)
            .add(Attributes.MOVEMENT_SPEED, 0.4)
            .add(Attributes.FOLLOW_RANGE, 16)
            .add(Attributes.ATTACK_DAMAGE, 3)
            .add(Attributes.ATTACK_KNOCKBACK, 0.75)
            .add(Attributes.ATTACK_SPEED, 2);
	}

    @Override
    protected void registerGoals() {
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
        this.goalSelector.addGoal(1, new FloatGoal(this));
        this.goalSelector.addGoal(2, new MeleeAttackGoal(this, 1, false));
        this.goalSelector.addGoal(3, new WaterAvoidingRandomStrollGoal(this, 0.5));
        this.goalSelector.addGoal(4, new RandomLookAroundGoal(this));
    }

    @Override
    public float getWalkTargetValue(BlockPos pos, LevelReader level) {
        return 0;
    }

    public static boolean checkDesertDessertSpawnRules(
        EntityType<DesertDessert> type,
        ServerLevelAccessor level,
        EntitySpawnReason spawnReason,
        BlockPos pos,
        RandomSource random
    ) {
        if (!Monster.checkAnyLightMonsterSpawnRules(type, level, spawnReason, pos, random)) {
            return false;
        }

        if (!level.canSeeSky(pos)) {
            return false;
        }

        if (level.getRawBrightness(pos, 0) < 10) {
            return false;
        }

        return true;
    }
}
