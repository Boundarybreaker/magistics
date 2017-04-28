package T145.magistics.network;

import T145.magistics.api.MagisticsApi;
import T145.magistics.api.ModBlocks;
import T145.magistics.blocks.machines.BlockCrucible;
import T145.magistics.blocks.machines.BlockInfuser;
import T145.magistics.blocks.storage.BlockConduit;
import T145.magistics.blocks.storage.BlockTank;
import T145.magistics.tiles.machines.TileInfuser;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.IGuiHandler;

public class CommonProxy implements IGuiHandler {

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		BlockPos pos = new BlockPos(x, y, z);
		TileEntity tile = world.getTileEntity(pos);

		switch (ID) {
		case 0:
			return ((TileInfuser) tile).createContainer(player.inventory, player);
		default:
			return null;
		}
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return null;
	}

	public void preInit(FMLPreInitializationEvent event) {
		ModBlocks.crucible = new BlockCrucible();
		ModBlocks.infuser = new BlockInfuser();
		ModBlocks.tank = new BlockTank();
		ModBlocks.conduit = new BlockConduit();
	}

	public void init(FMLInitializationEvent event) {
	}

	public void postInit(FMLPostInitializationEvent event) {
		MagisticsApi.addCrucibleRecipe(new ItemStack(Blocks.STONE, 1, 0), 1F);
		MagisticsApi.addInfuserRecipe(new ItemStack(Blocks.DISPENSER), new ItemStack[] {
				new ItemStack(Blocks.PISTON),
				new ItemStack(Blocks.FURNACE)
		}, 1F, false);
	}
}