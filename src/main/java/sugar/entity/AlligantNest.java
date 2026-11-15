package sugar.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ServerLevelAccessor;

public class AlligantNest extends Monster {
    public int ticks = 0;

    public AlligantNest(Level level) {
        this(ModEntityTypes.ALLIGANT_NEST, level);
    }

    public AlligantNest(EntityType<? extends AlligantNest> type, Level level) {
        super(type, level);
        this.xpReward = XP_REWARD_MEDIUM;
    }

    @Override
    public float getWalkTargetValue(BlockPos pos, LevelReader level) {
        return 0;
    }
    
    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(1, new FloatGoal(this));
    }

	public static AttributeSupplier.Builder createAttributes() {
		return Monster.createMonsterAttributes()
            .add(Attributes.MAX_HEALTH, 40)
            .add(Attributes.ARMOR, 20)
            .add(Attributes.MOVEMENT_SPEED, 0)
            .add(Attributes.FOLLOW_RANGE, 16)
            .add(Attributes.ATTACK_DAMAGE, 0)
            .add(Attributes.ATTACK_KNOCKBACK, 1)
            .add(Attributes.ATTACK_SPEED, 1)
            .add(Attributes.KNOCKBACK_RESISTANCE, 1);
	}

    public static boolean checkSpawnRules(
        EntityType<AlligantNest> type,
        ServerLevelAccessor level,
        EntitySpawnReason spawnReason,
        BlockPos pos,
        RandomSource random
    ) {
        return Monster.checkAnyLightMonsterSpawnRules(type, level, spawnReason, pos, random);
    }

    @Override
    protected void customServerAiStep(ServerLevel level) {
        if (
            ticks++ % 200 == 0 &&
            level.getEntitiesOfClass(Alligant.class, getBoundingBox().inflate(32.0)).size() <= 8
        ) {
            Alligant entity = new Alligant(level);
            entity.setPos(position());
            level.addFreshEntity(entity);
        }
    }
}
