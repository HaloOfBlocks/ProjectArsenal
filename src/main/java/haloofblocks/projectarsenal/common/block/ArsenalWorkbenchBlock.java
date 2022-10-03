package haloofblocks.projectarsenal.common.block;

import com.mrcrayfish.guns.block.RotatedObjectBlock;
import com.mrcrayfish.guns.util.VoxelShapeHelper;
import haloofblocks.projectarsenal.common.blockentity.ArsenalWorkbenchBlockEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Autovw
 */
public class ArsenalWorkbenchBlock extends RotatedObjectBlock
{
    private final Map<BlockState, VoxelShape> SHAPES = new HashMap<>();

    public ArsenalWorkbenchBlock(Block.Properties properties)
    {
        super(properties);
    }

    private VoxelShape getShape(BlockState state)
    {
        if (SHAPES.containsKey(state))
        {
            return SHAPES.get(state);
        }
        Direction direction = state.getValue(FACING);
        List<VoxelShape> shapes = new ArrayList<>();
        shapes.add(Block.box(0.5, 0, 0.5, 15.5, 13, 15.5));
        shapes.add(Block.box(0, 13, 0, 16, 15, 16));
        shapes.add(VoxelShapeHelper.getRotatedShapes(VoxelShapeHelper.rotate(Block.box(0, 15, 0, 16, 16, 2), Direction.SOUTH))[direction.get2DDataValue()]);
        VoxelShape shape = VoxelShapeHelper.combineAll(shapes);
        SHAPES.put(state, shape);
        return shape;
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader reader, BlockPos pos, ISelectionContext context)
    {
        return this.getShape(state);
    }

    @Override
    public VoxelShape getOcclusionShape(BlockState state, IBlockReader reader, BlockPos pos)
    {
        return this.getShape(state);
    }

    @Override
    public ActionResultType use(BlockState state, World world, BlockPos pos, PlayerEntity playerEntity, Hand hand, BlockRayTraceResult result)
    {
        if (!world.isClientSide())
        {
            TileEntity blockEntity = world.getBlockEntity(pos);
            if (blockEntity instanceof INamedContainerProvider)
            {
                NetworkHooks.openGui((ServerPlayerEntity) playerEntity, (INamedContainerProvider) blockEntity, pos);
            }
        }
        return ActionResultType.SUCCESS;
    }

    @Override
    public boolean hasTileEntity(BlockState state)
    {
        return true;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world)
    {
        return new ArsenalWorkbenchBlockEntity();
    }
}
