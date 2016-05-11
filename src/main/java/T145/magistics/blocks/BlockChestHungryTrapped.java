package T145.magistics.blocks;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import thaumcraft.common.blocks.BlockChestHungry;
import T145.magistics.Magistics;
import T145.magistics.lib.InventoryHelper;
import T145.magistics.tiles.TileChestHungryTrapped;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockChestHungryTrapped extends BlockChestHungry {
	public static final Block INSTANCE = new BlockChestHungryTrapped();
	private int renderID = RenderingRegistry.getNextAvailableRenderId();

	public BlockChestHungryTrapped() {
		super();
		setBlockName("trapped_hungry_chest");
		setBlockTextureName("thaumcraft:woodplain");
		setCreativeTab(Magistics.tabMagistics);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getRenderType() {
		return renderID;
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return new TileChestHungryTrapped();
	}

	@Override
	public void onEntityCollidedWithBlock(World world, int x, int y, int z, Entity entity) {
		TileChestHungryTrapped chest = (TileChestHungryTrapped) world.getTileEntity(x, y, z);
		InventoryHelper.absorbCollidingItemStackIntoInventory(entity, chest, this, 2, 2, world, x, y, z, true);
	}

	@Override
	public void onNeighborBlockChange(World world, int x, int y, int z, Block block) {
		super.onNeighborBlockChange(world, x, y, z, block);
		TileChestHungryTrapped chest = (TileChestHungryTrapped) world.getTileEntity(x, y, z);

		if (chest != null) {
			chest.updateContainingBlockInfo();
		}
	}

	@Override
	public boolean canProvidePower() {
		return true;
	}

	@Override
	public int isProvidingWeakPower(IBlockAccess world, int x, int y, int z, int power) {
		TileChestHungryTrapped chest = (TileChestHungryTrapped) world.getTileEntity(x, y, z);
		return MathHelper.clamp_int(chest.numUsingPlayers, 0, 15);
	}

	@Override
	public int isProvidingStrongPower(IBlockAccess world, int x, int y, int z, int power) {
		return isProvidingWeakPower(world, x, y, z, power);
	}
}