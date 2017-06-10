package T145.magistics.blocks.cosmetic;

import javax.annotation.Nonnull;

import T145.magistics.blocks.MBlock;
import T145.magistics.tiles.devices.TileVoidBorder;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockVoidBorder extends MBlock {

	public BlockVoidBorder() {
		super("void_border", Material.BARRIER);
		setSoundType(SoundType.STONE);
		setBlockUnbreakable();
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return new TileVoidBorder();
	}

	@Override
	public EnumBlockRenderType getRenderType(IBlockState state) {
		return EnumBlockRenderType.ENTITYBLOCK_ANIMATED;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean shouldSideBeRendered(IBlockState state, @Nonnull IBlockAccess blockAccess, @Nonnull BlockPos pos, EnumFacing side) {
		IBlockState neighborState = blockAccess.getBlockState(pos.offset(side));
		return !neighborState.isOpaqueCube() && neighborState.getBlock() != Blocks.END_GATEWAY;
	}

	@Override
	public int getLightValue(IBlockState state) {
		return 15;
	}

	@Override
	public float getExplosionResistance(World world, BlockPos pos, Entity exploder, Explosion explosion) {
		return Blocks.BEDROCK.getExplosionResistance(world, pos, exploder, explosion);
	}

	public boolean isBlockProtected(IBlockState state, IBlockAccess world, BlockPos pos) {
		return true;
	};

	@Override
	public boolean canEntityDestroy(IBlockState state, IBlockAccess world, BlockPos pos, Entity entity) {
		if (isBlockProtected(state, world, pos)) {
			return false;
		}

		return super.canEntityDestroy(state, world, pos, entity);
	}

	@Override
	public boolean canBeReplacedByLeaves(IBlockState state, IBlockAccess world, BlockPos pos) {
		if (isBlockProtected(state, world, pos)) {
			return false;
		}

		return super.canBeReplacedByLeaves(state, world, pos);
	}

	@Override
	public boolean canDropFromExplosion(Explosion explosionIn) {
		return true;
	}

	@Override
	public void onBlockExploded(World world, BlockPos pos, Explosion explosion) {
		if (isBlockProtected(world.getBlockState(pos), world, pos)) {
			return;
		}

		super.onBlockExploded(world, pos, explosion);
	}
}