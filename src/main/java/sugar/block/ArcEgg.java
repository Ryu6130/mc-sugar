package sugar.block;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import sugar.entity.Arc;
import sugar.entity.ModEntityTypes;

public class ArcEgg extends Block {
    private static final VoxelShape SHAPE =
        Block.box(5, 0, 5, 11, 7, 11);

    public ArcEgg(Properties properties) {
        super(properties);
    }
    
    @Override
    protected void randomTick(
        BlockState state,
        ServerLevel level,
        BlockPos pos,
        RandomSource random
    ) {
        level.removeBlock(pos, false);
        level.playSound(null, pos, SoundEvents.TURTLE_EGG_HATCH, SoundSource.BLOCKS);
        Arc arc = ModEntityTypes.ARC.create(level, EntitySpawnReason.TRIGGERED);
        arc.setBaby(true);
        arc.snapTo(Vec3.atBottomCenterOf(pos));
        level.addFreshEntity(arc);
    }

    @Override
    protected VoxelShape getShape(
        BlockState state,
        BlockGetter level,
        BlockPos pos,
        CollisionContext context
    ) {
        return SHAPE;
    }
}
