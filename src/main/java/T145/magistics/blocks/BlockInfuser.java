package T145.magistics.blocks;

import javax.annotation.Nullable;

import T145.magistics.Magistics;
import T145.magistics.api.blocks.IBlockMagistics;
import T145.magistics.client.render.BlockRenderer;
import T145.magistics.tiles.TileInfuser;
import net.minecraft.block.BlockPistonBase;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockInfuser extends BlockMagistics implements ITileEntityProvider {

	protected static final AxisAlignedBB INFUSER_AABB = new AxisAlignedBB(0D, 0D, 0D, 1D, 1D - BlockRenderer.W1, 1D);

	public BlockInfuser() {
		super(Material.ROCK, InfuserType.class, SoundType.STONE);
	}

	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
		return INFUSER_AABB;
	}

	@Override
	public boolean isOpaqueCube(IBlockState state) {
		return false;
	}

	@Override
	public boolean isFullCube(IBlockState state) {
		return false;
	}

	@Override
	public EnumBlockRenderType getRenderType(IBlockState state) {
		return EnumBlockRenderType.MODEL;
	}

	@Override
	public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
		TileInfuser infuser = (TileInfuser) world.getTileEntity(pos);

		if (infuser != null) {
			infuser.setFacing(BlockPistonBase.getFacingFromEntity(pos, placer).getIndex());
		}
	}

	@Override
	public void breakBlock(World world, BlockPos pos, IBlockState state) {
		IInventory inv = (IInventory) world.getTileEntity(pos);

		if (inv != null) {
			InventoryHelper.dropInventoryItems(world, pos, inv);
			world.updateComparatorOutputLevel(pos, this);
		}

		super.breakBlock(world, pos, state);
	}

	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, @Nullable ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ) {
		if (!world.isRemote) {
			player.openGui(Magistics.MODID, 0, world, pos.getX(), pos.getY(), pos.getZ());
		}

		return true;
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return new TileInfuser();
	}

	@Override
	public boolean hasComparatorInputOverride(IBlockState state) {
		return true;
	}

	@Override
	public int getComparatorInputOverride(IBlockState blockState, World world, BlockPos pos) {
		return Container.calcRedstoneFromInventory((IInventory) world.getTileEntity(pos));
	}

	public static enum InfuserType implements IBlockMagistics {
		LIGHT, DARK;

		@Override
		public String getName() {
			return name().toLowerCase();
		}

		@Override
		public String toString() {
			return getName();
		}
	}
}