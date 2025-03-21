package infvoid.fishingnet;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.Waterloggable;
import net.minecraft.fluid.Fluids;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.util.math.Direction;
import net.minecraft.item.ItemPlacementContext;

public class FishingNetBlock extends Block implements Waterloggable {
    private static final VoxelShape SHAPE = Block.createCuboidShape(3, 0, 0, 13, 10, 16);
    public static final BooleanProperty WATERLOGGED = Properties.WATERLOGGED;

    public FishingNetBlock() {
        super(FabricBlockSettings.create().strength(0.8f));

        this.setDefaultState(this.stateManager.getDefaultState().with(WATERLOGGED, false));
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE;
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(WATERLOGGED);
    }

    // ✅ Fix: Ensures the block only places underwater and on solid blocks
    @Override
    public BlockState getPlacementState(ItemPlacementContext context) {
        BlockPos pos = context.getBlockPos();
        World world = context.getWorld();

        // ✅ Check if water is present and block below is solid on top
        if (
                world.getFluidState(pos).isOf(Fluids.WATER) &&
                        world.getBlockState(pos.down()).isSideSolidFullSquare(world, pos.down(), Direction.UP)
        ) {
            return this.getDefaultState().with(WATERLOGGED, true);
        }

        return null; // Prevent placement in invalid conditions
    }


    // ✅ Ensures the block breaks if water is removed
    @Override
    public void neighborUpdate(BlockState state, World world, BlockPos pos, Block block, BlockPos fromPos, boolean notify) {
        if (!isUnderwater(world, pos)) {
            world.breakBlock(pos, true);
        }
    }

    private boolean isUnderwater(WorldAccess world, BlockPos pos) {
        return world.getFluidState(pos).isOf(Fluids.WATER);
    }
}
