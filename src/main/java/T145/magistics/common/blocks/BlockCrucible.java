package T145.magistics.common.blocks;

import static T145.magistics.client.lib.Render.W14;
import static T145.magistics.client.lib.Render.W2;
import static T145.magistics.client.lib.Render.W5;

import java.util.List;
import java.util.Random;

import javax.annotation.Nullable;

import T145.magistics.common.blocks.base.BlockBase;
import T145.magistics.common.tiles.TileCrucible;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.SoundEvents;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockCrucible extends BlockBase implements ITileEntityProvider {

	public static final AxisAlignedBB AABB_LEGS = new AxisAlignedBB(0D, 0D, 0D, 1D, W5, 1D);
	public static final AxisAlignedBB AABB_WALL_NORTH = new AxisAlignedBB(0D, 0D, 0D, 1D, 1D, W2);
	public static final AxisAlignedBB AABB_WALL_SOUTH = new AxisAlignedBB(0D, 0D, W14, 1D, 1D, 1D);
	public static final AxisAlignedBB AABB_WALL_EAST = new AxisAlignedBB(W14, 0D, 0D, 1D, 1D, 1D);
	public static final AxisAlignedBB AABB_WALL_WEST = new AxisAlignedBB(0D, 0D, 0D, W2, 1D, 1D);

	public BlockCrucible() {
		super("crucible", Material.IRON, false);
		setSoundType(SoundType.METAL);
		setHardness(3F);
		setResistance(17F);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return new TileCrucible();
	}

	@Override
	public void addCollisionBoxToList(IBlockState state, World world, BlockPos pos, AxisAlignedBB entityBox, List<AxisAlignedBB> collidingBoxes, @Nullable Entity entity, boolean p_185477_7_) {
		addCollisionBoxToList(pos, entityBox, collidingBoxes, AABB_LEGS);
		addCollisionBoxToList(pos, entityBox, collidingBoxes, AABB_WALL_WEST);
		addCollisionBoxToList(pos, entityBox, collidingBoxes, AABB_WALL_NORTH);
		addCollisionBoxToList(pos, entityBox, collidingBoxes, AABB_WALL_EAST);
		addCollisionBoxToList(pos, entityBox, collidingBoxes, AABB_WALL_SOUTH);
	}

	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
		return FULL_BLOCK_AABB;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void randomDisplayTick(IBlockState state, World world, BlockPos pos, Random rand) {
		/*if (crucible.isNormal()) {
			ParticleCreator.smallGreenFlameFX(world, pos.getX() + 0.2F + rand.nextFloat() * 0.6F, pos.getY() + 0.1F, pos.getZ() + 0.2F + rand.nextFloat() * 0.6F);
		}*/
	}

	@Override
	public void onEntityCollidedWithBlock(World world, BlockPos pos, IBlockState state, Entity entity) {
		TileCrucible crucible = (TileCrucible) world.getTileEntity(pos);

		if (!world.isRemote && entity.getEntityBoundingBox().minY <= pos.getY() + 0.7D) {
			if (entity instanceof EntityItem) {
				EntityItem item = (EntityItem) entity;
				item.motionX += (world.rand.nextFloat() - world.rand.nextFloat()) * 0.05F;
				item.motionY += world.rand.nextFloat() * 0.1F;
				item.motionZ += (world.rand.nextFloat() - world.rand.nextFloat()) * 0.05F;
				item.setPickupDelay(10);
			} else if (entity instanceof EntityLiving) {
				entity.attackEntityFrom(DamageSource.MAGIC, 1);
				world.playSound(null, pos.getX(), pos.getY(), pos.getZ(), SoundEvents.BLOCK_FIRE_EXTINGUISH, SoundCategory.BLOCKS, 0.4F, 2F + world.rand.nextFloat() * 0.4F);
			}
		}
	}
}