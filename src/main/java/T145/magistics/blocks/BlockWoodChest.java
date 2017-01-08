package T145.magistics.blocks;

import javax.annotation.Nullable;

import T145.magistics.Magistics;
import T145.magistics.api.enums.EnumWood;
import T145.magistics.tiles.TileWoodChest;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntityOcelot;
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
import net.minecraftforge.fml.common.registry.GameRegistry;

public class BlockWoodChest extends MBlock<EnumWood> implements ITileEntityProvider {

	public static final AxisAlignedBB CHEST_AABB = new AxisAlignedBB(0.0625D, 0D, 0.0625D, 0.9375D, 0.875D, 0.9375D);

	public BlockWoodChest(String name) {
		super(name, Material.WOOD, EnumWood.class);

		setSoundType(SoundType.WOOD);
		setHardness(3F);
		setResistance(17F);

		GameRegistry.registerTileEntity(TileWoodChest.class, TileWoodChest.class.getSimpleName());
	}

	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
		return CHEST_AABB;
	}

	@Override
	public boolean isOpaqueCube(IBlockState state) {
		return false;
	}

	@Override
	public boolean isFullCube(IBlockState state) {
		return false;
	}

	public EnumBlockRenderType getRenderType(IBlockState state) {
		return EnumBlockRenderType.ENTITYBLOCK_ANIMATED;
	}

	@Override
	public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
		TileWoodChest chest = (TileWoodChest) world.getTileEntity(pos);

		if (chest != null) {
			chest.setFacingFromEntity(placer);
		}
	}

	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, @Nullable ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ) {
		if (!world.isRemote) {
			for (EnumFacing facing : EnumFacing.Plane.HORIZONTAL) {
				BlockPos offset = pos.offset(facing);

				if (!isBlocked(world, offset)) {
					player.openGui(Magistics.MODID, 1, world, pos.getX(), pos.getY(), pos.getZ());
					return true;
				}
			}
		}

		return false;
	}

	private boolean isBlocked(World world, BlockPos pos) {
		return isBelowSolidBlock(world, pos) || isOcelotSittingOnChest(world, pos);
	}

	private boolean isBelowSolidBlock(World world, BlockPos pos) {
		return world.getBlockState(pos.up()).isSideSolid(world, pos.up(), EnumFacing.DOWN);
	}

	private boolean isOcelotSittingOnChest(World world, BlockPos pos) {
		for (Entity entity : world.getEntitiesWithinAABB(EntityOcelot.class, new AxisAlignedBB(pos.getX(), pos.getY() + 1, pos.getZ(), pos.getX() + 1, pos.getY() + 2, pos.getZ() + 1))) {
			EntityOcelot ocelot = (EntityOcelot) entity;

			if (ocelot.isSitting()) {
				return true;
			}
		}

		return false;
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
	public boolean hasComparatorInputOverride(IBlockState state) {
		return true;
	}

	@Override
	public int getComparatorInputOverride(IBlockState blockState, World world, BlockPos pos) {
		return Container.calcRedstoneFromInventory((IInventory) world.getTileEntity(pos));
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return new TileWoodChest();
	}
}