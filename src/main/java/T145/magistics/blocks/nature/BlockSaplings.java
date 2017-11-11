package T145.magistics.blocks.nature;

import java.util.Random;

import T145.magistics.Magistics;
import T145.magistics.blocks.MBlockItem;
import T145.magistics.blocks.nature.BlockPlanks.WoodType;
import T145.magistics.world.features.WorldGenGreatwoodTree;
import T145.magistics.world.features.WorldGenSilverwoodTree;
import net.minecraft.block.BlockBush;
import net.minecraft.block.IGrowable;
import net.minecraft.block.SoundType;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;
import net.minecraft.world.gen.feature.WorldGenBigTree;
import net.minecraft.world.gen.feature.WorldGenTrees;
import net.minecraftforge.event.terraingen.TerrainGen;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockSaplings extends BlockBush implements IGrowable {

	public static final PropertyEnum<WoodType> VARIANT = PropertyEnum.<WoodType>create("variant", WoodType.class);
	public static final PropertyInteger STAGE = PropertyInteger.create("stage", 0, 1);
	protected static final AxisAlignedBB SAPLING_AABB = new AxisAlignedBB(0.09999999403953552D, 0.0D,
			0.09999999403953552D, 0.8999999761581421D, 0.800000011920929D, 0.8999999761581421D);

	public BlockSaplings() {
		super();
		String name = "saplings";
		setDefaultState(blockState.getBaseState().withProperty(VARIANT, WoodType.GREATWOOD).withProperty(STAGE, 0));
		setRegistryName(new ResourceLocation(Magistics.MODID, name));
		setCreativeTab(Magistics.TAB);
		setUnlocalizedName(name);
		setSoundType(SoundType.PLANT);
		setHardness(0F);

		// delete this in 1.12
		GameRegistry.register(this);
		GameRegistry.register(new MBlockItem(this, WoodType.class), getRegistryName());
	}

	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
		return SAPLING_AABB;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubBlocks(Item item, CreativeTabs tab, NonNullList<ItemStack> list) {
		for (WoodType type : WoodType.values()) {
			list.add(new ItemStack(item, 1, type.ordinal()));
		}
	}

	@Override
	public int getFlammability(IBlockAccess world, BlockPos pos, EnumFacing face) {
		return 60;
	}

	@Override
	public int getFireSpreadSpeed(IBlockAccess world, BlockPos pos, EnumFacing face) {
		return 30;
	}

	@Override
	public void updateTick(World world, BlockPos pos, IBlockState state, Random rand) {
		if (!world.isRemote) {
			super.updateTick(world, pos, state, rand);

			if (world.getLightFromNeighbors(pos.up()) >= 9 && rand.nextInt(7) == 0) {
				grow(world, rand, pos, state);
			}
		}
	}

	@Override
	public int damageDropped(IBlockState state) {
		return state.getValue(VARIANT).ordinal();
	}

	@Override
	public boolean canGrow(World world, BlockPos pos, IBlockState state, boolean isClient) {
		return true;
	}

	@Override
	public boolean canUseBonemeal(World world, Random rand, BlockPos pos, IBlockState state) {
		return world.rand.nextFloat() < 0.45D;
	}

	@Override
	public void grow(World world, Random rand, BlockPos pos, IBlockState state) {
		if (state.getValue(STAGE) == 0) {
			world.setBlockState(pos, state.cycleProperty(STAGE), 4);
		} else {
			generateTree(world, pos, state, rand);
		}
	}

	public void generateTree(World world, BlockPos pos, IBlockState state, Random rand) {
		if (TerrainGen.saplingGrowTree(world, rand, pos)) {
			WorldGenAbstractTree generator = rand.nextInt(10) == 0 ? new WorldGenBigTree(true)
					: new WorldGenTrees(true);

			switch (state.getValue(VARIANT)) {
			case GREATWOOD:
				generator = new WorldGenGreatwoodTree(false);
				break;
			case SILVERWOOD:
				generator = new WorldGenSilverwoodTree(true, 7, 4);
				break;
			default:
				break;
			}

			world.setBlockState(pos, Blocks.AIR.getDefaultState(), 4);

			if (!generator.generate(world, rand, pos)) {
				world.setBlockState(pos, state, 4);
			}
		}
	}

	@Override
	public IBlockState getStateFromMeta(int meta) {
		return getDefaultState().withProperty(VARIANT, WoodType.byMetadata(meta & 7)).withProperty(STAGE,
				Integer.valueOf((meta & 8) >> 3));
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		int i = 0;
		i = i | state.getValue(VARIANT).ordinal();
		i = i | state.getValue(STAGE) << 3;
		return i;
	}

	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, new IProperty[] { VARIANT, STAGE });
	}
}